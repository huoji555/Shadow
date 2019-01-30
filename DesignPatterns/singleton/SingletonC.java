package DesignPatterns.singleton;

/*
 * @auther: Ragty
 * @describe: 单例模式的最优解(双重检测，线程安全)
 * @return:
 * @date: 2019/1/30 
 */
public class SingletonC {

    private SingletonC() {}

    //定义一个静态私有变量(不初始化，不使用final,使用volatile保证多线程访问时instance变量的可见性,避免instance初始化时其它属性还没赋完值时，被其它线程调用)
    private static volatile SingletonC instance;

    public static SingletonC getInstance() {
        if (instance == null) {
            //同步代码块(对象未初始化时，使用同步代码块，保证多线程访问对象在第一次创建后，不被重复创建)
            synchronized (SingletonC.class) {
                if (instance == null) {
                    instance = new SingletonC();
                }
            }
        }
        return instance;
    }



}
