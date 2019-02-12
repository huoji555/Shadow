package command;

public class ActionA implements AbstractAction{

    private AbstractReciver abstractReciver;

    public ActionA(AbstractReciver abstractReciver) {
        this.abstractReciver = abstractReciver;
    }

    @Override
    public void excute() {
        System.out.println("发布命令A ...");
        this.abstractReciver.cook();
    }
}
