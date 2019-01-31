package DesignPatterns.composite;

/**
 * @Auther: Ragty
 * @Date: 2019/1/31 16:51
 * @Description: 抽象构件（在该角色中可以包含所有子类共有行为的声明和实现）
 */
public abstract class Component {

    protected String name;

    public Component(String name) {
        this.name = name;
    }

    public abstract void add(Component component);
    public abstract void delete(Component component);
    public abstract void show(int index);

}
