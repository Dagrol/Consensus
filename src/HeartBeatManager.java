import java.util.Random;

/**
 * Created by pavan on 26/07/2016.
 */
public class HeartBeatManager implements Runnable {
    private Client client;
    private String address;
    private int ID;
    private int sendPort;
    private SharedData sharedData;

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
            Message heartBeat = new Message("PAYLOAD","",this.address,this.sendPort+"","","",this.ID+"","",messageID);
            this.sharedData.sentMessages.add(messageID);

           // client.broadcastMessage();
            if(this.ID != 0){
                client.sendMessage(this.sharedData.adjNodes.get(0),heartBeat);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        //}
    }

    public void setSharedData(SharedData sharedData) {
        this.sharedData = sharedData;
    }
}
