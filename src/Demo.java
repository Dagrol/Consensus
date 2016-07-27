
/**
 * Created by pavan on 25/07/2016.
 */
public class Demo {

    public static void main(String[] args) throws Exception{
        Thread controller1 = new Thread(new Controller(0,8000));
        Thread heartBeatManager1 = new Thread(new HeartBeatManager(0,8000));
        controller1.start();
        heartBeatManager1.start();


        Thread controller2 = new Thread(new Controller(0,8001));
        Thread heartBeatManager2 = new Thread(new HeartBeatManager(0,8001));
        controller2.start();
        heartBeatManager2.start();

    }



}
