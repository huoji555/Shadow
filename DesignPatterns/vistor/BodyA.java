package vistor;

public class BodyA {

    public void seeABody() {
        System.out.println("I see A Body");
    }

    public void seeBBody(BodyB b) {
        b.seeBBody();
    }

}
