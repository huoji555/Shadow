package template;

public class CarA extends CarModel {

    @Override
    protected void start() {
        System.out.println("CarA start");
    }

    @Override
    protected void stop() {
        System.out.println("CarA stop");
    }
}
