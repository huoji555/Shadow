package DesignPatterns.builder;

//建造者
public class PersonDirector {

    public Person constructorPerson(PersonBuilder pb) {
        pb.buildHead();
        pb.buildBody();
        pb.buildFoot();

        return pb.buildPerson();
    }

}
