package LinkedList;

public class ListNode {

    public Object node;
    public ListNode next;

    //init Node
    public ListNode() {
        this.node = null;
        this.next = null;
    }

    //usually use as the head
    public ListNode(Object node) {
        this.node = node;
        this.next = null;
    }


    //normal node
    public ListNode(Object node, ListNode next) {
        this.node = node;
        this.next = next;
    }


    @Override
    public String toString() {
        return "ListNode{" + "node=" + node + ", next=" + next + '}';
    }

}