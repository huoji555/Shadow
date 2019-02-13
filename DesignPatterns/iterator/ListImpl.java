package iterator;

public class ListImpl implements List{

    private Object[] list;
    private Integer index;
    private Integer size;

    public ListImpl() {
        this.list = new Object[100];
        this.index = 0;
        this.size = 0;
    }

    @Override
    public Iterator iterator() {
        return new IteratorImpl(this);
    }

    @Override
    public Object get(Integer index) {
        return list[index];
    }

    @Override
    public Integer getSize() {
        return this.size;
    }

    @Override
    public void add(Object object) {
        list[index++] = object;
        size++;
    }
}
