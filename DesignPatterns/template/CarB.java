package template;

public class CarB extends CarModel{

    @Override
    protected void start() {
        System.out.println("CarB start");
    }

    @Override
    protected void stop() {
        System.out.println("CarB stop");
    }
}
