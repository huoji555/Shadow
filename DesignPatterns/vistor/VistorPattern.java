package vistor;

/**
 * @Auther: HASEE
 * @Description: 访问者模式 -----> 表示一个作用于其对象结构中的各元素的操作，它使你可以在不改变各元素类的前提下定义作用于这些元素的新操作
 * @param:${param}
 * @Date: 2019/2/14 10:52
 */
public class VistorPattern {

    public static void main(String[] args) {

        BodyA bodyA = new BodyA();
        bodyA.seeABody();
        bodyA.seeBBody(new BodyB());

    }

}
