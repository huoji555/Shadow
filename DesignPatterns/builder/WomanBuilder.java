package DesignPatterns.builder;

public class WomanBuilder implements PersonBuilder {

    Person person;

    public WomanBuilder() {person = new Woman();}

    @Override
    public void buildHead() { person.setHead("构造女孩的大脑"); }

    @Override
    public void buildBody() { person.setBody("构造女孩的身体"); }

    @Override
    public void buildFoot() { person.setFoot("构造女孩的脚"); }

    @Override
    public Person buildPerson() { return person; }

}
