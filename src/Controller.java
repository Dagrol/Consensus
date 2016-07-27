import com.google.gson.Gson;
import java.io.IOException;
import java.net.*;
import java.security.Timestamp;
import java.util.*;

/**
 * Created by pavan on 25/07/2016.
 */
public class Controller implements Runnable {

    private int ID;
    private String ipAddress;
    private String NodeData;
    private int listenPort;
    private int sendPort;
    private List<Node> adjNodes = new ArrayList<>();

    private Map<Integer,List<Integer>> globalAdjMapping = new HashMap<>();
    private Map<String,Timestamp> latestHeartbeats = new HashMap<>();

    public Controller(int ID,int listenPort, int sendPort,String ipAddress) {
        this.ID = ID; this.listenPort = listenPort; this.sendPort = sendPort; this.ipAddress = ipAddress;
    }


    @Override
    public void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(listenPort);
            while(true) {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                serverSocket.receive(packet);
                handle(packet);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handle(DatagramPacket packet){
        String receivedData = new String(packet.getData(),0,packet.getLength());
        Gson gson = new Gson();
        Message receivedMessage = gson.fromJson(receivedData,Message.class);
        System.out.println("Thread: " + Thread.currentThread().getId() + " Type: " + receivedMessage.Type + ", " + "FROM: " + receivedMessage.FromAddr);
        if(receivedMessage.Type.equals("SYNC")){
            handleSync(receivedMessage);
        }else if(receivedMessage.Type.equals("PAYLOAD")){
            handlePayload(receivedMessage);
        }else if(receivedMessage.Type.equals("HEARTBEAT")){
            handleHeartbeat(receivedMessage);
        }else if(receivedMessage.Type.equals("JOIN")){
            handleJOIN(receivedMessage);
        }

    }

    private void handleJOIN(Message receivedMessage) {
        Node adjNode = new Node(receivedMessage.FromAddr,Integer.parseInt(receivedMessage.FromPort),Integer.parseInt(receivedMessage.FromPort),Integer.parseInt(receivedMessage.FromID));
        Message syncMessage = new Message("SYNC",new Gson().toJson(receivedMessage),this.ipAddress,this.sendPort+"","receiverAddress","receiverPort",this.ID+"","");
        adjNodes.add(adjNode);
        broadcastMessage(syncMessage);
    }


    private void handleHeartbeat(Message receivedMessage) {
        latestHeartbeats.put(receivedMessage.FromAddr,null);
    }

    private void handlePayload(Message receivedMessage) {
        this.NodeData = receivedMessage.Data;
        broadcastMessage(receivedMessage);
    }

    private void handleSync(Message receivedMessage) {
        Message originalJOINMessage = new Gson().fromJson(receivedMessage.Data,Message.class);
        globalAdjMapping.get(originalJOINMessage.FromID).add(Integer.parseInt(originalJOINMessage.ToID));
        globalAdjMapping.get(originalJOINMessage.ToID).add(Integer.parseInt(originalJOINMessage.FromID));
        Message syncMessage = new Message("SYNC",new Gson().toJson(receivedMessage),this.ipAddress,this.sendPort+"","receiverAddress","receiverPort",this.ID+"","");
        broadcastAllExcept(syncMessage,Integer.parseInt(receivedMessage.FromID));

    }

    public void broadcastMessage(Message message){
        for(Node neighbour : adjNodes){
            message.ToID = neighbour.getID()+"";
            message.ToAddr = neighbour.getIpAddress().getHostAddress();
            message.ToPort = neighbour.getListenPort() + "";
            sendMessage(neighbour,message);
        }
    }

    public void broadcastAllExcept(Message message,int nodeID){
        for(Node neighbour: adjNodes){
            if(neighbour.getID() != nodeID){
                message.ToID = neighbour.getID() + "";
                message.ToAddr = neighbour.getIpAddress().getHostAddress();
                message.ToPort = neighbour.getListenPort() + "";
                sendMessage(neighbour,message);
            }
        }

    }

    public void sendMessage(Node destination, Message message) {
        /**
         * Create a UDP packet consisting of the source ID and the message payload
         * All the nodes will be listening for pulses on an universally known port.
         */

        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket();
            byte[] sendData = new Gson().toJson(message).getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, destination.getIpAddress(), destination.getListenPort());
            clientSocket.send(sendPacket);
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
