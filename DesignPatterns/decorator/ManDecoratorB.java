package DesignPatterns.decorator;

//具体装饰角色
public class ManDecoratorB extends Decorator{

    @Override
    public void eat() {
        super.eat();
        System.out.println("Decorator B ...");
    }
}
