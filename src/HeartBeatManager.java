import java.util.Random;

public class HeartBeatManager implements Runnable {
    private Client client;
    private String address;
    private int ID;
    private int sendPort;
    private SharedData sharedData;

    public HeartBeatManager(String address, int ID, int sendPort){
        this.client = new Client(ID,sendPort,this.sharedData);
        this.address = address;
        this.ID = ID;
        this.sendPort = sendPort;
    }

    @Override
    public void run() {
        while(true){
            String messageID = new Random().nextInt(1000000)+""+this.ID;
            Message heartBeat = new Message("HEARTBEAT","",this.address,this.sendPort+"","","",this.ID+"","",messageID);

            /*
            synchronized (this.sharedData.sentMessages) {
                this.sharedData.sentMessages.add(messageID);
            } */

            client.broadcastMessage(heartBeat);
            /*if(this.ID != 0){
                client.sendMessage(this.sharedData.adjNodes.get(0),heartBeat);
            } */

            try {
                Thread.sleep(1000);
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSharedData(SharedData sharedData) {
            this.sharedData = sharedData;
            this.client.setSharedData(sharedData);
    }
}
