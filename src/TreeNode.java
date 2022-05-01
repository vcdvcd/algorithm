import java.util.*;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    //递归写先序遍历
    public static void preOrder(TreeNode root) {
        if (root == null)
            return;
        System.out.println(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }

    //迭代写先序遍历
    public static void preOrder2(TreeNode root) {
        if (root != null) {
            Stack<TreeNode> s = new Stack<>();
            s.push(root);
            while (!s.isEmpty()) {
                root = s.pop();
                System.out.println(root.val);
                if (root.right != null)
                    s.push(root.right);
                if (root.left != null)
                    s.push(root.left);
            }
        }
    }

    public static void preOrder3(TreeNode root) {
        if (root == null)
            return;
        Stack<TreeNode> s = new Stack<>();
        while (root != null || !s.isEmpty()) {
            while (root != null) {
                System.out.println(root.val);
                s.push(root);
                root = root.left;
            }
            root = s.pop();
            root = root.right;
        }
    }

    //递归写中序遍历
    public static void inOder(TreeNode root) {
        if (root == null)
            return;
        inOder(root.left);
        System.out.println(root.val);
        inOder(root.right);
    }

    //迭代写中序遍历
    public static void inOder2(TreeNode root) {
        if (root == null)
            return;
        Stack<TreeNode> s = new Stack<>();
        while (root != null || !s.isEmpty()) {
            while (root != null) {
                s.push(root);
                root = root.left;
            }
            root = s.pop();
            System.out.println(root.val);
            root = root.right;
        }
    }

    //递归写后序遍历
    public static void postOrder(TreeNode root) {
        if (root == null)
            return;
        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.val);
    }

    //迭代写后序遍历
    public static void postOrder2(TreeNode root) {
        if (root == null)
            return;
        Stack<TreeNode> s = new Stack<>();
        TreeNode pre = null;//记录已经遍历过的右子树
        while (root != null || !s.isEmpty()) {
            while (root != null) {//将左边界压栈
                s.push(root);
                root = root.left;
            }
            root = s.pop();
            //说明右子树已经遍历完或者没有右子树
            if (root.right == null || root.right == pre) {
                System.out.println(root.val);
                pre = root;//更新pre为新的右子树
                root = null;//让下次循环直接走栈中的节点。
            } else {
                s.push(root);//发现右子树没有遍历完。将当前节点重新压栈，避免遗漏。
                root = root.right;
            }
        }
    }

    //双栈写后序遍历
    public static void postOrder3(TreeNode root) {
        if (root != null) {
            Stack<TreeNode> s1 = new Stack<>();
            Stack<Integer> s2 = new Stack<>();
            s1.push(root);
            while (!s1.isEmpty()) {
                root = s1.pop();
                s2.push(root.val);
                if (root.left != null)
                    s1.push(root.left);
                if (root.right != null)
                    s1.push(root.right);
            }
            while (!s2.isEmpty()) {
                System.out.println(s2.pop());
            }
        }
    }

    //层次遍历
    public static void w(TreeNode root) {
        if (root == null)
            return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            root = queue.poll();
            System.out.println(root.val);
            if (root.left != null)
                queue.add(root.left);
            if (root.right != null)
                queue.add(root.right);
        }
    }

    //leetcode解二叉树最大宽度
    public static int widthOfBinaryTree2(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> q = new LinkedList();
        int max = 0;
        q.add(root);
        while (!q.isEmpty()) {
            int queueSize = q.size();
            for (int i = 0; i < queueSize; i++) {
                root = q.poll();
                max = Math.max(max, queueSize);
                if (root.left != null)
                    q.add(root.left);
                if (root.right != null)
                    q.add(root.right);
            }
        }
        return max;
    }

    //哈希表解二叉树最大宽度
    public static int widthOfBinaryTree3(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> q = new LinkedList<>();
        HashMap<TreeNode, Integer> levelMap = new HashMap<>();//哈希表记录每个节点的层数
        levelMap.put(root, 1);//初始化哈希表
        int curLevelNodes = 0;//当前层数节点数。
        int curLevel = 0;//记录当前节点的层数
        int curLevelNum = 1;//记录当前正在遍历的层数
        int max = 0;
        q.add(root);
        while (!q.isEmpty()) {
            root = q.poll();
            curLevel = levelMap.get(root);
            //如果当前节点的层数与当前正在遍历的层数相同，说明当前层还没有遍历完
            if (curLevel == curLevelNum) {
                curLevelNodes++;
            } else {//否则说明来到了下一层
                curLevelNum = curLevel;//更新当前正在遍历的层数
                max = Math.max(max, curLevelNodes);
                curLevelNodes = 1;//初始化为1，避免遗漏最开始的节点
            }
            if (root.left != null) {
                levelMap.put(root.left, curLevelNum + 1);//添加下一层的节点和它的层数
                q.add(root.left);
            }
            if (root.right != null) {
                levelMap.put(root.right, curLevelNum + 1);//添加下一层的节点和它的层数
                q.add(root.right);
            }
        }
        return Math.max(max, curLevelNodes);//避免遗漏最后一层的比较
    }

    //二叉树最大宽度（不采用哈希表）
    public static int widthOfBinaryTree(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> q = new LinkedList();
        TreeNode curEnd = root;//指向当前层的最后一个节点
        TreeNode nextEnd = null;//指向当前层下一层的最后一个节点
        int max = 0;
        int curLevelNodes = 0;//统计当前层数的节点个数
        q.add(root);
        while (!q.isEmpty()) {
            root = q.poll();
            //判断是否具有左子树和右子树
            if (root.left != null) {
                q.add(root.left);
                //只要入队列就赋值给nextEnd，这样可以保证nextEnd一直是下一层最后一个节点
                nextEnd = root.left;
            }
            if (root.right != null) {
                q.add(root.right);
                nextEnd = root.right;
            }
            //如果出队列的节点是当前层数最后一个节点，说明当前层已经到底了
            if (root == curEnd) {
                //max更新
                max = ++curLevelNodes;
                curLevelNodes = 0;
                //指向下一层最后一个节点
                curEnd = nextEnd;
                nextEnd = null;
            } else {
                curLevelNodes++;
            }
        }
        return max;
    }

    //leetcode662二叉树最大宽度（包括统计null）
    public static int widthOfBinaryTree4(TreeNode root) {
        if (root == null)
            return 0;
        Queue<MyNode> q = new LinkedList<>();
        MyNode head = new MyNode(root, 0);//定义一个带下标的数据结构
        q.offer(head);
        int max = 0;
        int maxIndex = 0;//每一层的最后一个节点的下标
        int minIndex = 0;//每一层第一个节点的下标
        while (!q.isEmpty()) {
            int queueSize = q.size();
            for (int i = 0; i < queueSize; i++) {
                head = q.poll();
                if (i == 0)
                    minIndex = head.index;//第一个节点的下标
                maxIndex = head.index;//更新maxIndex
                if (head.node.left != null)
                    q.add(new MyNode(head.node.left, head.index * 2));//左节点的下标为父节点的下标*2
                if (head.node.right != null)
                    q.add(new MyNode(head.node.right, head.index * 2 + 1));//右节点的下标为父节点的下标*2+1
            }
            //maxIndex-minIndex+1，就是一层的所有节点个数
            max = Math.max(max, maxIndex - minIndex + 1);//更新max
        }
        return max;
    }

    //leetcode98验证二叉搜索树(搜索二叉树是中序有序的)
    public static boolean isValidBST(TreeNode root) {
        return BST(root).isBST;
    }

    public static ReturnType BST(TreeNode root) {
        if (root == null)
            return null;
        ReturnType left = BST(root.left);
        ReturnType right = BST(root.right);
        int max = root.val;
        int min = root.val;
        if (left != null) {
            max = Math.max(max, left.max);
            min = Math.min(min, left.min);
        }
        if (right != null) {
            max = Math.max(max, right.max);
            min = Math.min(min, right.min);
        }
        boolean isBST = true;
        if (left != null && (!left.isBST || left.max > root.val))
            isBST = false;
        if (right != null && (!right.isBST || right.min < root.val))
            isBST = false;
        return new ReturnType(isBST, max, min);
    }

    public static class ReturnType {
        boolean isBST;
        int max;
        int min;

        public ReturnType(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }

    public static boolean isValidBST2(TreeNode root) {
        if (root == null)
            return true;
        Stack<TreeNode> s = new Stack<>();
        long max = Long.MIN_VALUE;
        while (root != null || !s.isEmpty()) {
            while (root != null) {
                s.push(root);
                root = root.left;
            }
            root = s.pop();
            if (root.val <= max)
                return false;
            max = Math.max(max, root.val);
            root = root.right;
        }
        return true;
    }

    //leetcode958二叉树的完整性检验
    //套用层次遍历的模板
    public static boolean isCompleteTree(TreeNode root) {
        if (root == null)
            return true;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        boolean flag = false;//用于标记第一次遇到了不完全的节点（左右子节点有一个不存在或者都不存在）
        while (!q.isEmpty()) {
            int queueSize = q.size();
            for (int i = 0; i < queueSize; i++) {
                root = q.poll();
                if ((root.left == null && root.right != null) //如果没有左子节点而又右子节点，那么必定不是完全二叉树
                        || (flag && (root.left != null || root.right != null)))//如果第一次遇到了不完全的节点，那么后面遇到的节点必须为叶节点
                    return false;
                if (root.left != null) {
                    q.add(root.left);
                }
                if (root.right != null) {
                    q.add(root.right);
                }
                if (root.left == null || root.right == null)//由于标记第一次遇到了不完全的节点
                    flag = true;
            }
        }
        return true;
    }

    //平衡二叉树
    //二叉树中任意节点的左右子树的深度相差不超过1，这就是平衡二叉树
    public static boolean isBalanced(TreeNode root) {
        return isBT(root).isBT;
    }
    public static ReturnData isBT(TreeNode root){
        if (root==null)
            return new ReturnData(true,0);
        ReturnData left =isBT(root.left);
        ReturnData right =isBT(root.right);
        boolean isBT = left.isBT && right.isBT && Math.abs(left.height-right.height)<2;
        int height =Math.max(left.height, right.height)+1;
        return new ReturnData(isBT,height);
    }
    //leetcode236最近公共祖先
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root==null || root==p || root==q)
            return root;
        TreeNode left =lowestCommonAncestor(root.left,p,q);
        TreeNode right =lowestCommonAncestor(root.right,p,q);
        if (left!=null && right!=null)
            return root;
        return left!=null?left:right;
    }
    public static class ReturnData {
        boolean isBT;
        int height;

        public ReturnData(boolean isBT, int height) {
            this.isBT = isBT;
            this.height = height;
        }
    }
    //leetcode297序列化二叉树与反序列化二叉树
    //第一种解法DFS（深度优先遍历即先序遍历）
    public static String serialize(TreeNode root) {
        if(root==null)
            return "None,";
        String res=root.val+",";
        res+=serialize(root.left);
        res+=serialize(root.right);
        return res;
    }
    public static TreeNode deserialize(String data) {
        String[] values = data.split(",");
        Queue<String> q =new LinkedList<>();
        for (int i = 0; i < values.length; i++) {
            q.add(values[i]);
        }
        return process(q);
    }
    public static TreeNode process(Queue<String> q){
        String value =q.poll();
        if("None".equals(value))
            return null;
        TreeNode root =new TreeNode(Integer.valueOf(value));
        root.left=process(q);
        root.right=process(q);
        return root;
    }
    //leetcode297序列化二叉树与反序列化二叉树
    //第二种解法BFS效率低于DFS
    public static String serialize2(TreeNode root) {
        if (root == null) {
            return "None,";
        }
        Queue<TreeNode> q =new LinkedList<>();
        String res="";
        q.add(root);
        while(!q.isEmpty()){
            root=q.poll();
            if (root!=null){
                res+=root.val+",";
                q.add(root.left);
                q.add(root.right);
            }
            else{
                res+="None,";
            }
        }
        return res;
    }
    public static TreeNode deserialize2(String data) {
        if (data.equals("None,"))
            return null;
        String[] values =data.split(",");
        Queue<TreeNode> q =new LinkedList<>();
        TreeNode root =new TreeNode(Integer.valueOf(values[0]));
        q.add(root);
        int j=1;
        while(j< values.length){
            TreeNode cur = q.poll();
            if (!values[j].equals("None")){
                cur.left =new TreeNode(Integer.valueOf(values[j]));
                q.add(cur.left);
            }
            if (!values[j+1].equals("None")){
                cur.right =new TreeNode(Integer.valueOf(values[j+1]));
                q.add(cur.right);
            }
            j+=2;
        }
        return root;
    }
    //二叉搜索树的中序后继节点
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode res =null;
        while(root!=null){
            if (root.val>p.val){
                res=root;
                root=root.left;
            }else{
                root=root.right;
            }
        }
        return res;
    }
    //微软原题 折纸问题
    public static void printProcess(int n){
        solution(1,n,true);//第一个参数表示当前对折次数，第二个参数表示一共对折多少次，flag如果为true，表示为凹痕，反之为凸痕
    }
    public static void solution(int i,int n,boolean flag){
        if (i>n)//终止条件，表示当前次数超出总共次数
            return;
        solution(i+1,n,true);//相当于对左子树进行递归
        System.out.println(flag?"凹":"凸");//中序遍历
        solution(i+1,n,false);//相当于对右子树进行递归
    }
    //二叉树节点间的最大距离问题
    public static class Info{
        int maxHeight;
        int maxDistance;

        public Info(int maxHeight, int maxDistance) {
            this.maxHeight = maxHeight;
            this.maxDistance = maxDistance;
        }
    }
    public static int maxDistance(TreeNode root){
        return solution(root).maxDistance;
    }
    public static Info solution(TreeNode root){
        if(root == null) return new Info(0,0);
        Info left = solution(root.left);
        Info right = solution(root.right);
        int maxHeight = Math.max(left.maxHeight,right.maxHeight) + 1;
        int maxDistance = Math.max(left.maxHeight + right.maxHeight + 1,Math.max(left.maxDistance,right.maxDistance));
        return new Info(maxHeight,maxDistance);
    }

    //leetcode337 打家劫舍III

    public static class Inf{
        int select;
        int unSelect;

        public Inf(int select, int unSelect) {
            this.select = select;
            this.unSelect = unSelect;
        }
    }
    public static int rob(TreeNode root){
        Inf i = f(root);
        return Math.max(i.select,i.unSelect);
    }
    public static Inf f(TreeNode root){
        if(root == null) return new Inf(0,0);
        Inf left = f(root.left);
        Inf right = f(root.right);
        int select = left.unSelect + right.unSelect + root.val;
        //这里选择和不选择 两种情况的最大值
        int unselect = Math.max(left.select,left.unSelect) + Math.max(right.select,right.unSelect);
        return new Inf(select,unselect);
    }
    //morris遍历
    public static void morris(TreeNode root){
        if(root == null) return;
        TreeNode cur = root;
        TreeNode mostRight = null;
        while(cur != null){
            mostRight = cur.left;
            if(mostRight != null){
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else{
                    mostRight.right = null;
                }
            }
            cur = cur.right;
        }
    }
    //morris先序遍历
    public static void morrisPre(TreeNode root){
        if(root == null) return;
        TreeNode cur = root;
        TreeNode mR = null;
        while(cur != null){
            mR = cur.left;
            if(mR != null){
                while(mR.right != null && mR.right != cur){
                    mR = mR.right;
                }
                if(mR.right == null){
                    System.out.println(cur.val);
                    mR.right = cur;
                    cur = cur.left;
                    continue;
                }
                else{
                    mR.right = null;
                }
            }else {
                System.out.println(cur.val);
            }
            cur = cur.left;
        }
    }
    //morris中序遍历
    public static void morrisInorder(TreeNode root){
        if(root == null) return;
        TreeNode cur = root;
        TreeNode mR = null;
        while(cur != null){
            mR = cur.left;
            if(mR != null){
                while(mR.right != null && mR.right != cur){
                    mR = mR.right;
                }
                if(mR.right == null){
                    mR.right = cur;
                    cur = cur.left;
                    continue;
                }else {
                    mR.right = null;
                }
            }
            System.out.println(cur.val);
            cur = cur.right;
        }
    }
    //morris后序遍历
    public static void morrisPost(TreeNode root){
        if(root == null) return;
        TreeNode cur = root;
        TreeNode mR = null;
        while(cur != null){
            mR = cur.left;
            if(mR != null){
                while(mR.right != null && mR.right != cur){
                    mR = mR.right;
                }
                if(mR.right == null){
                    mR.right = cur;
                    cur = cur.left;
                    continue;
                }else {
                    mR.right = null;
                    printAns(cur.left);
                }
            }
            cur = cur.right;
        }
        printAns(root);
    }
    public static TreeNode reverse(TreeNode root){
        TreeNode pre = null;
        TreeNode cur = root;
        while(cur != null){
            TreeNode next = cur.right;
            cur.right = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
    public static void printAns(TreeNode root){
        TreeNode tail = reverse(root);
        TreeNode cur = tail;
        while(cur != null){
            System.out.println(cur.val);
            cur = cur.right;
        }
        reverse(tail);
    }
    //===========================

    public static void morrisPre1(TreeNode root){
        if(root == null) return;
        TreeNode cur = root;
        TreeNode mR = null;
        while(cur != null){
            mR = cur.left;
            if(mR != null){
                while(mR.right != null && mR.right != cur){
                    mR = mR.right;
                }
                if(mR.right == null){
                    System.out.println(cur.val);
                    mR.right = cur;
                    cur = cur.left;
                    continue;
                }else{
                    mR.right = null;
                }
            }else{
                System.out.println(cur.val);
            }
            cur = cur.right;
        }
    }
    public static void morrisInorder1(TreeNode root){
        if(root == null) return;
        TreeNode cur = root;
        TreeNode mR = null;
        while(cur != null){
            mR = cur.left;
            if(mR != null){
                while(mR.right != null && mR.right != cur){
                    mR = mR.right;
                }
                if(mR.right == null){
                    mR.right = cur;
                    cur = cur.left;
                    continue;
                }else{
                    mR.right = null;
                }
            }
            System.out.println(cur.val);
            cur = cur.right;
        }
    }
    public static void morrisPost1(TreeNode root){
        if(root == null) return;
        TreeNode cur = root;
        TreeNode mR = null;
        while(cur != null){
            mR = cur.left;
            if(mR != null){
                while(mR.right != null && mR.right != cur){
                    mR = mR.right;
                }
                if (mR.right == null){
                    mR.right = cur;
                    cur = cur.left;
                    continue;
                }else{
                    mR.right = null;
                    printAns1(cur.left);
                }
            }
            cur = cur.right;
        }
        printAns1(root);
    }
    public static void printAns1(TreeNode root){
        TreeNode tail = reverse1(root);
        TreeNode cur = tail;
        while(cur != null){
            System.out.println(cur.val);
            cur = cur.right;
        }
        reverse1(tail);
    }
    public static TreeNode reverse1(TreeNode root){
        TreeNode pre = null;
        while(root != null){
            TreeNode next = root.right;
            root.right = pre;
            pre = root;
            root = next;
        }
        return pre;
    }
    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(4);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(6);
        TreeNode t4 = new TreeNode(1);
        TreeNode t5 = new TreeNode(3);
        TreeNode t6 = new TreeNode(5);
        TreeNode t7 = new TreeNode(7);
        t1.left = t2;
        t1.right = t3;
        t1.left.left = t4;
        t1.left.right = t5;
        t1.right.left = t6;
        t1.right.right = t7;
//        System.out.println(widthOfBinaryTree4(t1));
//        System.out.println("==========");
//        System.out.println(isValidBST2(t1));
//        System.out.println(isCompleteTree(t1));
//        printProcess(3);
        //System.out.println((serialize(t1)));
        //System.out.println(deserialize2(serialize2(t1)));
        //System.out.println(maxDistance(t1));
        //System.out.println(rob(t1));
        //morrisInorder1(t1);
        //morrisPre1(t1);
        morrisPost1(t1);
    }
}

class MyNode {
    TreeNode node;
    int index;

    public MyNode() {
    }

    public MyNode(TreeNode node, int index) {
        this.node = node;
        this.index = index;
    }
}