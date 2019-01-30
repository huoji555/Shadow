package DesignPatterns.singleton;

/*
 * @auther: Ragty
 * @describe: 饿汉模式(线程安全，效率低) 类加载时就初始化
 * @param:
 * @return:
 * @date: 2019/1/30
 */
public class singletonA {

    private singletonA() {
    }

    private static final singletonA instance = new singletonA();

    public static singletonA getInstance() {
        return instance;
    }


}
