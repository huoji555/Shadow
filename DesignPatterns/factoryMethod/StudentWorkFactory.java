package DesignPatterns.factoryMethod;

public class StudentWorkFactory implements IWorkFactory{

    @Override
    public work getWork() {
        return new studentWork();
    }
}
