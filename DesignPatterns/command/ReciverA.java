package command;

public class ReciverA extends AbstractReciver{

    @Override
    void cook() {
        System.out.println("接收到命令A 并执行完毕");
    }
}
