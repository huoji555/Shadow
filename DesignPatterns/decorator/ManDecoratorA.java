package DesignPatterns.decorator;

//具体装饰角色
public class ManDecoratorA extends Decorator {

    @Override
    public void eat() {
        super.eat();
        reEat();
        System.out.println("DecoratorA ...");
    }

    public void reEat() {
        System.out.println("Eat again ...");
    }

}
