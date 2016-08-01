
public class Message {
    public String Data;
    public String Type;
    public String FromID;
    public String ToID;

    public String FromAddr;
    public String FromPort;
    public String ToAddr;
    public String ToPort;
    public String MessageID;

    public String TTL;

    public Message(String Type, String Data, String FromAddr, String FromPort, String ToAddr,String ToPort,String FromID,String ToID,String MessageID){
        this.FromAddr = FromAddr;
        this.FromPort = FromPort;
        this.ToAddr = ToAddr;
        this.ToPort = ToPort;
        this.Data = Data;
        this.Type = Type;
        this.FromID = FromID;
        this.ToID = ToID;
        this.TTL = "0";
        this.MessageID = MessageID;
    }

    public void incrementTTL(){
       int TTL = Integer.parseInt(this.TTL)+1;
        this.TTL = TTL + "";
    }

    public int getTTL(){
        return Integer.parseInt(this.TTL);
    }

}
