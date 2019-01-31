package DesignPatterns.composite;

/**
 * @Auther: Ragty
 * @Date: 2019/1/31 16:54
 * @Description: 叶子构件（它在组合结构中表示叶子节点对象，叶子节点没有子节点，它实现了在抽象构件中定义的行为）
 */
public class Leaf extends Component{

    public Leaf(String name) {
        super(name);
    }

    @Override
    public void add(Component component) {
        System.out.println("A leaf could not add component");
    }

    @Override
    public void delete(Component component) {
        System.out.println("A leaf could not delete component");
    }

    @Override
    public void show(int index) {
        StringBuilder stringBuilder = new StringBuilder();

        for ( int i=0; i<index; i++ ) {
            stringBuilder.append("-");
        }
        System.out.println(stringBuilder.toString() + this.name);
    }

}
