package observer;

//被观察者接口    --- 提供添加，删除，通知观察者方法
public interface Observerable {

    void add(Observer observer);

    void remove(Observer observer);

    void notifyObs();

}
