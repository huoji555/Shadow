package mediator;

public class ColleagueA extends Colleague {

    public ColleagueA(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void getMessage(String msg) {
        System.out.println(name + "收到消息:" + msg);
    }

    //与中介者通讯

    public void contact(String msg){
        mediator.contact(msg,this);
    }

}
