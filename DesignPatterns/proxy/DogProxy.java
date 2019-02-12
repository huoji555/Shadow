package proxy;

public class DogProxy implements Dog {

    Dog dog;

    public DogProxy() {
        dog = new DogImpl();
    }

    @Override
    public void eat() {
        System.out.println("静态代理开始");
        dog.eat();
        System.out.println("静态代理结束");
    }


}
