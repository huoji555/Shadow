package LinkedList;

import javax.xml.soap.Node;
import java.util.*;

public class MyLinkedList<E> implements List<E> {

    private int size;           // keep track of the number of elements
    private Node head;          // reference to the first node

    public MyLinkedList() {
        this.size = 0;
        this.head = null;
    }

    //内嵌函数（Nested Function set the Node）
    private class Node {
        public E data;
        public Node next;

        public Node() {
            this.data = null;
            this.next = null;
        }

        public Node(E data) {
            this.data = data;
            this.next = null;
        }

        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }

    }

    public static void main(String[] args) {
       Collection<Integer> c = new ArrayList<Integer>();
       c.add(1);
       c.add(2);
       c.add(3);

       MyLinkedList<Integer> list = new MyLinkedList<Integer>();
       list.addAll(0,c);
        System.out.println(list.size());
        System.out.println(list.indexOf(3));

    }


    /**
     * @Author Ragty
     * @Description Add单参版本
     * @Date 11:09 2019/3/20
     **/
    @Override
    public boolean add( E element) {
        if (head == null) {
            head = new Node(element);
        } else {
            Node node = head;
            for (; node.next != null; node = node.next) {}      //loop until the next one
            node.next = new Node(element);
        }
        size++;
        return true;
    }


    /**
     * @Author Ragty
     * @Description Add双参版本
     * @Date 11:42 2019/3/20
     **/
    @Override
    public void add(int index, E element) {
        if (head == null) {
            Node node = new Node(element);
        } else {
            Node node = getNode(index-1);
            node.next = new Node(element,node.next);
        }
        size++;
    }


    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean flag = true;
        for (E element : c) {
            flag &= add(element);
        }
        return flag;
    }


    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (head == null) {
            Object[] a = c.toArray();
            head = new Node((E) a[0]);
            size++;
            for (int i=1; i<a.length; i++) {
                System.out.println("xxx");
                add((E) a[i]);
            }
        } else {
            Node node = getNode(index-1);
            for (E element : c) {
                add(element);
            }
        }
        return false;
    }


    /**
     * @Author Ragty
     * @Description 辅助方法，获取当前下表的元素
     * @Date 14:33 2019/3/20
     **/
    private Node getNode(int index){
        if (index<0 || index>=size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = head;
        for (int i=0; i<index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public E remove(int index) {
        E element = get(index);
        if (index == 0) {
            head = head.next;
        } else {
            Node node = getNode(index-1);
            node.next = node.next.next;
        }
        size--;
        return element;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }


    @Override
    public int indexOf(Object target) {
        Node node = head;
        for (int i=0; i<size; i++) {
            if ( equals(target,node.data) ) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }


    private boolean equals(Object target, Object element) {
        if (target == null) {
            return element == null;
        } else {
            return target.equals(element);
        }
    }

    @Override
    public E get(int index) {
        Node node = getNode(index);
        return node.data;
    }

    @Override
    public E set(int index, E element) {
        Node node = getNode(index);
        E old = node.data;
        node.data = element;
        return old;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean flag = true;
        for (Object obj: collection) {
            flag &= remove(obj);
        }
        return flag;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0 ? true:false;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }


    @Override
    public boolean contains(Object o) {
        return indexOf(o)!=-1 ? true : false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean result = true;
        for (Object o : c) {
            result &= contains(o);
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        E[] array = (E[])toArray();
        return Arrays.asList(array).iterator();
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[size];
        int i = 0;
        for (Node node = head; node.next!=null; node = node.next) {
            objects[i] = node.data;
            i++;
        }
        return objects;
    }



    @Override
    public <E> E[] toArray(E[] a) {
        return null;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

}