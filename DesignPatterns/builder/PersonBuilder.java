package DesignPatterns.builder;

//抽象构造人
public interface PersonBuilder {

    void buildHead();
    void buildBody();
    void buildFoot();

    Person buildPerson();

}
