import java.util.ArrayList;
import java.util.List;

public class Demo {

    public static void main(String[] args) throws Exception{
        List<Node> adjNodes1 = new ArrayList<>();
        List<Node> adjNodes2 = new ArrayList<>();
        List<Node> adjNodes3 = new ArrayList<>();

        Node node1 = new Node("localhost",8000,8000,0);
        Node node2 = new Node("localhost",8001,8001,1);
        Node node3 = new Node("localhost",8002,8002,2);

        adjNodes1.add(node2); adjNodes1.add(node3);
        adjNodes2.add(node1); adjNodes2.add(node3);
        adjNodes3.add(node1); adjNodes3.add(node2);


        SharedData sharedData1 = new SharedData(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        sharedData1.adjNodes = adjNodes1;
        Controller controller1 = new Controller(0,8000,8000,"localhost");
        HeartBeatManager heartBeatManager1 = new HeartBeatManager("localhost",0,8000);
        CombinedSystem system1 = new CombinedSystem(controller1,heartBeatManager1,sharedData1);
        new Thread(system1).start();

        SharedData sharedData2 = new SharedData(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        sharedData2.adjNodes = adjNodes2;
        Controller controller2 = new Controller(1,8001,8001,"localhost");
        HeartBeatManager heartBeatManager2 = new HeartBeatManager("localhost",1,8001);
        CombinedSystem system2 = new CombinedSystem(controller2,heartBeatManager2,sharedData2);
        new Thread(system2).start();

        SharedData sharedData3 = new SharedData(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        sharedData3.adjNodes = adjNodes3;
        Controller controller3 = new Controller(2,8002,8002,"localhost");
        HeartBeatManager heartBeatManager3 = new HeartBeatManager("localhost",2,8002);
        CombinedSystem system3 = new CombinedSystem(controller3,heartBeatManager3,sharedData3);
        new Thread(system3).start();

    }

}
