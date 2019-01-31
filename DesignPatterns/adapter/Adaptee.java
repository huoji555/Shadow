package DesignPatterns.adapter;

/**
 * @Auther: Ragty
 * @Date: 2019/1/31 10:58
 * @Description: 适配者(它定义了一个已经存在的接口，这个接口需要适配，适配者类包好了客户希望的业务方法)
 */
public class Adaptee {

    public void adapteeMethod() {
        System.out.println("Adaptee Metho ...");
    }

}
