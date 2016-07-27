import com.google.gson.Gson;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by pavan on 26/07/2016.
 */
public class Client {
    private List<Node> neighbours = new ArrayList<Node>();
    private int ID;
    private int destPort;


    public Client(int ID,int destPort){
        this.ID = ID;
        this.destPort = destPort;
        this.neighbours.add(new Node("localhost",8000,0));
    }


    public void broadcastMessage(Message message){
        for(Node neighbour : neighbours){
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
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, destination.getIpAddress(), destPort);
            clientSocket.send(sendPacket);
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
