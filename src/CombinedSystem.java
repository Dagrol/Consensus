import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavan on 28/07/2016.
 */
public class CombinedSystem implements Runnable {
    public volatile List<Node> adjNodes;
    private Controller controller;
    private HeartBeatManager heartBeatManager;

    public volatile List<String> receivedMessages = new ArrayList<>();
    public volatile List<String> sentMessages = new ArrayList<>();

    public CombinedSystem(Controller controller, HeartBeatManager heartBeatManager,List<Node> adjNodes) {
        this.adjNodes = adjNodes;
        this.controller = controller;
        this.heartBeatManager = heartBeatManager;

    }

    @Override
    public void run() {
        controller.setSystem(this);
        heartBeatManager.setSystem(this);
        new Thread(controller).start();
        new Thread(heartBeatManager).start();
    }
}
