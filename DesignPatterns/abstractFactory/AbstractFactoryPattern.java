package DesignPatterns.abstractFactory;

/**
 * @Auther: Ragty
 * @Date: 2019/1/30 15:06
 * @Description: 抽象工厂模式 --> 对一组具有相同主题的工厂进行封装
 * 例如：生产一台PC机，使用工厂方法模式的话，一般会有cpu工厂，内存工厂，显卡工厂...但是使用抽象工厂模式的话，只有一个工厂就是PC工厂，
 *      但是一个PC工厂涵盖了cpu工厂，内存工厂，显卡工厂等要做的所有事
 */
public class AbstractFactoryPattern {

    public static void main(String[] args) {
        DellFactory df = new DellFactory();
        df.createKeyboard().sayHi();
        df.createMouse().sayHi();

        HPFactory hp  = new HPFactory();
        hp.createMouse().sayHi();
        hp.createKeyboard().sayHi();

    }

}
