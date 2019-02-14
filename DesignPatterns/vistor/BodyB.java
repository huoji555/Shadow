package vistor;

public class BodyB {

    public void seeABody(BodyA a){
        a.seeABody();
    }

    public void seeBBody(){
        System.out.println("I see B body");
    }

}
