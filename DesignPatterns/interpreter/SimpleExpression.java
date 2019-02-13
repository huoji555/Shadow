package interpreter;

public class SimpleExpression extends Expression{


    private String data;

    public SimpleExpression(String data) {
        this.data = data;
    }

    @Override
    void interpreter() {
        System.out.println("这是一个简单的解析器 ..."+data);
    }
}
