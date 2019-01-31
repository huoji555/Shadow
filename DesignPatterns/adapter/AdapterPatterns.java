package DesignPatterns.adapter;

/**
 * @Auther: Ragty
 * @Date: 2019/1/31 11:09
 * @Description: 适配器模式 (是一种适配中间件，它存在于不匹配的二者之间，用于连接二者，将不匹配变得匹配)
 */
public class AdapterPatterns {

    public static void main(String[] args) {
        Target target = new Adapter(new Adaptee());
        target.adapteeMethod();
        target.adapterMethod();
    }

}
