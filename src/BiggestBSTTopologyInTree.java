import java.util.HashMap;
import java.util.Scanner;

public class BiggestBSTTopologyInTree {
    public static class Node{
        public int value;
        public Node left;
        public Node right;
        Node(int value){
            this.value = value;
        }
    }
    public static int bstTopoSize1(Node h){
        if (h == null) return 0;
        int max = maxTopo(h,h);
        max = Math.max(bstTopoSize1(h.left),max);
        max = Math.max(bstTopoSize1(h.right),max);
        return max;
    }
    public static int maxTopo(Node h,Node n){
        if(h != null && n != null && isBST(h,n)){
            return maxTopo(h,n.left) + maxTopo(h,n.right) + 1;
        }
        return 0;
    }
    public static boolean isBST(Node h,Node n){
        if (h == n) return true;
        if(h == null) return false;
        return isBST(h.value > n.value ? h.left : h.right,n);
    }
    public static class Record{
        public int l;
        public int r;
        Record(int left,int right){
            l = left;
            r = right;
        }
    }
    public static int bstTopoSize2(Node head){
        HashMap<Node,Record> map = new HashMap<>();
        return getMaxTopo(head,map);
    }
    public static int getMaxTopo(Node head,HashMap<Node,Record> map){
        if(head == null) return 0;
        int left = getMaxTopo(head.left,map);
        int right = getMaxTopo(head.right,map);
        modifyMap(head.value,head.left,map,true);
        modifyMap(head.value,head.right,map,false);
        Record l = map.get(head.left);
        Record r = map.get(head.right);
        int lr = l == null ? 0 : l.r + l.l + 1;
        int rr = r == null ? 0 : r.r + r.l + 1;
        Record record = new Record(lr,rr);
        map.put(head,record);
        return Math.max(lr + rr + 1,Math.max(left,right));
    }
    public static int modifyMap(int headValue,Node node,HashMap<Node,Record> map,boolean s){
        if (node == null || !map.containsKey(node)) return 0;
        Record record = map.get(node);
        if(s && headValue < node.value || (!s && headValue > node.value)){
            map.remove(node);
            return record.l + record.r + 1;
        }else{
            int m = modifyMap(headValue,s ? node.right : node.left,map,s);
            if (s){
                record.r = record.r - m;
            }else{
                record.l = record.l - m;
            }
            return m;
        }
    }
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }
    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }
    public static Node buildTree(Scanner sc, int n){
        HashMap<Integer,Node> map = new HashMap<>();
        int rt = sc.nextInt();
        Node root = new Node(rt);
        map.put(rt,root);
        for(int i = 0;i < n;i++){
            int rtv = sc.nextInt();
            int lv = sc.nextInt();
            int rv = sc.nextInt();
            if(map.containsKey(rtv)){
                Node r1 = map.get(rtv);
                r1.left = lv == 0 ? null : new Node(lv);
                r1.right = rv == 0 ? null : new Node(rv);
                if(lv != 0)
                    map.put(lv,r1.left);
                if(rv != 0)
                    map.put(rv,r1.right);
            }
        }
        return root;
    }
    public static void main(String[] args) {
        Node head = new Node(6);
        head.left = new Node(1);
        head.left.left = new Node(0);
        head.left.right = new Node(3);
        head.right = new Node(12);
        head.right.left = new Node(10);
        head.right.left.left = new Node(4);
        head.right.left.left.left = new Node(2);
        head.right.left.left.right = new Node(5);
        head.right.left.right = new Node(14);
        head.right.left.right.left = new Node(11);
        head.right.left.right.right = new Node(15);
        head.right.right = new Node(13);
        head.right.right.left = new Node(20);
        head.right.right.right = new Node(16);
//        printTree(head);
        System.out.println(bstTopoSize1(head));
        System.out.println(bstTopoSize2(head));
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Node root = buildTree(sc,n);
        System.out.println(bstTopoSize1(root));
    }
}