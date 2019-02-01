package DesignPatterns.decorator;

//具体构件角色（定义一个将要接受附加责任的类）
public class Man implements Person {

    @Override
    public void eat() {
        System.out.println("Man eating ...");
    }

}
