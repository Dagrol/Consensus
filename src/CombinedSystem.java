
/**
 * Created by pavan on 28/07/2016.
 */
public class CombinedSystem implements Runnable {
    private Controller controller;
    private HeartBeatManager heartBeatManager;
    private volatile SharedData sharedData;

    public CombinedSystem(Controller controller, HeartBeatManager heartBeatManager,SharedData sharedData) {
        this.controller = controller;
        this.heartBeatManager = heartBeatManager;
        this.sharedData = sharedData;
        this.controller.setSharedData(this.sharedData);
        this.heartBeatManager.setSharedData(this.sharedData);
    }

    @Override
    public void run() {
        new Thread(controller).start();
        new Thread(heartBeatManager).start();
    }
}
