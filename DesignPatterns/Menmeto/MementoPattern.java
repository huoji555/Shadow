package Menmeto;

/**
 * @Auther: HASEE
 * @Description:备忘录模式 -----> 在不破坏封闭的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。
 *                               这样以后就可将该对象恢复到原先保存的状态
 * @Date: 2019/2/13 16:13
 */
public class MementoPattern {

    public static void main(String[] args) {


        Original original = new Original();
        original.setState("在洗澡...");
        original.showState();

        //状态进行保存
        CareTaker careTaker = new CareTaker();
        careTaker.setMenmeto(original.createMenmeto());

        //更换状态
        original.setState("在吃饭...");
        original.showState();

        //取出状态
        original.setMenmeto(careTaker.getMenmeto());
        original.showState();

    }

}
