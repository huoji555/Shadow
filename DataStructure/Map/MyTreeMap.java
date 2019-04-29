package Map;

import java.util.*;

public class MyTreeMap<K,V> implements Map<K,V> {

    private int size = 0;
    private Node root = null;


    /**
     * @Author Ragty
     * @Description 树节点结构
     * @Date 10:33 2019/4/26
     **/
    protected class Node {
        public K key;
        public V value;
        public Node left = null;
        public Node right = null;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


    @Override
    public void clear() {
        size = 0;
        root = null;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * @Author Ragty
     * @Description 寻找节点(从根节点开始,迭代，因为每次都赋值)
     * @Date 10:44 2019/4/26
     **/
    private Node findNode(Object target) {
        if (target == null) {
            throw new IllegalArgumentException();
        }

        @SuppressWarnings("unchecked")
        Comparable<?super K> k = (Comparable<?super K>)target;

        Node node = root;
        while (node != null) {
            int cmp = k.compareTo(node.key);
            if (cmp <0) {
                node = node.left;
            }else if (cmp>0) {
                node = node.right;
            }else {
                return node;
            }
        }
        return null;
    }


    /**
     * @Author Ragty
     * @Description 判断两值是否相等
     * @Date 11:33 2019/4/26
     **/
    private boolean equals(Object target,Object obj) {
        if (target == null) {
            return obj == null;
        }
        return target.equals(obj);
    }

    /**
     * @Author Ragty
     * @Description 寻找树里是不是有对应的值(递归)
     * @Date 11:11 2019/4/26
     **/
    private boolean containsValueHelper(Node node, Object target) {
        if (node == null) {
            return  false;
        }
        if (equals(target,node.value)) {
            return true;
        }
        if (containsValueHelper(node.left,target)) {
            return true;
        }
        if (containsValueHelper(node.right,target)) {
            return true;
        }
        return false;
    }


    /**
     * @Author Ragty
     * @Description 按顺序将key值添加进set
     * @Date 11:31 2019/4/26
     **/
    private void addInOrder(Node node,Set<K> set) {
        if (node == null){return;}
        addInOrder(node.left,set);
        set.add(node.key);
        addInOrder(node.right,set);
    }


    /**
     * @Author Ragty
     * @Description 按顺序put,比较大小，根据大小，分别添加到左子树，或者右子树(这里还是用递归的方法)
     * @Date 11:46 2019/4/26
     **/
    private V putHelper(Node node,K key,V value) {
        Comparable<?super K> k = (Comparable<?super K>)key;
        int cmp = k.compareTo(node.key);

        if (cmp<0) {
            if (node.left == null) {
                node.left = new Node(key,value);
                size++;
                return null;
            } else {
                return putHelper(node.left,key,value);
            }
        }

        if (cmp>0) {
            if (node.right == null) {
                node.right = new Node(key,value);
                size++;
                return null;
            } else {
                return putHelper(node.right,key,value);
            }
        }

        V oldValue = node.value;
        node.value = value;
        return oldValue;
    }


    @Override
    public boolean containsKey(Object key) {
        return findNode(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsValueHelper(root,value);
    }

    @Override
    public V get(Object key) {
        Node node = findNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (root == null) {
            root = new Node(key,value);
            size++;
            return null;
        }
        return putHelper(root,key,value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry: m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * @Author Ragty
     * @Description 获取keySet,这里用LinkedHashSet主要是它处理好了排序问题
     * @Date 17:13 2019/4/26
     **/
    @Override
    public Set<K> keySet() {
        Set<K> set = new LinkedHashSet<K>();
        addInOrder(root,set);
        return set;
    }

    /**
     * @Author Ragty
     * @Description 这个也是中序遍历
     * @Date 18:03 2019/4/26
     **/
    @Override
    public Collection<V> values() {
        Set<V> set = new HashSet<V>();
        Deque<Node> stack = new LinkedList<Node>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node == null) continue;
            set.add(node.value);
            stack.push(node.left);
            stack.push(node.right);
        }
        return set;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw  new UnsupportedOperationException();
    }

    @Override
    public V remove(Object key) {
        // 一会儿我来填这个坑
        Node node = findNode(key);
        if (node == null) {
            return null;
        }
        V oldValue = node.value;
        deleteNode(node);
        return oldValue;
    }

    private void deleteNode(Node node) {
        size--;
        // waiting...

    }


    public static void main(String[] args) {
        Map<String, Integer> map = new MyTreeMap<String, Integer>();
        map.put("Word1", 1);
        map.put("Word3", 3);
        map.put("Word4", 7);
        map.put("Word2", 5);

        System.out.println(map.containsKey("Word1"));
        System.out.println(map.containsValue(5));

        for (String key: map.keySet()) {
            System.out.println(key + ", " + map.get(key));
        }

        for (Integer value: map.values()) {
            System.out.println(value);
        }
    }

}