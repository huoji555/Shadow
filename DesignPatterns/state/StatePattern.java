package state;

/**
 * @Auther: Ragty
 * @Description: 状态模式  ---> 在状态模式中，我们创建表示各种状态的对象和一个行为随着状态对象改变而改变的 context 对象
 * @Date: 2019/2/14 08:54
 */
public class StatePattern {

    public static void main(String[] args) {

        Context context = new Context();
        Rain rain = new Rain();
        context.setState(rain);
        System.out.println(context.stateMessage());

        System.out.println("----------");

        Sunshine sunshine = new Sunshine();
        context.setState(sunshine);
        System.out.println(context.stateMessage());

    }


}
