package Map;

import java.util.List;
import java.util.Map;

public class MyHashMap<K,V> extends MyBetterMap<K,V> implements Map<K,V> {

    // 常数FACTOR（称为负载因子）确定每个子映射的平均最大条目数
    protected static final double FACTOR = 1.0;

    /**
     * @Author Ragty
     * @Description 重写put方法(放的时候，才会发生变化)，注意，父类put是常数时间，size()是线性时间，所以整体为线性时间(问题出在size上，不是最优解)
     * @Date 16:13 2019/4/23
     **/
    @Override
    public V put(K key, V value) {
        V oldValue = super.put(key,value);

        // 这个是由 n > k * FACTOR 确定的，这意味着n/k > FACTOR，意味着每个子映射的条目数超过阈值，所以我们调用rehash
        if (size()> maps.size() * FACTOR) {
            rehash();
        }
        return oldValue;
    }


    /**
     * @Author Ragty
     * @Description (扩容)收集表中的条目，调整表的大小，然后重新放入条目
     * @Date 16:33 2019/4/23
     **/
    protected void rehash() {
        // 扩容
        List<MyLinearMap<K,V>> oldMaps = maps;
        makeMaps(maps.size()*2);

        // 将旧的map放进去
        for (MyLinearMap<K,V> map: oldMaps) {
            for (Map.Entry<K, V> entry : map.getEntries()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }


    public static void main(String[] args) {
        Map<String, Integer> map = new MyHashMap<String, Integer>();
        for (int i=0; i<10; i++) {
            map.put(new Integer(i).toString(), i);
        }
        Integer value = map.get("5");
        System.out.println(value);
    }



}