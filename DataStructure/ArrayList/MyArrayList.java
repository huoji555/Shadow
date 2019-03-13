package ArrayList;

import javax.swing.text.Element;
import javax.swing.text.html.parser.Entity;
import java.util.*;

public class MyArrayList<T> implements List<T> {

    int size;
    private T[] array;

    //实例化一个数组
    public MyArrayList() {
        size = 0;
        array = (T[])new Object[10];
    }


    /**
     * @Author Ragty
     * @Description 采用摊销分析，n次添加，存储n个元素，复制n-2个元素(2*n-2),时间复杂度为(2-2/n),可以认为是常数级别的
     * @Date 17:12 2019/3/12
     **/
    @Override
    public boolean add(T element) {
        //make a bigger array and copy over the elements
        if (size >= array.length) {
            T[] bigger = (T[])new Object[array.length * 2];
            System.arraycopy(array,0,bigger,0,array.length);
            array = bigger;
        }
        array[size] = element;
        size++;
        return true;
    }



    /**
     * @Author Ragty
     * @Description add双参方法（先add单参，然后从下标处开始右移，最后把放到该放的位置）
     * @Date 9:55 2019/3/13
     **/
    @Override
    public void add(int index, T element) {
        if (index<0 || index>size) {
            throw new IndexOutOfBoundsException();
        }

        //new element（扩容之需）
        add(element);

        //shift the other element
        for (int i=size-1; i>index; i--) {
            array[i] = array[i-1];
        }

        //put the new one in the right place
        array[index] = element;
    }



    /**
     * @Author Ragty
     * @Description get方法（常数）
     * @Date 10:22 2019/3/13
     **/
    @Override
    public T get(int index) {
        if (index<0 || index>size) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }



    /**
     * @Author Ragty
     * @Description set方法，它不会显式的检查数组边界，而是利用了get(常数)
     * @Date 10:23 2019/3/13
     **/
    @Override
    public T set(int index, T element) {
        T old = get(index);
        array[index] = element;
        return old;
    }



    /**
     * @Author Ragty
     * @Description 获取当前元素的下表
     * @Date 10:32 2019/3/13
     **/
    @Override
    public int indexOf(Object o) {
        for (int i=0; i<size; i++) {
            if (equals(o,array[i])) {
                return i;
            }
        }
        return -1;
    }



    /**
     * @Author Ragty
     * @Description  判断目标元素和当前元素是否相等
     * @Date 10:36 2019/3/13
     **/
    private boolean equals(Object target,Object element) {
        if (target == null) {
            return element == null;
        }else {
            return target.equals(element);
        }
    }



    /**
     * @Author Ragty
     * @Description  移除元素
     * @Date 10:51 2019/3/13
     **/
    @Override
    public T remove(int index) {
        T old = get(index);
        for (int i=index; i<size-1; i++) {
            array[i] = array[i+1];
        }
        size--;
        return old;
    }



    /**
     * @Author Ragty
     * @Description 移除元素(多态)
     * @Date 10:58 2019/3/13
     **/
    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }


    /**
     * @Author Ragty
     * @Description 移除所有元素(时间复杂度不一定是n2,需要考虑它的规模)
     * @Date 11:06 2019/3/13
     **/
    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean flag = true;
        for (Object object : collection) {
            flag &= remove(object);
        }
        return flag;
    }


    /**
     * @Author Ragty
     * @Description 添加所有集合中的元素
     * @Date 11:13 2019/3/13
     **/
    @Override
    public boolean addAll(Collection<? extends T> collection) {
        boolean flag = true;
        System.out.println(collection.size());
        for (T element: collection) {
            flag &= add(element);
        }
        return flag;
    }


    /**
     * @Author Ragty
     * @Description 从指定位置开始，添加集合中的所有元素
     * @Date 11:14 2019/3/13
     **/
    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        boolean flag = true;
        int collectionSize = collection.size();
        for (T object : collection) {
            flag &= add(object);
        }

        for (int i=size-1; i>collectionSize+index-1; i--) {
            array[i] = array[i-collectionSize];
        }

        for (T element : collection) {
            array[index] = element;
            index++;
        }

        return flag;
    }


    /**
     * @Author Ragty
     * @Description  返回当前长度
     * @Date 15:49 2019/3/13
     **/
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object obj) {
        return indexOf(obj)!=-1;
    }


    public static void main(String[] args) {
        Collection<Integer> collection = new ArrayList<Integer>();
        collection.add(5);
        collection.add(6);
        collection.add(7);

        MyArrayList<Integer> list = new MyArrayList<Integer>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.addAll(2,collection);

        for (int i=0; i<list.size(); i++) {
            System.out.println(list.get(i));
        }

    }


}
