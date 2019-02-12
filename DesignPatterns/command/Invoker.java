package command;

public class Invoker {

    private AbstractAction abstractAction;

    public void setAbstractAction(AbstractAction abstractAction) {
        this.abstractAction = abstractAction;
    }

    public void action() {
        this.abstractAction.excute();    //执行命令
    }

}
