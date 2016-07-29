import java.util.List;
import java.util.Random;

/**
 * Created by pavan on 26/07/2016.
 */
public class HeartBeatManager implements Runnable {
    private Client client;
    private String address;
    private int ID;
    private int sendPort;
    private CombinedSystem system;

    public HeartBeatManager(String address, int ID, int sendPort){
        this.client = new Client(ID,sendPort);
        this.address = address;
        this.ID = ID;
        this.sendPort = sendPort;
    }

    @Override
    public void run() {
        //while(true){
            String messageID = new Random().nextInt(1000000)+""+this.ID;
            Message heartBeat = new Message("PAYLOAD","",address,sendPort+"","","",this.ID+"","",messageID);
            CombinedSystem.sentMessages.add(messageID);

           // client.broadcastMessage();

            client.sendMessage(CombinedSystem.adjNodes.get(0),heartBeat);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        //}
    }

    public void setSystem(CombinedSystem system) {
        this.system = system;
    }
}
