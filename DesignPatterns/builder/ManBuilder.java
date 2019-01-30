package DesignPatterns.builder;

//具体建造
public class ManBuilder implements PersonBuilder{

    Person person;

    public ManBuilder() {
        person = new Man();
    }



    @Override
    public void buildHead() { person.setHead("正在建造头..."); }

    @Override
    public void buildBody() { person.setBody("正在建造身体..."); }

    @Override
    public void buildFoot() { person.setFoot("正在建造脚..."); }

    @Override
    public Person buildPerson() { return person; }

}
