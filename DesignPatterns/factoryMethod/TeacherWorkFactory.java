package DesignPatterns.factoryMethod;

public class TeacherWorkFactory implements IWorkFactory{

    @Override
    public work getWork() {
        return new teacherWork();
    }
}
