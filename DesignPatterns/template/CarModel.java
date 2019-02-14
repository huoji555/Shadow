package template;

public abstract class CarModel {

    protected abstract void start();

    protected abstract void stop();

    final public void excute() {
        start();
        stop();
    }

}
