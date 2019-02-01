package DesignPatterns.decorator;

//装饰类（持有一个构件对象的实例，并定义一个与抽象构件接口一致的接口）
public abstract class Decorator implements Person{

    protected Person person;

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public void eat() {
        person.eat();
    }
}
