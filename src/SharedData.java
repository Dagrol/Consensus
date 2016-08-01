import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavan on 01/08/2016.
 */
public class SharedData {
    public volatile List<String> sentMessages;
    public volatile  List<String> receivedMessages;
    public volatile List<Node> adjNodes;
    public SharedData(ArrayList<String> receivedMessages, ArrayList<String> sentMessages, ArrayList<Node> adjNodes) {
        this.receivedMessages = receivedMessages;
        this.sentMessages = sentMessages;
        this.adjNodes = adjNodes;
    }
}
