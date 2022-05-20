import java.util.*;

public class Skill {
    //等概率返回1——>5
    public static int f(){
        return (int) (Math.random() * 5) + 1;
    }
    //将f()修改成等概率返回0或1
    public static int r01(){
        int res = 0;
        do {
            res = f();
        }while (res == 3);
        return res < 3 ? 0 : 1;
    }
    //等概率返回1——>7
    public static int g(){
        int res = 0;
        do {
            res = (r01() << 2) + (r01() << 1) + r01();
        }while (res == 7);
        return res + 1;
    }
    //===========以上将等概率返回1——>5修改成等概率返回1——>7==========
    //最长有效括号子串 leetcode32
    public static int longestValidParentheses(String s) {
        char[] chs = s.toCharArray();
        int n = chs.length;
        int[] dp = new int[n];//记录以第i位字符结尾的最长有效括号子串
        int max = 0;
        for(int i = 1;i < n;i++){
            if(chs[i] == ')'){
                int index = i - dp[i - 1] - 1;
                if(index >= 0 && chs[index] == '('){
                    dp[i] = dp[i - 1] + (index - 1 >= 0 ? dp[index - 1] : 0) + 2;
                }
            }
            max = Math.max(max,dp[i]);
        }
        return max;
    }
    //二维数组查找数字
    public static boolean findNumberIn2DArray(int[][] matrix, int target) {
        int n = matrix.length;
        if(n == 0) return false;
        int m = matrix[0].length;
        int row = 0;
        int col = m - 1;
        while(row < n && col >= 0){
            if(matrix[row][col] > target){
                col--;
            }
            else if(matrix[row][col] < target){
                row++;
            }
            else
                return true;
        }
        return false;
    }
    //螺旋输出矩阵
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        int n = matrix.length;
        int m = matrix[0].length;
        int a = 0;
        int b = 0;
        int c = n - 1;
        int d = m - 1;
        while(a <= c && b <= d){
            if(a == c){
                for(int i = b;i <= d;i++){
                    ans.add(matrix[a][i]);
                }
            }else if(b == d){
                for(int i = a;i <= c;i++){
                    ans.add(matrix[i][b]);
                }
            }else{
                int a1 = a;
                int b1 = b;
                while(b1 != d){
                    ans.add(matrix[a][b1++]);
                }
                while(a1 != c){
                    ans.add(matrix[a1++][d]);
                }
                while(b1 != b){
                    ans.add(matrix[c][b1--]);
                }
                while(a1 != a){
                    ans.add(matrix[a1--][b]);
                }
            }
            a++;
            b++;
            c--;
            d--;
        }
        return ans;
    }
    //洗衣机问题 leetcode517
    public static int findMinMoves(int[] machines) {
        int sum = 0;
        int n = machines.length;
        for(int i = 0;i < n;i++){
            sum += machines[i];
        }
        if(sum % n != 0) return -1;
        int target = sum / n;
        int leftSum = 0;
        int ans = 0;
        for(int i = 0;i < n;i++){
            int leftNeed = leftSum - i * target;
            int rightNeed = (sum - leftSum - machines[i]) - (n - i - 1) * target;
            if(leftNeed < 0 && rightNeed < 0){
                ans = Math.max(ans,Math.abs(leftNeed) + Math.abs(rightNeed));
            }
            else{
                ans = Math.max(ans,Math.max(Math.abs(leftNeed),Math.abs(rightNeed)));
            }
            leftSum += machines[i];
        }
        return ans;
    }
    //顺时针旋转90°输出正方形矩阵
    public static void rotate(int[][] matrix){
        int n = matrix.length;
        int m = matrix[0].length;
        int x1 = 0,y1 = 0,x2 = n - 1,y2 = m - 1;
        while(x1 < x2){
            printRotate(matrix,x1++,y1++,x2--,y2--);
        }
    }
    public static void printRotate(int[][] matrix,int x,int y,int c,int d){
        for(int i = 0;i < d - y;i++){
            int tmp = matrix[x][y + i];
            matrix[x][y + i] = matrix[c - i][y];
            matrix[c - i][y] = matrix[c][d - i];
            matrix[c][d - i] = matrix[x + i][d];
            matrix[x + i][d] = tmp;
        }
    }
    //曲折遍历矩阵
    public static void zigzag(int[][] matrix){
        int x1 = 0,x2 = 0,y1 = 0,y2 = 0;
        int endR = matrix.length - 1;
        int endC = matrix[0].length - 1;
        boolean flag = true;
        while(x1 != endR + 1){
            printZigzag(matrix,x1,y1,x2,y2,flag);
            //下面四个顺序不能乱了
            x1 = y1 == endC ? x1 + 1 : x1;
            y1 = y1 == endC ? y1 : y1 + 1;
            y2 = x2 == endR ? y2 + 1 : y2;
            x2 = x2 == endR ? x2 : x2 + 1;
            flag = !flag;
        }
    }
    public static void printZigzag(int[][] matrix,int x1,int y1,int x2,int y2,boolean f){
        if(f){
            while(x2 != x1 - 1){
                System.out.print(matrix[x2--][y2++] + " ");
            }
        }else{
            while(x1 != x2 + 1){
                System.out.print(matrix[x1++][y1--] + " ");
            }
        }
        System.out.println();
    }
    //leetcode 650 只有两个键的键盘
    public static int minSteps(int n) {
        int i = 2;
        int ans = 0;
        while(i <= n){
            while(n % i == 0){
                ans += i;
                n /= i;
            }
            i++;
        }
        return ans;
    }
    //动态的topK问题 返回词频最大的前K个字符串
    //思路：手写堆
    public static class Node{
        private String str;
        private int cnt;
        Node(String str,int cnt){
            this.str = str;
            this.cnt = cnt;
        }
    }
    public static class TopK{
        private HashMap<String,Node> numMap;
        private HashMap<Node,Integer> indexMap;
        private Node[] heap;
        private int size;
        TopK(int K){
            numMap = new HashMap<>();
            indexMap = new HashMap<>();
            heap = new Node[K];
            size = 0;
        }
        public void add(String str){
            Node cur = null;
            int preIndex = -1;
            if(!numMap.containsKey(str)){
                cur = new Node(str,1);
                numMap.put(str,cur);
                indexMap.put(cur,-1);
            }else {
                cur = numMap.get(str);
                cur.cnt++;
                preIndex = indexMap.get(cur);
            }
            if(preIndex == -1){
                if(size == heap.length){
                    if(cur.cnt > heap[0].cnt){
                        heap[0] = cur;
                        indexMap.put(cur,0);
                        indexMap.put(heap[0],-1);
                        heapify(0,size);
                    }
                }else{
                    heap[size] = cur;
                    indexMap.put(cur,size);
                    heapInsert(size++);
                }
            }else{
                heapify(preIndex,size);
            }
        }

        public void heapify(int index,int heapSize) {
            int left = index * 2 + 1;
            while(left < heapSize){
                int smallest = left + 1 < heapSize &&
                        heap[left + 1].cnt < heap[left].cnt ? left + 1 : left;
                smallest = heap[smallest].cnt < heap[index].cnt ? smallest : index;
                if(smallest == index) break;
                swap(heap[smallest],heap[index]);
                index = smallest;
                left = index * 2 + 1;
            }
        }
        public void heapInsert(int index){
            while(heap[index].cnt < heap[(index - 1) / 2].cnt){
                swap(heap[index],heap[(index - 1) / 2]);
                index = (index - 1) / 2;
            }
        }
        public void swap(Node n1,Node n2){
            String s1 = n1.str;
            String s2 = n2.str;
            int i1 = indexMap.get(s1);
            int i2 = indexMap.get(s2);
            heap[i1] = n2;
            heap[i2] = n1;
            indexMap.put(n1,i2);
            indexMap.put(n2,i1);
        }
    }
    //用栈实现队列
    public static class StackQueue{
        public Stack<Integer> pushStack;
        public Stack<Integer> popStack;
        StackQueue(){
            pushStack = new Stack<>();
            popStack = new Stack<>();
        }
        public void add(int n){
            pushStack.add(n);
            isEmpty();
        }
        public void isEmpty(){
            if(popStack.isEmpty()){
                while(!pushStack.isEmpty()){
                    popStack.push(pushStack.pop());
                }
            }
        }
        public int pop(){
            if(popStack.isEmpty() && pushStack.isEmpty()){
                throw new RuntimeException("Stack is Empty!");
            }
            isEmpty();
            return popStack.pop();
        }
    }
    //用队列实现栈
    public static class MyStack {
        private Queue<Integer> q1;
        private Queue<Integer> q2;
        private boolean flag;
        private int top;
        public MyStack() {
            q1 = new LinkedList<>();
            q2 = new LinkedList<>();
            flag = true;
        }
        public void push(int x) {
            if(flag){
                q1.offer(x);
                top = x;
            }else{
                q2.offer(x);
                top = x;
            }
        }
        public int pop() {
            if (flag) {
                top = q1.peek();
                while (q1.size() != 1) {
                    top = q1.poll();
                    q2.add(top);
                }
                flag = false;
                return q1.poll();
            } else {
                top = q2.peek();
                while (q2.size() != 1) {
                    top = q2.poll();
                    q1.add(top);
                }
                flag = true;
                return q2.poll();
            }
        }
        public int top() {
            return top;
        }
        public boolean empty() {
            return q1.isEmpty() && q2.isEmpty();
        }
    }
    //leetcode 42 接雨水
    //双指针
    public static int trap(int[] height) {
        int n = height.length;
        int R = n - 1;
        int L = 1;
        int leftMax = height[0];
        int rightMax = height[n - 1];
        int ans = 0;
        while(L <= R){
            leftMax = Math.max(height[L],leftMax);
            rightMax = Math.max(height[R],rightMax);
            if(leftMax >= rightMax){
                ans += rightMax - height[R--];
            }else if(leftMax < rightMax){
                ans += leftMax - height[L++];
            }
        }
        return ans;
    }
    //给定一个数组arr,长度为N，你可以把任意长度大于0且小于N的前缀作为左部分，剩下的作为右部分。
    //但是每种划分下都有左部分的最大值和右部分的最大值，请返回最大的，左部分最大值减去右部分最大值的绝对值。
    public static int dividerMax(int[] arr){
        int n = arr.length;
        int max = Integer.MIN_VALUE;
        for(int i = 0;i < n;i++){
            max = Math.max(max,arr[i]);
        }
        return Math.max(max - arr[n - 1],max - arr[0]);
    }
    //有一道关于KMP的算法题，复习以后再来 （中级5 time 1：04:31）

    //咖啡杯问题（很难的一道题）
    //数组arr：表示几个咖啡机，这几个咖啡机生产一杯咖啡所需要的时间就是数组中的值，
    //例如arr=[2,3,7]就表示第一台咖啡机生产一杯咖啡需要2单位时间，第二台需要3单位时间，第三台需要7单位时间。
    //int N：表示有N个人需要用咖啡机制作咖啡，每人一杯，同时，假设制作完咖啡后，喝咖啡时间为0，一口闷。
    //int a：表示用洗碗机洗一个咖啡杯需要的时间，串行运行。
    //int b：表示咖啡杯也可以不洗，自然晾干的时间，咖啡杯要么洗，要么晾干。
    //现在，请你求出这N个人从开始用咖啡杯制作咖啡到杯子洗好或者晾干的最少时间？
    //思路：小根堆 + 动态规划
    //先写个暴力版本
    public static class CaffeMachine{
//        public
    }
    //斐波那契数列的优化（类似的存在严格递推式的都可以优化成O（logN））
    //原理：矩阵相乘 + 快速幂
    public static int fibonacciPlus(int n){
        if(n == 1 || n == 2) return 1;
        int[][] t = {{1,1},{1,0}};
        int[][] res = getMatrix(t,n - 2);
        return res[0][0] + res[0][1];
    }
    //矩阵的快速幂
    public static int[][] getMatrix(int[][] matrix,int n){
        int[][] t = matrix;
        int[][] res = {{1,0},{0,1}};
        while(n > 0){
            if(n % 2 != 0) {
                res = matrixMultiply(res, t);
            }
            n /= 2;
            t = matrixMultiply(t,t);
        }
        return res;
    }
    //矩阵相乘
    public static int[][] matrixMultiply(int[][] m1,int[][] m2){
        int[][] res = new int[m1.length][m2[0].length];
        for(int i = 0;i < m1.length;i++){
            for(int j = 0;j < m2[0].length;j++){
                for(int k = 0;k < m1[0].length;k++){
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, map.getOrDefault(1, 0) + 1);
        int[][] m = {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}};
        int[][] m1 = {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}};
        rotate(m);
        zigzag(m1);
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(fibonacciPlus(11));
    }
}
