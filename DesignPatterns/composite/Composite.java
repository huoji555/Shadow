package DesignPatterns.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Ragty
 * @Date: 2019/1/31 17:02
 * @Description: 容器构件(表示容器节点对象，容器节点包含子节点，其子节点可以是叶子节点，也可以是容器节点
 *                      它提供一个集合用于存储子节点，实现了在抽象构件中定义的行为，包括那些访问及管理子构件的方法，在其业务方法中可以递归调用其子节点的业务方法)
 */
public class Composite extends Component {

    List<Component> list = new ArrayList<Component>();

    public Composite(String name) {
        super(name);
    }

    @Override
    public void add(Component component) {
        list.add(component);
    }

    @Override
    public void delete(Component component) {
        list.remove(component);
    }

    @Override
    public void show(int index) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<index; i++) {
            stringBuilder.append("-");
        }
        System.out.println(stringBuilder.toString()+ this.name);

        for(Component component : list) {
            component.show(index + 2);
        }
    }
}
