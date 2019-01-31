package DesignPatterns.adapter;

/**
 * @Auther: Ragty
 * @Date: 2019/1/31 11:01
 * @Description: 适配器(它可以调用另一个接口，作为一个转换器，对Adaptee和Target进行适配)
 */
public class Adapter implements Target{

    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    public void adapteeMethod() {
        adaptee.adapteeMethod();            //适配过程
    }

    @Override
    public void adapterMethod() {
        System.out.println("Adaptor Method ...");
    }
}
