package DesignPatterns.prototype;

/**
 * @Auther: Ragty
 * @Date: 2019/1/31 10:12
 * @Description: 声明克隆的自身接口(重写Cloneable的clone())
 */
public class ProtoType implements Cloneable{

    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
