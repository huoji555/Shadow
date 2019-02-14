package template;

/**
 * @Auther: Ragty
 * @Description:   模板模式 -----> 定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。
 * 模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。通俗的说的就是有很多相同的步骤的
 * 在某一些地方可能有一些差别适合于这种模式，如大话设计模式中说到的考试场景中
 * 每个人的试卷都是一样的，只有答案不一样。这种场景就适合于模板方法模式。我这次自己写的是一个汽车启动的过程
 * 每一种汽车启动的过程都基本是一样的流程，无非是这一过程中存在一些细小差别。
 * @Date: 2019/2/14 10:37
 */
public class TemplatePattern {

    public static void main(String[] args) {

        CarModel carA = new CarA();
        carA.excute();

        CarModel carB = new CarB();
        carB.excute();

    }

}
