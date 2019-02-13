package observer;

import java.util.ArrayList;
import java.util.List;

//被观察者服务
public class ObserverServer implements Observerable{

    private List<Observer> list;
    private String messgae;

    public ObserverServer() {
        list = new ArrayList<Observer>();
    }

    @Override
    public void add(Observer observer) {
        list.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        list.remove(observer);
    }

    @Override
    public void notifyObs() {
        for (int i=0; i<list.size(); i++) {
            Observer obs =  list.get(i);
            obs.update(messgae);
        }
    }


    public void setInformation(String messgae) {
        this.messgae = messgae;
        System.out.println("服务器更新消息");
        notifyObs();
    }


}
