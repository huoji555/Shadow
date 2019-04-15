package SearchEngine;

import org.jsoup.nodes.Node;

import java.util.*;

/**
 * @Author Ragty
 * @Description 执行jsoup的深度优先遍历
 * @Date 11:00 2019/4/15
 **/
public class WikiNodeIterable implements Iterable<Node> {

    private  Node root;

    // 从给定的节点开始创造一个迭代器
    public WikiNodeIterable(Node root) {
        this.root = root;
    }

    @Override
    public Iterator<Node> iterator() {
        return new WikiNodeIterator(root);
    }


    //实现Iterator的内部类
    private class WikiNodeIterator implements Iterator<Node> {

        // 这个堆栈跟踪等待访问的节点
        Deque<Node>  stack;

        // 使用堆栈上的根节点初始化Iterator。
        public WikiNodeIterator(Node node) {
            stack = new ArrayDeque<Node>();
            stack.push(node);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Node next() {
            if (stack.isEmpty()) {
                throw new NoSuchElementException();
            }
            // 出栈
            Node node = stack.pop();
            // 反转后将child节点放入堆栈中
            List<Node> nodes = new ArrayList<Node>(node.childNodes());
            Collections.reverse(nodes);
            for (Node child : nodes) {
                stack.push(child);
            }
            return node;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }


}