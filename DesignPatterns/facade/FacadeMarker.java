package DesignPatterns.facade;

public class FacadeMarker {

    ServiceA serviceA;
    ServiceB serviceB;
    ServiceC serviceC;

    public FacadeMarker() {
        serviceA = new ServiceAImpl();
        serviceB = new ServiceBImpl();
        serviceC = new ServiceCImpl();
    }


    public void methodA() {
        serviceA.methodA();
        serviceB.methodB();
    }

    public void methodB() {
        serviceB.methodB();
        serviceC.methodC();
    }

    public void methodC() {
        serviceC.methodC();
        serviceA.methodA();
    }


}
