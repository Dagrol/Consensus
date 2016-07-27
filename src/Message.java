import java.util.List;

/**
 * Created by pavan on 25/07/2016.
 */
public class Message {
    public String Data;
    public String Type;

    public String FromAddr;
    public int FromPort;
    public String ToAddr;
    public int ToPort;

    public Message(String Type, String Data, String FromAddr, String FromPort, String ToAddr,String ToPort){
        this.FromAddr = FromAddr;
        this.FromPort = Integer.parseInt(FromPort);
        this.ToAddr = ToAddr;
        this.ToPort = Integer.parseInt(ToPort);
    }

}
