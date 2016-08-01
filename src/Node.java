import java.net.InetAddress;
import java.net.UnknownHostException;

public class Node {
    private InetAddress ipAddress;
    private int ID;
    private int listenPort;
    private int sendPort;

    public Node(String ipAddress, int listenPort,int sendPort, int ID){
        this.ID = ID;
        this.listenPort = listenPort;
        this.sendPort = sendPort;
        try {
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getIpAddress(){
        return this.ipAddress;
    }


    public void setIpAddress(String ipAddress){
        try {
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public int getID(){
        return this.ID;
    }

    public int getListenPort(){
        return this.listenPort;
    }

    public int getSendPort(){
        return this.sendPort;
    }

    @Override
    public String toString(){
        return this.getID() + "";
    }

}
