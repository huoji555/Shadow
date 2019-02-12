package command;

public class ActionB implements AbstractAction{

    private AbstractReciver abstractReciver;

    public ActionB(AbstractReciver abstractReciver) {
        this.abstractReciver = abstractReciver;
    }

    @Override
    public void excute() {
        System.out.println("命令B发布 ...");
        this.abstractReciver.cook();
    }
}
