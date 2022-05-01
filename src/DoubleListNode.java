public class DoubleListNode {
    public Integer val;
    public DoubleListNode left;
    public DoubleListNode right;

    public DoubleListNode(Integer val, DoubleListNode left, DoubleListNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public DoubleListNode(Integer val) {
        this.val = val;
    }

    public static DoubleListNode reverseList(DoubleListNode head) {
        DoubleListNode p = null;
        DoubleListNode cur = head;
        while (cur != null) {
            DoubleListNode next = cur.right;
            cur.right = p;
            cur.left = next;
            p = cur;
            cur = next;
        }
        return p;
    }
    public static void show(DoubleListNode head){
        while(head!=null){
            System.out.println(head.val);
            head=head.right;
        }
    }
    public static void show1(DoubleListNode head){
        DoubleListNode h=head.right;
        while(h!=null){
            System.out.println(h.left.val);
            h=h.right;
        }
    }
    public static void main(String[] args) {
        DoubleListNode doubleListNode =new DoubleListNode(1);
        DoubleListNode doubleListNode2 =new DoubleListNode(2);
        DoubleListNode doubleListNode3 =new DoubleListNode(3);
        DoubleListNode doubleListNode4 =new DoubleListNode(4);
        DoubleListNode doubleListNode5 =new DoubleListNode(5);
        doubleListNode.right=doubleListNode2;
        doubleListNode2.left=doubleListNode;
        doubleListNode2.right=doubleListNode3;
        doubleListNode3.left=doubleListNode2;
        doubleListNode3.right=doubleListNode4;
        doubleListNode4.left=doubleListNode3;
        doubleListNode4.right=doubleListNode5;
        doubleListNode5.left=doubleListNode4;
//        System.out.println(doubleListNode2.left.val);
//        DoubleListNode doubleListNode1 = reverseList(doubleListNode);
        show1(doubleListNode);
    }
}
