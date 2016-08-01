import com.google.gson.Gson;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class Client {
    private int ID;
    private int destPort;
    private SharedData sharedData;


    public Client(int ID,int destPort,SharedData sharedData){
        this.ID = ID;
        this.destPort = destPort;
        this.sharedData = sharedData;
    }


    public void broadcastMessage(Message message){
        for(Node neighbour : this.sharedData.adjNodes){
            message.ToID = neighbour.getID()+"";
            message.ToAddr = neighbour.getIpAddress().getHostAddress();
            message.ToPort = neighbour.getListenPort() + "";
            sendMessage(neighbour,message);
        }
    }

    public void sendMessage(Node destination, Message message) {
        /**
         * Create a UDP packet consisting of the source ID and the message payload
         * All the nodes will be listening for pulses on an universally known port.
         */

        message.ToID = destination.getID()+"";
        message.ToAddr = destination.getIpAddress().getHostAddress();
        message.ToPort = destination.getListenPort() + "";

        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket();
            byte[] sendData = new Gson().toJson(message).getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, destination.getIpAddress(), destination.getSendPort());
            clientSocket.send(sendPacket);
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSharedData(SharedData sharedData) {
        this.sharedData = sharedData;
    }
}
