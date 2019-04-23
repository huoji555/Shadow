package Map;

import java.util.*;

/**
 * @Author Ragty
 * @Description 使用MyLinearMap集合实现Map,并用Hash值确定该进入哪个集合（主要依靠的是ChooseMap）
 * @Date 10:39 2019/4/23
 **/
public class MyBetterMap<K,V> implements Map<K,V> {

    // MyBetterMap uses a collection of MyLinearMap
    protected List<MyLinearMap<K,V>> maps;

    /**
     * @Author Ragty
     * @Description 使用两个子Map初始化Map（数量多时，可以调节这个，变得更快）
     * @Date 10:44 2019/4/23
     **/
    public MyBetterMap() {
        makeMaps(3);
    }


    /**
     * @Author Ragty
     * @Description 创造一个含有k个linearMap的集合
     * @Date 10:48 2019/4/23
     **/
    protected void makeMaps(int k) {
        maps = new ArrayList<MyLinearMap<K, V>>(k);
        for (int i=0; i<k; i++) {
            maps.add(new MyLinearMap<K, V>());
        }
    }


    /**
     * @Author Ragty
     * @Description 为一个给定的键选择正确的子映射（同makeMaps的初始化数量有关）
     * @Date 10:53 2019/4/23
     **/
    protected MyLinearMap<K,V> chooseMap(Object key) {
        int index = key==null ? 0 : Math.abs(key.hashCode()) % maps.size(); //这样会得到 0 - maps.size()之间的一个数作为查找对应子映射的值(玄学一样)
        return maps.get(index);
    }


    @Override
    public int size() {
        Integer total = 0;
        for (MyLinearMap map: maps)  {
            total += map.size();
        }
        return total;
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    /**
     * @Author Ragty
     * @Description 判断有无键，只需要找到一个子Map就可以
     * @Date 11:17 2019/4/23
     **/
    @Override
    public boolean containsKey(Object key) {
        MyLinearMap map = chooseMap(key);
        return map.containsKey(key);
    }


    /**
     * @Author Ragty
     * @Description 判断有无该值，需要遍历所有map
     * @Date 11:19 2019/4/23
     **/
    @Override
    public boolean containsValue(Object value) {
        for (MyLinearMap map: maps) {
            if (map.containsValue(value)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public V get(Object key) {
        MyLinearMap<K,V> map = chooseMap(key);
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        MyLinearMap<K,V> map = chooseMap(key);
        return map.put(key,value);
    }

    @Override
    public V remove(Object key) {
        MyLinearMap<K,V> map = chooseMap(key);
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry: m.entrySet()) {
            put(entry.getKey(),entry.getValue());
        }
    }

    @Override
    public void clear() {
        for (int i=0; i<maps.size(); i++) {
            maps.get(i).clear();
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();
        for (MyLinearMap map: maps) {
            set.addAll(map.keySet());
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        Set<V> set = new HashSet<V>();
        for (MyLinearMap map: maps) {
            set.addAll(map.values());
        }
        return set;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }


    public static void main(String[] args) {
        Map<String,Integer> map = new MyBetterMap<String, Integer>();
        map.put("ss",1);
        map.put("ds",2);
        map.put("kk",3);
        map.put("ff",4);

        for (String key: map.keySet()) {
            System.out.println(key+"  "+map.get(key));
        }

    }


}