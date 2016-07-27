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
    private String NodeData;
    private int listenPort;
    private List<Node> adjNodes = new ArrayList<Node>();

    private Map<Integer,List<Node>> globalAdjMapping = new HashMap<Integer,List<Node>>();
    private Map<String,Timestamp> latestHeartbeats = new HashMap<String,Timestamp>();

    public Controller(int ID,int listenPort) {
        this.ID = ID; this.listenPort = listenPort;
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
        System.out.println("Type: " + receivedMessage.Type + ", " + "FROM: " + receivedMessage.FromAddr);
        if(receivedMessage.Type.equals("SYNC")){
            handleSync(receivedMessage);
        }else if(receivedMessage.Type.equals("PAYLOAD")){
            handlePayload(receivedMessage);
        }else if(receivedMessage.Type.equals("HEARTBEAT")){
            handleHeartbeat(receivedMessage);
        }
    }

    private void handleHeartbeat(Message receivedMessage) {
        latestHeartbeats.put(receivedMessage.FromAddr,new Timestamp(new Date(),null));
    }

    private void handlePayload(Message receivedMessage) {
        this.NodeData = receivedMessage.Data;
        broadcastMessage(receivedMessage);
    }

    private void handleSync(Message receivedMessage) {
        String nodeData = receivedMessage.Data;
        adjNodes.add(new Gson().fromJson(nodeData,Node.class));
    }

    public void broadcastMessage(Message message){
        for(Node neighbour : adjNodes){
            sendMessage(neighbour,message);
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
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, destination.getIpAddress(), destination.getPort());
            clientSocket.send(sendPacket);
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
