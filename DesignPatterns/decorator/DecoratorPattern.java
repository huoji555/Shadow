package DesignPatterns.decorator;

/**
 * @Auther: Ragty
 * @Date: 2019/2/1 11:20
 * @Description: 装饰模式 -----> 对新房进行装修并没有改变房屋的本质，但它可以让房子变得更漂亮、更温馨、更实用。
 *              在软件设计中，对已有对象（新房）的功能进行扩展（装修）。 把通用功能封装在装饰器中，用到的地方进行调用。
 *               装饰模式是一种用于替代继承的技术，使用对象之间的关联关系取代类之间的继承关系。引入装饰类，扩充新功能。
 */
public class DecoratorPattern {

    public static void main(String[] args) {

        Man man = new Man();
        ManDecoratorA manDecoratorA = new ManDecoratorA();

        manDecoratorA.setPerson(man);
        manDecoratorA.eat();

    }


}
