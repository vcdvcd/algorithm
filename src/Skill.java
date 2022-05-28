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
    public static int KMP(String s1,String s2){
        if(s2.length() < 1)
            return 0;
        if(s1 == null || s2 == null || s2.length() > s1.length())
            return -1;
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] nextArr = getNextArr(str2);
        int i = 0;
        int j = 0;
        while(i < str1.length && j < str2.length){
            if(str1[i] == str2[j]){
                i++;
                j++;
            }else if(j == 0){
                i++;
            }else{
                j = nextArr[j];
            }
        }
        return j == str2.length ? i - j : -1;
    }
    public static int[] getNextArr(char[] str){
        if(str.length == 1)
            return new int[]{-1};
        int[] res = new int[str.length];
        res[0] = -1;
        res[1] = 0;
        int i = 2;
        int j = 0;
        while(i < str.length){
            if(str[i - 1] == str[j]){
                res[i++] = ++j;
            }else if(j == 0){
                res[i++] = 0;
            }else{
                j = res[j];
            }
        }
        return res;
    }
    public static boolean isValid(String s1,String s2){
        String s = s1 + s1;
        return KMP(s, s2) != -1;
    }
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
        public int finishTime;
        public int time;
        CaffeMachine(int t){
            finishTime = t;
            time = t;
        }
    }
    public static class CaffeComparator implements Comparator<CaffeMachine>{
        @Override
        public int compare(CaffeMachine o1, CaffeMachine o2) {
            return (o1.finishTime + o1.time) - (o2.finishTime + o2.time);
        }
    }
    public static int f(int[] arr,int a,int b,int N){
        PriorityQueue<CaffeMachine> queue = new PriorityQueue<>(new CaffeComparator());
        for(int i = 0;i < arr.length;i++){
            queue.add(new CaffeMachine(arr[i]));
        }
        CaffeMachine caffeMachine;
        int[] finish = new int[N];
        for(int i = 0;i < N;i++){
            caffeMachine = queue.poll();
            finish[i] = caffeMachine.finishTime;
            caffeMachine.finishTime += caffeMachine.time;
            queue.add(caffeMachine);
        }
        return f2(finish,a,b,0,0);
    }
    public static int f2(int[] finish,int a,int b,int index,int washTime){
        if(index == finish.length - 1){
            return Math.min(Math.max(finish[index],washTime) + a,finish[index] + b);
        }
        //newTime 是当前的咖啡杯，决定放到洗咖啡杯的机器上去洗完的时间
        int newTime = Math.max(finish[index],washTime) + a;
        //洗完剩余咖啡杯的最早时间
        int next1 = f2(finish,a,b,index + 1,newTime);
        //比较当前咖啡杯洗完的时间和剩余咖啡杯洗完的时间
        int p1 = Math.max(newTime,next1);
        //当前杯子决定挥发
        int dry = finish[index] + b;
        int next2 = f2(finish,a,b,index + 1,washTime);
        int p2 = Math.max(dry,next2);
        return Math.min(p1,p2);
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
    //分配工作问题
    public static class Job{
        public int money;
        public int hard;
        Job(int m,int h){
            money = m;
            hard = h;
        }
    }
    //将任务以难度从小到大，相同难度的任务以报酬从大到小排序
    public static class JobComparator implements Comparator<Job>{
        @Override
        public int compare(Job o1, Job o2) {
            return o1.hard == o2.hard ? o2.money - o1.money : o1.hard - o2.hard;
        }
    }
    public static int[] pay(Job[] jobarr,int[] p){
        Arrays.sort(jobarr,new JobComparator());
        TreeMap<Integer,Integer> map = new TreeMap<>();
        int[] res = new int[p.length];
        Job pre = jobarr[0];
        map.put(jobarr[0].hard,jobarr[0].money);
        for(int i = 1;i < jobarr.length;i++){
            if(jobarr[i].hard != pre.hard && jobarr[i].money > pre.money){
                pre = jobarr[i];
                map.put(pre.hard,pre.money);
            }
        }
        for(int i = 0;i < p.length;i++){
            res[i] = map.floorKey(p[i]) == null ? 0 : map.floorKey(p[i]);
        }
        return res;
    }
    //给定一个字符串，如果该字符串符合人们日常书写一个整数的形式，返回int类型的这个数，如果越界或不符合输出-1
    public static int convert(String s){
        if(s == null || s.equals("")) return -1;
        char[] chs = s.toCharArray();
        if(!isValid(chs)){
            throw new RuntimeException("can't convert");
        }
        int d = Integer.MIN_VALUE;
        int c = d / 10;
        int m = d % 10;
        int ans = 0;
        boolean f = chs[0] == '-';
        for(int i = f ? 1 : 0;i < chs.length;i++){
            int a = '0' - chs[i];
            //防止溢出
            if(ans < c || (ans == c && a < m))
                throw new RuntimeException("cant' convert");
            ans = ans * 10 + a;
        }
        if(!f && ans == Integer.MIN_VALUE)
            throw new RuntimeException("can't convert");
        return f ? ans : -ans;
    }
    public static boolean isValid(char[] s){
        if(s[0] != '-' && (s[0] < '0' || s[0] > '9')) return false;
        if(s[0] == '-' && s.length == 1) return false;
        if(s[0] == '-' && s[1] == '0') return false;
        if(s[0] == '0' && s.length > 1) return false;
        for(int i = 1;i < s.length;i++){
            if(s[i] < '0' || s[i] > '9') return false;
        }
        return true;
    }
    //给你一个字符串数组arr
    //例如String[] arr = {"b\\cst","d\\","a\\d\\e","a\\b\\c"} 把这个目录结构打印出来，子目录在父目录的下面右进两个空格
    public static class PreTree{
        public String name;
        public TreeMap<String,PreTree> nexts;
        PreTree(String s){
            name = s;
            nexts = new TreeMap<>();
        }
    }
    public static void build(PreTree node,String[] arr){
        PreTree cur = node;
        for(String s : arr){
            if(!cur.nexts.containsKey(s)){
                cur.nexts.put(s,new PreTree(s));
            }
            cur = cur.nexts.get(s);
        }
    }
    public static void printD(String[] arr){
        PreTree node = new PreTree("");
        for(String s : arr) {
            build(node,s.split("\\\\"));
        }
        printAns(node,0);
    }
    public static void printAns(PreTree node,int i){
        TreeMap<String, PreTree> nexts = node.nexts;
        if(nexts.size() == 0) return;
        for(PreTree preTree : nexts.values()){
            String res = "";
            for(int j = 0;j < i * 2;j++){
                res += " ";
            }
            System.out.println(res + preTree.name);
            printAns(preTree,i + 1);
        }
    }
    //二叉搜索树转换成头尾相连的双向链表
    //leetcode 剑指offer 36
    public static class Info{
        public DoubleListNode start;
        public DoubleListNode end;
        Info(DoubleListNode s,DoubleListNode e){
            start = s;
            end = e;
        }
    }
    public static Info f2(DoubleListNode x){
        if(x == null){
            return new Info(null,null);
        }
        Info leftInfo = f2(x.left);
        Info rightInfo = f2(x.right);
        DoubleListNode start;
        DoubleListNode end;
        if(leftInfo.end != null){
            leftInfo.end.right = x;
        }
        x.left = leftInfo.end;
        x.right = rightInfo.start;
        if(rightInfo.start != null){
            rightInfo.start.left = x;
        }
        start = leftInfo.start == null ? x : leftInfo.start;
        end = rightInfo.end == null ? x : rightInfo.end;
        return new Info(start,end);
    }
    public DoubleListNode treeToDoublyList(DoubleListNode root) {
        if(root == null) return null;
        Info info = f2(root);
        info.start.left = info.end;
        info.end.right = info.start;
        return info.start;
    }
    //leetcode 53 最大子数组和
    public static int maxSubArray(int[] nums) {
        int n = nums.length;
        int cur = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            cur += nums[i];
            max = Math.max(max,cur);
            cur = cur < 0 ? 0 : cur;
        }
        return max;
    }
    //最大子数组和的变种题目。求矩形的最大子矩阵,返回最大值
    public static int maxSumMatrix(int[][] ma){
        int n = ma.length;
        int m = ma[0].length;
        int max = Integer.MIN_VALUE;
        int cur;
        int[] cnt;
        for (int i = 0; i < n; i++) {
            cnt = new int[m];
            for (int j = i; j < n; j++) {
                cur = 0;
                for (int k = 0; k < m; k++) {
                    cnt[k] += ma[j][k];
                    cur += cnt[k];
                    max = Math.max(max,cur);
                    cur = cur < 0 ? 0 : cur;
                }
            }
        }
        return max;
    }
    //s中只有"."或者"X"两种字符
    //路灯可以影响左中右三个位置
    //至少需要多少灯，可以把"."都点亮
    public static int minLight(String s){
        if(s == null || s.equals("")) return 0;
        char[] chs = s.toCharArray();
        int light = 0;
        int i = 0;
        while(i < chs.length){
            if(chs[i] == 'X'){
                i++;
            }else{
                light++;//默认放在i位置上
                if(i + 1 == chs.length) break;
                else {//如果i+1不越界
                    if (chs[i + 1] == 'X') {
                        i += 2;
                    } else {//贪心：把灯放在i+1位置上，这样就可以跳过i+2
                        i += 3;
                    }
                }
            }
        }
        return light;
    }
    //已知一棵没有重复节点的二叉树，提供它的前序数组和中序数组，给出它的后序数组
    public static int[] posTree(int[] pre,int[] in){
        HashMap<Integer,Integer> map = new HashMap<>();
        int N = pre.length;
        int[] pos = new int[N];
        for(int i = 0; i < N; i++){
            map.put(in[i],i);
        }
        buildPos(pre,in,pos,0,N - 1,0,N - 1,0,N - 1,map);
        return pos;
    }
    public static void buildPos(int[] pre,int[] in,int[] pos,int prei,int prej,int ini,int inj,int posi,int posj,HashMap<Integer,Integer> map){
        if(prei > prej) return;//如果左边界大于右边界，返回
        if(prei == prej){//只有一个数值，直接填写
            pos[posj] = pre[prei];
            return;
        }
        pos[posj] = pre[prei];//先序数组的第一个肯定是后序数组的最后一个
        int find = map.get(pre[prei]);//找到中序数组中根节点的位置，左侧就是左子树，右侧就是右子树
        buildPos(pre,in,pos,prei + 1,prei + find - ini,ini,find - 1,posi,posi + find - ini - 1,map);//左子树递归
        buildPos(pre,in,pos,prei + find - ini + 1,prej,find + 1,inj,posi + find - ini,posj - 1,map);//右子树递归
    }
    //完全二叉树节点个数
    public static int countNodes(TreeNode root){
        if(root == null) return 0;
        return findH(root,1,countH(root,1));
    }
    //h是整棵树的深度，level是当前root节点处于的深度
    public static int findH(TreeNode root,int level,int h){
        //如果来到了最底层，说明来到了叶节点，直接返回1
        if(level == h) return 1;
        //如果右子树的左节点最深和整棵树的深度一样，说明整棵树的左子树是满二叉树，因此只需要递归右子树即可。
        if(countH(root.right,level + 1) == h)
            return (1 << (h - level)) + findH(root.right,level + 1,h);
        else//否则说明右子树是满二叉树，并且右子树的深度只比整棵树的深度少1，因为整棵树是完全二叉树，因此只需要递归左子树即可
            return (1 << (h - level - 1)) + findH(root.left,level + 1,h);
    }
    public static int countH(TreeNode root,int level){
        while(root != null){
            level++;
            root = root.left;
        }
        return level - 1;
    }
    //最长递增子序列（最优解O(NlogN)）
    public static int lengthOfLIS(int[] nums) {
        int len = 1,n = nums.length;
        if(n == 0) return 0;
        int[] ends = new int[n];//ends[i]表示长度为i的子序列的最小末尾元素,并且得出的ends数组是个递增数组
        ends[len] = nums[0];
        for(int i = 1; i < n;i++){
            if(ends[len] < nums[i]){
                ends[++len] = nums[i];
            }else{
                int left = 1,right = len,pos = 0;
                //二分查找大于nums[i]位于ends数组最右侧的数值位置
                while(left <= right){
                    int mid = (left + right) >> 1;
                    if(ends[mid] < nums[i]){
                        pos = mid;
                        left = mid + 1;
                    }else{
                        right = mid - 1;
                    }
                }
                ends[pos + 1] = nums[i];
            }
        }
        return len;
    }
    //给定一个整数数组A，长度为n，有 1<= A[i] <= n ，且对于【1，n】的整数，其中部分元素会重复出现或者不出现，找出不出现的元素
    public static void findNumNotInArray(int[] arr){
        for(int i : arr){
            while(arr[i - 1] != i){
                int t = arr[i - 1];
                arr[i - 1] = i;
                i = t;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] != i + 1) System.out.print(i + 1 + " ");
        }
    }
    //给定一个布尔表达式，由0、1、&、|和^等符号组成，以及一个想要的布尔结果result，实现一个函数，算出有几种括号的放法可使该表达式
    //递归版本会超时
    public static int express(String s,boolean desired){
        if(s == null || s.equals("")) return 0;
        if(!isValid(s)) return 0;
        return process(s.toCharArray(),0,s.length() - 1,desired);
    }
    //dp版本
    public static int expressDp(String s,int result){
        int n = s.length();
        char[] chs = s.toCharArray();
        int[][][] dp = new int[n][n][2];
        for(int i = 0; i < n;i += 2){
            dp[i][i][0] = chs[i] == '0' ? 1 : 0;
            dp[i][i][1] = chs[i] == '1' ? 1 : 0;
        }
        for(int i = n - 3; i >= 0 ;i -= 2){
            for(int j = i + 2;j < n; j += 2){
                for(int k = i + 1; k < j;k += 2){
                    if (chs[k] == '&') {
                        dp[i][j][1] += dp[i][k - 1][1] * dp[k + 1][j][1];
                        dp[i][j][0] += dp[i][k - 1][0] * dp[k + 1][j][1];
                        dp[i][j][0] += dp[i][k - 1][1] * dp[k + 1][j][0];
                        dp[i][j][0] += dp[i][k - 1][0] * dp[k + 1][j][0];
                    } else if (chs[k] == '|') {
                        dp[i][j][1] += dp[i][k - 1][1] * dp[k + 1][j][0];
                        dp[i][j][1] += dp[i][k - 1][0] * dp[k + 1][j][1];
                        dp[i][j][1] += dp[i][k - 1][1] * dp[k + 1][j][1];
                        dp[i][j][0] += dp[i][k - 1][0] * dp[k + 1][j][0];
                    } else {
                        dp[i][j][1] += dp[i][k - 1][1] * dp[k + 1][j][0];
                        dp[i][j][1] += dp[i][k - 1][0] * dp[k + 1][j][1];
                        dp[i][j][0] += dp[i][k - 1][0] * dp[k + 1][j][0];
                        dp[i][j][0] += dp[i][k - 1][1] * dp[k + 1][j][1];
                    }
                }
            }
        }
        return dp[0][n - 1][result];
    }
    public static int process(char[] str,int L,int R,boolean f){
        if(L == R){
            if(f)
                return str[L] == '1' ? 1 : 0;
            else
                return str[L] == '0' ? 1 : 0;
        }
        int res = 0;
        if (f) {
            for (int i = L + 1; i < R; i += 2) {
                switch (str[i]) {
                    case '&':
                        res += process(str, L, i - 1, true) * process(str, i + 1, R, true);
                        break;
                    case '|':
                        res += process(str, L, i - 1, true) * process(str, i + 1, R, false);
                        res += process(str, L, i - 1, false) * process(str, i + 1, R, true);
                        res += process(str, L, i - 1, true) * process(str, i + 1, R, true);
                        break;
                    case '^':
                        res += process(str, L, i - 1, false) * process(str, i + 1, R, true);
                        res += process(str, L, i - 1, true) * process(str, i + 1, R, false);
                        break;
                }
            }
        }
        else {
            for (int i = L + 1; i < R; i += 2) {
                switch (str[i]) {
                    case '&':
                        res += process(str, L, i - 1, false) * process(str, i + 1, R, true);
                        res += process(str, L, i - 1, true) * process(str, i + 1, R, false);
                        res += process(str, L, i - 1, false) * process(str, i + 1, R, false);
                        break;
                    case '|':
                        res += process(str, L, i - 1, false) * process(str, i + 1, R, false);
                        break;
                    case '^':
                        res += process(str, L, i - 1, true) * process(str, i + 1, R, true);
                        res += process(str, L, i - 1, false) * process(str, i + 1, R, false);
                        break;
                }
            }
        }
        return res;
    }
    //最长无重复字符的子串
    public static int findUnique(String s){
        if(s == null || s.equals("")) return 0;
        char[] chs = s.toCharArray();
        int[] map = new int[256];//记录每个字符上次出现的位置
        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }
        int len = 0;
        int pre = -1;
        int cur = 0;
        for (int i = 0; i < chs.length; i++) {
            pre = Math.max(pre,map[chs[i]]);//pre取上次该字符出现的位置和上个字符上次出现的位置的较大值（也就是距离i最近的位置）
            //取较小值是不合法的
            cur = i - pre;//求出长度
            len = Math.max(len,cur);
            map[chs[i]] = i;//更新上次出现的位置
        }
        return len;
    }
    public static boolean isValid(String s){
        char[] chs = s.toCharArray();
        if((chs.length & 1) == 0) return false;
        for(int i = 0; i < chs.length; i += 2){
            if(chs[i] != '0' && chs[i] != '1')
                return false;
        }
        for(int i = 1;i < chs.length; i += 2){
            if(chs[i] != '&' && chs[i] != '|' && chs[i] != '^')
                return false;
        }
        return true;
    }
    //编辑距离 leetcode 72
    public static int minDistance(String word1, String word2){
        char[] s1 = word1.toCharArray();
        char[] s2 = word2.toCharArray();
        int n = s1.length;
        int m = s2.length;
        //dp数组表示word1前i位字符串转换为word2的前j位字符串所需要的最小步骤
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= m; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                //如果最后一位都相等，那么就只需要看s1的前i-1位和s2的前j-1位。不需要任何增，删，替换操作
                if(s1[i - 1] == s2[j - 1]){
                    dp[i][j] = dp[i - 1][j - 1];
                }else{
                    //如果不等的话，就要考虑增删和替换操作
                    //dp[i - 1][j] + 1 :表示s1的前i-1位替换成s2的前j位所需要的步骤，在加上删除s1的第i位这个操作
                    //dp[i][j - 1] + 1 ：表示s1的前i位替换成s2的前j-1位所需要的步骤，在加上增加s2的第j位这个操作
                    //dp[i - 1][j - 1] + 1：表示s1的前i-1位替换成s2的前j-1位所需要的步骤，
                    // 在加上替换s1的第i位变成s2的第j位这个操作
                    //取上面三者的最小值
                    //所有的增删替换都是针对s1。
                    dp[i][j] = Math.min(dp[i - 1][j],Math.min(dp[i][j - 1],dp[i - 1][j - 1])) + 1;
                }
            }
        }
        return dp[n][m];
    }
    //去除重复字母
    public static String removeDuplicateLetters(String s){
        char[] chs = s.toCharArray();
        int n = chs.length;
        int[] cnt = new int[26];
        boolean[] f = new boolean[26];
        Deque<Character> stack = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < n;i++){
            cnt[chs[i] - 'a']++;
        }
        for (int i = 0; i < n; i++) {
            if(f[chs[i] - 'a']) continue;
            cnt[chs[i] - 'a']--;
            while(!stack.isEmpty() && stack.peek() > chs[i]){
                int cur = stack.peek() - 'a';
                if(cnt[cur] == 0) break;
                stack.poll();
                f[cur] = false;
            }
            stack.push(chs[i]);
            f[chs[i] - 'a'] = true;
        }
        while(!stack.isEmpty()){
            sb.append(stack.poll());
        }
        return sb.reverse().toString();
    }
    //对字母表产生的所有长度不超过6的升序字符串按照字典排列编码如下：a(1),b(2),c(3)......z(26),ab(27),ac(28)......
    //对于任意长度不超过16的升序字符串，返回其编码
    //长度为len的字符串有多少个
    public static int g(int len){
        int res = 0;
        for (int i = 1; i <= 26; i++) {
            res += f(i,len);
        }
        return res;
    }
    //以ASCII为i的字母开头，长度为len的字符串有多少个
    public static int f(int i,int len){
        if(len == 1) return 1;
        int res = 0;
        for(int j = i + 1; j <= 26; j++){
            res += f(j,len - 1);
        }
        return res;
    }
    public static int code(String s){
        char[] chs = s.toCharArray();
        int n = chs.length;
        int res = 0;
        if(n - 1 == 0) return chs[0] - 'a' + 1;
        for (int i = 1; i < n; i++) {
            res += g(i);
        }
        for(int i = 1;i < chs[0] - 'a' + 1;i++){
            res += f(i,n);
        }
        for (int i = 0; i < n - 1; i++) {
           int cur = chs[i] - 'a' + 1;
            for (int j = cur + 1; j < chs[i + 1] - 'a' + 1; j++) {
                res += f(j,n - i - 1);
            }
        }
        return res + 1;
    }
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, map.getOrDefault(1, 0) + 1);
        int[][] m = {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}};
        int[][] m1 = {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}};
//        rotate(m);
//        zigzag(m1);
//        for (int i = 0; i < m.length; i++) {
//            for (int j = 0; j < m[0].length; j++) {
//                System.out.print(m[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println(fibonacciPlus(11));
//        System.out.println(isValid("12345", "51234"));
//        String[] arr = {"b\\cst","d\\","a\\d\\e","a\\b\\c"};
//        printD(arr);
//        System.out.println(convert("2147483649"));
//        System.out.println(minLight(".X.X.X.X.X.X.X.X.X."));
//        int[] pre = {1,2,4,5,3,6,7};
//        int[] in = {4,2,5,1,6,3,7};
//        Arrays.stream(posTree(pre, in)).forEach(System.out::print);
//        findNumNotInArray(pre);
//        System.out.println(express("0^1|1|0&0&0&1^0&0&0^1|0&1",
//               true));
//        System.out.println(expressDp("0^1|1|0&0&0&1^0&0&0^1|0&1",1));
        System.out.println(minDistance("intention",
                "execution"));
        System.out.println(findUnique("1324afasdasd"));
        System.out.println(removeDuplicateLetters("cbacdcbc"));
        System.out.println(code("az"));
    }
}
//中文表示数字
class NumToCN{
    public static String[] singles = {"一","二","三","四","五","六","七","八","九"};
    public static String num1to99(int n) {
        StringBuilder res = new StringBuilder();
        if (n < 10) return singles[n - 1];
        int d = n / 10;
        int m = n % 10;
        if (m == 0)
            if (d == 1)
                res.append("十");
            else
                res.append(singles[d - 1] + "十");
        else
            res.append(singles[d - 1] + "十" + singles[m - 1]);
        return res.toString();
    }
    public static String num1to999(int n){
        StringBuilder res = new StringBuilder();
        if(n < 10) return singles[n - 1];
        else if(n < 100) return num1to99(n);
        else{
            int d = n / 100;
            int m = n % 100;
            res.append(singles[d - 1] + "百");
            if(m == 0) return res.toString();
            else if(m >= 10){
                res.append(num1to99(m));
            }else{
                res.append("零" + singles[m - 1]);
            }
        }
        return res.toString();
    }
    public static String num1to9999(int n){
        StringBuilder res = new StringBuilder();
        if(n < 10) return singles[n - 1];
        else if(n < 100) return num1to99(n);
        else if(n < 1000) return num1to999(n);
        else{
            int d = n / 1000;
            int m = n % 1000;
            res.append(singles[d - 1] + "千");
            if(m == 0) return res.toString();
            else if(m > 100){
                res.append(num1to999(m));
            }else{
                res.append("零" + num1to99(m));
            }
        }
        return res.toString();
    }
    public static String num1to99999999(int n){
        StringBuilder res = new StringBuilder();
        if(n < 10) return singles[n - 1];
        else if(n < 100) return num1to99(n);
        else if(n < 1000) return num1to999(n);
        else{
            int d = n / 10000;
            int m = n % 10000;
            res.append(num1to9999(d) + "万");
            if(m == 0) return res.toString();
            else if(m > 1000){
                res.append(num1to9999(m));
            }
            else{
                res.append("零" + num1to999(m));
            }
        }
        return res.toString();
    }
    public static String numberToCN(int n){
        StringBuilder res = new StringBuilder();
        if(n < 10) return singles[n - 1];
        else if(n < 100) return num1to99(n);
        else if(n < 1000) return num1to999(n);
        else if(n < 10000_0000) return num1to99999999(n);
        else{
            if(n == Integer.MAX_VALUE){
                res.append("二十一亿");
                n %= (int)210000_0000;
                res.append(num1to99999999(n));
                return res.toString();
            }
            int d = n / (int)1000_00000;
            int m = n % (int)1000_00000;
            res.append(num1to99(d) + "亿");
            if(m == 0) return res.toString();
            else if(m > 10000000)
                res.append(num1to99999999(m));
            else
                res.append("零" + num1to99999999(m));
        }
        return res.toString();
    }
    public static void main(String[] args) {
        System.out.println(numberToCN(601500021));
    }
}
//动态的topK问题 返回词频最大的前K个字符串
//思路：手写堆
class TopK{
    public static class Node{
        private String str;
        private int cnt;
        Node(String str,int cnt){
            this.str = str;
            this.cnt = cnt;
        }
    }
    private HashMap<String, Node> numMap;
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
    public void swap(Node n1, Node n2){
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
//英文表示数字
class NumToEN {
    public static String[] two = {"One","Two","Three","Four","Five","Six","Seven","Eight","Nine",
            "Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen",
            "Eighteen","Nineteen"};
    public static String[] tens = {"Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"};
    public static String[] t = {" Billion"," Million"," Thousand",""};
    public static String numberToWords(int num) {
        if(num == 0) return "Zero";
        StringBuilder res = new StringBuilder();
        if(num < 20) return two[num - 1];
        else if(num < 100) return enNum1to99(num);
        else if(num < 1000) return enNum1to999(num);
        else{
            if(num == Integer.MAX_VALUE){
                res.append("Two Billion ");
                num %= (int)2e9;
            }
            int h = (int)1e9;
            int index = 0;
            while(num != 0){
                int cur = num / h;
                num %= h;
                if(cur != 0){
                    res.append(enNum1to999(cur));
                    res.append(t[index] + (num == 0 ? "" : " "));
                }
                h /= 1000;
                index++;
            }
        }
        return res.toString();
    }
    public static String enNum1to99(int n){
        StringBuilder res = new StringBuilder();
        if(n < 20){
            return two[n - 1];
        }
        int d = n / 10;
        int m = n % 10;
        if(m == 0)
            res.append(tens[d - 2]);
        else
            res.append(tens[d - 2] + " " + two[m - 1]);
        return res.toString();
    }
    public static String enNum1to999(int n){
        StringBuilder res = new StringBuilder();
        if(n < 20)
            return two[n - 1];
        else if(n < 100)
            return enNum1to99(n);
        else{
            int d = n / 100;
            int m = n % 100;
            if(m == 0)
                res.append(two[d - 1] + " Hundred");
            else
                res.append(two[d - 1] + " Hundred " + enNum1to99(m));
        }
        return res.toString();
    }

    public static void main(String[] args) {
        System.out.println(numberToWords(Integer.MAX_VALUE));
    }
}