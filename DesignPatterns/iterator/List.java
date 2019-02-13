package iterator;

public interface List {

    Iterator iterator();

    Object get(Integer index);

    Integer getSize();

    void add(Object object);

}
