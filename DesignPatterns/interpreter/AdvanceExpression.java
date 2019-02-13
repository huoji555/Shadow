package interpreter;

public class AdvanceExpression extends Expression{

    private String data;

    public AdvanceExpression(String data) {
        this.data = data;
    }

    @Override
    void interpreter() {
        System.out.println("这是一个高级解释器 ..."+data);
    }
}
