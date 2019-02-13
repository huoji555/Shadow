package mediator;

/**
 * @Auther: HASEE
 * @Description: 中介者模式 -----> 用一个中介对象来封装一系列的对象交互。中介者使各对象不需要显式地相互引用，
 *                               从而使其耦合松散，而且可以独立地改变它们之间的交互。
 * @param:${param}
 * @Date: 2019/2/13 15:21
 */
public class MeditorPattern {

    public static void main(String[] args) {
        ConcreateMeditor meditor = new ConcreateMeditor();

        ColleagueA colleagueA = new ColleagueA("A",meditor);
        ColleagueB colleagueB = new ColleagueB("B",meditor);

        meditor.setColleagueA(colleagueA);
        meditor.setColleagueB(colleagueB);

        colleagueA.contact("hello A!");
        colleagueB.contact("hi B!");


    }

}
