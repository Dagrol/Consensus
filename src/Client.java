import com.google.gson.Gson;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by pavan on 26/07/2016.
 */
public class Client {
    private int ID;
    private int destPort;


    public Client(int ID,int destPort){
        this.ID = ID;
        this.destPort = destPort;
    }


    public void broadcastMessage(Message message){
        for(Node neighbour : CombinedSystem.adjNodes){
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
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, destination.getIpAddress(), destPort);
            clientSocket.send(sendPacket);
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
