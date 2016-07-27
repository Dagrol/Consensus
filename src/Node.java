import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by pavan on 25/07/2016.
 */

public class Node {
    private InetAddress ipAddress;
    private int ID;
    private int port;

    public Node(String ipAddress, int port, int ID){
        this.ID = ID;
        this.port = port;
        try {
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getIpAddress(){
        return this.ipAddress;
    }

    public int getPort(){return this.port;}

    public void setIpAddress(String ipAddress){
        try {
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
