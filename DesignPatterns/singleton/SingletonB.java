package DesignPatterns.singleton;

/*
 * @auther: Ragty
 * @describe: 懒汉模式(线程不安全)，改造后安全，第一次加载时会慢
 * @param:
 * @return:
 * @date: 2019/1/30
 */
public class SingletonB {

    //防止实例化
    private SingletonB(){
    }

    //定义一个变量(不初始化，这里没有final关键字)
    private static SingletonB instance;

    //定义一个静态方法(调用时再初始化对象，但是多线程操作时，可能造成重复初始化问题)
    //加锁(互斥锁)保证线程安全
    public static synchronized SingletonB getInstance() {

        if (instance == null) {
            instance == new SingletonB();
        }
        return instance;
    }


}
