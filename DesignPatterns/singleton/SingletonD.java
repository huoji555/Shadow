package DesignPatterns.singleton;

/*
 * @auther: Ragty
 * @describe: 静态内部类
 * @param:
 * @return:
 * @date: 2019/1/30
 */
public class SingletonD {

    private SingletonD() {}

    private static class singletonHolder {
        private static final SingletonD instance = new SingletonD();
    }

    public static SingletonD getInstance() {
        return singletonHolder.instance;
    }

}
