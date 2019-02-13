package interpreter;

/**
 * @Auther: Ragty
 * @Description:  解释器模式 --> 提供了一种解释语言的语法或表达式的方式. 通过定义一个表达式接口,解释一个特定的上下文
 * @Date: 2019/2/13 10:17
 */
public class InterpreterPattern {

    public static void main(String[] args) {

        Context context = new Context();
        context.add(new SimpleExpression("1"));
        context.add(new AdvanceExpression("2"));
        context.add(new AdvanceExpression("3"));

        for (Expression ex : context.getList()) {
            ex.interpreter();
        }

    }


}
