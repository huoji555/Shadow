package iterator;

/**
 * @Auther: Ragty
 * @Description: 迭代器模式 --> 提供一种方法顺序访问一个聚合对象中的各种元素，而又不暴露该对象的内部表示。
 * @param:${param}
 * @Date: 2019/2/13 11:27
 */
public class IteratorPattern {

    public static void main(String[] args) {

        List list = new ListImpl();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        System.out.println("普通循环");
        for (int i=0; i<list.getSize(); i++) {
            System.out.println(list.get(i));
        }

        System.out.println("迭代器循环");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }


    }

}
