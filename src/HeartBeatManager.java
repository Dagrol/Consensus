/**
 * Created by pavan on 26/07/2016.
 */
public class HeartBeatManager implements Runnable {
    private Client client;

    private int ID;

    public HeartBeatManager(int ID, int destPort){
        this.client = new Client(ID,destPort);
    }

    @Override
    public void run() {
        while(true){
            client.broadcastMessage(new Message("HEARTBEAT","","localhost","8000","","",this.ID+"",""));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
