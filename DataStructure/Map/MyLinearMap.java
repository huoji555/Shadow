package Map;

import java.util.*;

/**
 * @Author Ragty
 * @Description 用List实现的简单Map （大部分操作都市线性时间）
 * @Date 11:34 2019/4/22
 **/
public class MyLinearMap<K,V>implements Map<K,V> {

    private List<Entry> entries = new ArrayList<Entry>();

    public class Entry implements Map.Entry<K,V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            this.value = newValue;
            return value;
        }
    }


    /**
     * @Author Ragty
     * @Description 比较大小
     * @Date 11:59 2019/4/22
     **/
    private boolean equals(Object target,Object obj) {
        if (target == null) {
            return obj == null;
        }
        return  target.equals(obj);
    }


    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public V get(Object key) {
        Entry entry = findEntry(key);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    @Override
    public V put(K key, V value) {
        Entry entry = findEntry(key);
        if (entry == null) {
            entries.add(new Entry(key,value));
            return  null;
        } else {
            V oldValue = entry.getValue();
            entry.setValue(value);
            return oldValue;
        }
    }

    @Override
    public V remove(Object key) {
        Entry entry = findEntry(key);
        if (entry == null) {
            return null;
        }
        V oldValue = entry.getValue();
        entries.remove(entry);
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry: m.entrySet()) {
            put(entry.getKey(),entry.getValue());
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();
        for (Entry entry: entries) {
            set.add(entry.getKey());
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return findEntry(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Entry entry: entries) {
            if (equals(value,entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Author Ragty
     * @Description 返回对应key值的Entry
     * @Date 11:54 2019/4/22
     **/
    private Entry findEntry(Object target) {
        for(Entry entry:entries) {
            if (equals(target,entry.getKey())) {
                return entry;
            }
        }
        return  null;
    }

    @Override
    public void clear() {
        entries.clear();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }


    public static void main(String[] args) {
        Map<String,Integer> map = new MyLinearMap<String, Integer>();
        map.put("dsd",1);
        map.put("gff",2);
        map.put("dsds",3);

        map.remove("gff");

        for (String key: map.keySet()) {
            System.out.println(key+"     "+map.get(key));
        }

    }


	protected Collection<? extends java.util.Map.Entry<K, V>> getEntries() {
		return entries;
	}

}