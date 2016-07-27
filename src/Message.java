import java.util.List;

/**
 * Created by pavan on 25/07/2016.
 */
public class Message {
    public String Data;
    public String Type;
    public String FromID;
    public String ToID;

    public String FromAddr;
    public String FromPort;
    public String ToAddr;
    public String ToPort;

    public Message(String Type, String Data, String FromAddr, String FromPort, String ToAddr,String ToPort,String FromID,String ToID){
        this.FromAddr = FromAddr;
        this.FromPort = FromPort;
        this.ToAddr = ToAddr;
        this.ToPort = ToPort;
        this.Data = Data;
        this.Type = Type;
        this.FromID = FromID;
        this.ToID = ToID;
    }

}
