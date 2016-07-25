import java.util.*;

/**
 * Created by pavan on 25/07/2016.
 */
public class Controller {

    List<Node> neighbours = new ArrayList<Node>();
    Queue<Message> messageQueue = new LinkedList<Message>();


    public void broadcastMessages(){
        for (Message m : messageQueue){
            broadcastMessage(m);
        }
    }

    public void broadcastMessage(Message message){

    }


    public void sendMessage(Message message, Node node){

    }

    public void receiveMessage(Message message){
        messageQueue.add(message);
        broadcastMessages();
    }

}
