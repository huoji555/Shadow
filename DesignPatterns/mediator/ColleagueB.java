package mediator;

public class ColleagueB extends Colleague{

    public ColleagueB(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void getMessage(String msg) {
        System.out.println(name + "收到消息:" + msg);
    }

    public void contact(String msg) {
        mediator.contact(msg,this);
    }

}
