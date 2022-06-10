import java.util.*;

public class SuperSkill {
    //给定一个数组，求如果排序以后，相邻两个数的最大差值。要求时间复杂度O(N)，且要求不能使用非基于比较的排序
    public static int maxDiff(int[] arr){
        int n = arr.length;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            max = Math.max(max,arr[i]);
            min = Math.min(min,arr[i]);
        }
        if(max == min) return 0;
        boolean[] f = new boolean[n + 1];//记录桶中是否有元素
        int[] maxArr = new int[n + 1];//创建n+1个桶，记录最大值
        int[] minArr = new int[n + 1];//创建n+1个桶，记录最小值
        for (int i = 0; i < n; i++) {
            //查找桶的序号
            int index = (int)((arr[i] - min) * n / (max - min));
            maxArr[index] = f[index] ? Math.max(maxArr[index],arr[i]) : arr[i];
            minArr[index] = f[index] ? Math.min(minArr[index],arr[i]) : arr[i];
            f[index] = true;
        }
        int pre = maxArr[0];
        int ans = 0;
        int j = 1;
        //下一个桶的最小值与当前桶的最大值的差就是相邻数之间的差值
        while(j <= n){
            if(f[j]){
                ans = Math.max(minArr[j] - pre,ans);
                pre = maxArr[j];
            }
            j++;
        }
        return ans;
    }
    //给出n个数字，问最多有多少不重复的非空区间，使得每个区间内数字的异或和都等于0
    public static int maxXorNum(int[] arr){
        int xor = 0;//记录前缀异或和
        HashMap<Integer,Integer> map = new HashMap<>();//记录每个前缀异或和上次出现的位置
        int n = arr.length;
        int[] dp = new int[n];//dp[i]表示以0——i的子数组，他最多异或和为0的区间数。
        map.put(arr[0],-1);
        for (int i = 0; i < n; i++) {
            xor ^= arr[i];
            if(map.containsKey(xor)){
                int cur = map.get(xor);
                dp[i] = cur == -1 ? 1 : dp[cur] + 1;
            }
            //无论该异或和是否存在，都需要更新或者添加
            map.put(xor,i);
            //如果最后一个区间不是异或和为0，那么dp[i] = dp[i - 1]
            //如果最后一个区间是异或和为0的，那么需要比较dp[i - 1] 和 dp[i]的值
            if(i > 0){
                dp[i] = Math.max(dp[i - 1],dp[i]);
            }
        }
        return dp[n - 1];
    }
    //现有n1+n2种面值的硬币，其中前n1种为普通币，可以取任意枚，后n2种为纪念币，每种最多只能取一枚，每种硬币有一个面值
    //问能用多少种方法拼出m的面值
    //思路：用普通币凑出目标面值，本质上就是一个完全背包问题
    //纪念币就是一个0 1 背包问题
    //可以创建两个表，一个是完全背包的表，另一个是01背包的表。最后累加
    //这里直接用一维数组累加了。
    public static int coins(int[] c1,int[] c2,int m){
        int n1 = c1.length;
        int n2 = c2.length;
        int[] dp1 = new int[m + 1];
        dp1[0] = 1;
        //完全背包问题
        for (int i = 0; i < n1; i++) {
            for (int j = c1[i]; j <= m; j++) {
                    dp1[j] += dp1[j - c1[i]];
            }
        }
        //0 1 背包问题
        for (int i = 0; i < n2; i++) {
            for (int j = m; j >= c2[i]; j--) {
                    dp1[j] += dp1[j - c2[i]];
            }
        }
        return dp1[m];
    }
    //给定两个一维int数组A和B，其中：A是长度为m，元素从小到大的有序数组，B是长度为n，元素从小到大的有序数组
    //从A和B数组中找出第k小的数字。要求：使用尽量少的比较次数
    public static int findKthNum(int[] A,int[] B,int k){
        if(k == 0 || k > A.length + B.length) throw new RuntimeException("K is invalid!");
        int n = A.length,m = B.length;
        int[] shorts = n <= m ? A : B;
        int[] longs = n > m ? A : B;
        int s = shorts.length;
        int l = longs.length;
        //第一种情况 k的值小于较短数组的长度，那么就只要取出两个数组的前k个数字，进行查找上中位数即可
        if(k <= s){
            return getUpMid(shorts,0,k - 1,longs,0,k - 1);
        }
        //如果大于了较大数组的长度，那么就要分类讨论了。
        //具体分类还是自己列例子就明了了。
        if(k > l){
            if(shorts[k - l - 1] >= longs[l - 1])
                return shorts[k - l - 1];
            if(longs[k - s - 1] >= shorts[s - 1])
                return longs[k - s - 1];
            return getUpMid(shorts,k - l,s - 1,longs,k - s,l - 1);
        }
        //k的值大于s且小于l
        if(longs[k - s - 1] >= shorts[s - 1])
            return longs[k - s -1];
        return getUpMid(shorts,0,s - 1,longs,k - s,k - 1);
    }
    //查找两个数组的上中位数
    //这里的写法有点难理解
    public static int getUpMid(int[] a1,int L1,int R1,int[] a2,int L2,int R2){
        int m1 = 0;
        int m2 = 0;
        int offset = 0;
        while(L1 < R1){
            m1 = (L1 + R1) >> 1;
            m2 = (L2 + R2) >> 1;
            offset = ((R1 - L1 + 1) & 1) ^ 1;
            if(a1[m1] > a2[m2]){
                R1 = m1;
                L2 = m2 + offset;
            }else if(a1[m1] < a2[m2]){
                R2 = m2;
                L1 = m1 + offset;
            }else{
                return a1[m1];
            }
        }
        return Math.min(a1[L1],a2[L2]);
    }
    //约瑟夫环问题
    //这个从1开始报数
    /**
     *
     * @param m 表示要报的数
     * @param len 表示当前数字个数
     * @return 返回最终存活的元素，在元素长度为len时的编号为多少
     */
    public static int getNo(int m,int len){
        if(len == 1) return 1;
        return (getNo(m,len - 1) + m - 1) % len + 1;
    }
    //下面这个是从0开始报数的  leetcode 剑指 Offer 62 圆圈中最后剩下的数字
//    public static int getNo(int m,int len){
//        if(len == 1) return 0;
//        return (getNo(m,len - 1) + m) % len;
//    }
    //进阶版约瑟夫问题
    //给定一个数组A和n，n相当于上题的len，A中的元素就是上题的m，其中A中的元素轮流采用，相当于一个环形的数组,报数从0开始
    //返回最后存活下来的元素在开始的编号为多少
    public static int live(int[] arr,int n,int index){
        if(n == 1) return 0;
        return (live(arr,n - 1,index == arr.length - 1 ? 0 : index + 1) + arr[index]) % n;
    }
    //给定一个N * 3 的矩阵m，对于每一个长度为3的小数组arr，都表示一个大楼的三个数据。arr[0]表示大楼的左边界，arr[1]表示大楼的右边界
    //arr[2]表示大楼的高度（一定大于0）。每座大楼的地基都在x轴上，大楼之间可能会有重叠，返回整体的轮廓数组。

    //思路：通过最大高度变化来描述轮廓
    public static class Node{
        public int x;//x轴上的值
        public boolean isAdd;//true表示加入高度，false表示删除高度
        public int height;//高度
        Node(int x,boolean isAdd,int h){
            this.x = x;
            this.isAdd = isAdd;
            height = h;
        }
    }
    //比较规则：以x的大小升序，如果x相同，isAdd为true的排前面，如果还是一样就无所谓顺序。
    public static class BuildingComparator implements Comparator<Node>{

        @Override
        public int compare(Node o1, Node o2) {
            if(o1.x != o2.x)
                return o1.x - o2.x;
            else
                return o1.isAdd == o2.isAdd ? 0 : o1.isAdd ? -1 : 1;
        }
    }
    public static List<List<Integer>> buildingOutline(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        Node[] nodes = new Node[buildings.length * 2];//因为每座楼有左边界和右边界，那么高度变化会有两个，所以数组长度要*2
        for(int i = 0;i < buildings.length;i++){
            nodes[i * 2] = new Node(buildings[i][0],true,buildings[i][2]);//这个是左边界且高度变大
            nodes[i * 2 + 1] =new Node(buildings[i][1],false,buildings[i][2]);//这个是右边界且高度变低
        }
        Arrays.sort(nodes,new BuildingComparator());
        //记录每个高度出现的次数
        TreeMap<Integer,Integer> heightTimes = new TreeMap<>();
        //记录每个x点，所对应的高度
        TreeMap<Integer,Integer> xHeight = new TreeMap<>();
        for (int i = 0; i < nodes.length; i++) {
            if(nodes[i].isAdd){//表示加入高度
                if(!heightTimes.containsKey(nodes[i].height)){
                    heightTimes.put(nodes[i].height,1);
                }else{
                    heightTimes.put(nodes[i].height,heightTimes.get(nodes[i].height) + 1);
                }
                xHeight.put(nodes[i].x,heightTimes.lastKey());//左边界直接将其和它的高度，添加到xHeight中
            }else{//表示删除高度
                if (heightTimes.get(nodes[i].height) == 1){//只剩一个就直接删除
                    heightTimes.remove(nodes[i].height);
                }else{
                    heightTimes.put(nodes[i].height,heightTimes.get(nodes[i].height) - 1);
                }
                if(heightTimes.isEmpty()){//如果是空的说明当前高度降到了0
                    xHeight.put(nodes[i].x,0);
                }else
                    xHeight.put(nodes[i].x,heightTimes.lastKey());//更新为最大的高度，因为轮廓只可能是当前区间最大高度
            }
        }
        int preX = nodes[0].x;
        int pre = xHeight.get(preX);
        for(Map.Entry<Integer,Integer> e : xHeight.entrySet()){
            int x = e.getKey();
            int h = e.getValue();
            //如果高度不同说明这里就是高度变化的地方，直接添加到答案中
            if(pre != h){
                if(pre != 0)//如果高度降低到0就不记录
                    res.add(Arrays.asList(preX,x,pre));
                preX = x;
                pre = h;
            }
        }
        return res;
    }
    //给定一个arr数组，数组无序，但每个值都是正数，在给定一个正数k，求arr的所有子数组中所有元素和为k的最长子数组长度
    //思路：简单的滑动窗口
    public static int getMaxLength(int[] arr,int k){
        int left = -1;
        int right = 0;
        int sum = arr[0];
        int ans = 0;
        while(right < arr.length){
            if(sum < k){
                if(right + 1 < arr.length)
                    sum += arr[++right];
                else break;
            }else if(sum > k){
                sum -= arr[++left];
            }else{
                ans = Math.max(right - left,ans);
                if(right + 1 < arr.length)
                    sum += arr[++right];
                else break;
            }
        }
        return ans;
   }
   //给定一个arr数组，数组无序(可能有负数和0)，在给定一个k，求arr的所有子数组中所有元素和为k的最长子数组长度
    //思路：计算每个i位置的前缀和cnt[i]，以cnt[i],i的形式记录到哈希表中，如果key相同，只记录最早的一次。因为该题是求最长子数组长度
   public static int maxSubArrayLen(int[] nums, int k) {
        int n = nums.length;
        int[] cnt = new int[n + 1];
        HashMap<Integer,Integer> map = new HashMap<>();
       for (int i = 1; i < cnt.length; i++) {
           cnt[i] = cnt[i - 1] + nums[i - 1];
           if(!map.containsKey(cnt[i]))
                map.put(cnt[i],i);
       }
       map.put(0,0);
       int ans = 0;
       for (int i = n; i > ans; i--) {
           if(map.containsKey(cnt[i] - k))//如果cnt[i] - k存在，那么只需要将该部分剥离出当前i的前缀和即可得出和为0的子数组
               ans = Math.max(ans,i - map.get(cnt[i] - k));
       }
       return ans;
   }
   //给定一个arr数组，数组无序(可能有负数和0)，在给定一个k，求arr的所有子数组中所有元素和小于等于k的最长子数组长度
    public static int getMaxLengthPlus(int[] arr,int k){
        int n = arr.length;
        int[] minSum = new int[n];//minSum[i]表示以i开始的最小子数组和
        int[] minSumEnd = new int[n];//minSumEnd[i]记录minSum[i]这个子数组和的右边界
        minSum[n - 1] = arr[n - 1];
        minSumEnd[n - 1] = n - 1;
        for (int i = n - 2; i >= 0; i--) {
            minSum[i] = Math.min(arr[i],arr[i] + minSum[i + 1]);
            minSumEnd[i] = minSum[i] == arr[i] ? i : minSumEnd[i + 1];
        }
        int ans = 0;
        int sum = 0;
        int end = 0;//end是窗口右边界的下一个位置
        for (int i = 0; i < n; i++) {//i表示窗口的左边界
            while(end < n && sum + minSum[end] <= k){
                sum += minSum[end];
                end = minSumEnd[end] + 1;
            }
            ans = Math.max(ans,end - i);
            if (end > i){//如果sum大于k，并且窗口内还有数，就删除最左侧的数
                sum -= arr[i];
            }else{//窗口内没有数了，说明以i开头的所有子数组和不可能小于等于k了
                end = i + 1;
            }
        }
        return ans;
    }
    //给一个数组arr，arr[i]表示铜钱数量，先手后手,每个人都必须拿铜钱，谁拿完铜钱谁胜，假设两人都绝顶聪明
    //思路：如果一开始数组元素异或和为0，那么后手赢，如果一开始异或和不为0，先手胜。
    //原理：尼姆博弈，只要保证让下一个人面对的是异或和为0的元素，那么无论如何他都无法再次选择出异或和为0的新数组，因为不能不取铜钱
    //true表示先手赢，false表示后手赢
    public static boolean nim(int[] arr){
        int xor = 0;
        for (int i = 0; i < arr.length; i++) {
            xor ^= arr[i];
        }
        if(xor == 0) return false;
        else return true;
    }
    //一个char类型的数组chs，其中所有的字符都不同。例如，chs['A','B','C',......,'Z',]，则字符串与整数的对应关系如下：
    //A,B....Z,AA,AB......AZ,BA,BB.......ZZ,AAA...ZZZ,AAAA...
    //1,2....26,27,28......52,53,54......702,703...18278,18279...
    //给定一个数组chs，实现根据对应关系完成字符串与整数相互转换的两个函数
    public static String getString(char[] chs,int i){
        int n = chs.length;
        int j = 1;
        int cnt = -1;
        StringBuilder sb = new StringBuilder();
        while(i >= j){
            i -= j;
            sb.append(chs[0]);
            j *= n;
            cnt++;
        }
        j /= n;
        if (i == 0) return sb.reverse().toString();
        while(i != 0){
            int index = i / j;
            sb.setCharAt(cnt, (char) (sb.charAt(cnt--) + index));
            i %= j;
            j /= n;
        }
        return sb.reverse().toString();
    }
    public static int getNum(char[] chs,String s){
        char[] str = s.toCharArray();
        int n1 = chs.length;
        int n2 = str.length;
        int res = 0;
        int j = 1;
        for (int i = n2 - 1; i >= 0; i--) {
            res += j * (str[i] - 'A' + 1);
            j *= n1;
        }
        return res;
    }
    //给定一个二维数组matrix，每个单元表示一个整数，有正有负。给你操纵一条长度为0的蛇。
    // 蛇从矩阵的最左侧进入，蛇每次能够到达当前位置的右上相邻，右侧相邻和右下相邻的单元格。蛇到达一个单元格以后自身的长度就会加上单元格的数值
    //如果蛇的长度为负数，游戏结束。你有一个能力，可以将一个单元格数值变成其相反数，但是只能使用一次。问蛇的长度最大可以是多少

    //Info用来记录来到当前单元格时使用能力和不使用能力分别的最大蛇长度。
    public static class Info{
        public int yes;
        public int no;
        Info(int y,int n){
            yes = y;
            no = n;
        }
    }
    public static int snake(int[][] matrix){
        int row = matrix.length;
        int col = matrix[0].length;
        int ans = 0;
        Info[][] dp = new Info[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Info process = process(matrix, i, j,dp);
                ans = Math.max(ans,Math.max(process.yes, process.no));
            }
        }
        return ans;
    }
    public static Info process(int[][] matrix,int row,int col,Info[][] dp){
        //如果是第一列，说明是刚进入matrix
        if (dp[row][col] != null)
            return dp[row][col];
        if (col == 0){
            dp[row][col] = new Info(-matrix[row][col],matrix[row][col]);
            return dp[row][col];
        }
        int preY = -1;//记录之前使用能力时蛇的长度
        int preN = -1;//记录之前没有使用能力时蛇的长度
        //说明有左上有单元格
        if (row > 0){
            Info leftUp = process(matrix,row - 1,col - 1,dp);
            if (leftUp.no >= 0){
                preN = leftUp.no;
            }
            if (leftUp.yes >= 0){
                preY = leftUp.yes;
            }
        }
        Info left = process(matrix,row,col - 1,dp);
        if (left.no >= 0){
            preN = Math.max(preN,left.no);
        }
        if(left.yes >= 0){
            preY = Math.max(preY,left.yes);
        }
        if (row < matrix.length - 1){
            Info leftD = process(matrix,row + 1,col - 1,dp);
            if (leftD.no >= 0){
                preN = Math.max(preN,leftD.no);
            }
            if(leftD.yes >= 0){
                preY = Math.max(preY,leftD.yes);
            }
        }
        int yes = -1;
        int no = -1;
        if(preN >= 0){
            no = preN + matrix[row][col];
            yes = preN - matrix[row][col];
        }
        if (preY >= 0){
            yes = Math.max(preY + matrix[row][col],yes);
        }
        dp[row][col] = new Info(yes,no);
        return dp[row][col];
    }
    //给定一个字符串str，str表示一个公式，公式里可能有整数，加减乘除符号和左右括号，返回公式的计算结果
    public static int result(String str){
        char[] chs = str.replaceAll(" ", "").toCharArray();
        return f(chs,0)[0];
    }
    public static int[] f(char[] chs,int i){
        int num = 0;
        int res = 0;
        LinkedList<String> list = new LinkedList<>();
        int j = i;
        while(j < chs.length && chs[j] != ')'){
            if(chs[j] >= '0' && chs[j] <= '9'){
                num = num * 10 + chs[j++] - '0';
            }else if(chs[j] != '('){
                addNum(list,num);
                num = 0;
                list.add(String.valueOf(chs[j++]));
            }else{
                int[] f = f(chs, j + 1);
                num = f[0];
                j = f[1] + 1;
            }
        }
        addNum(list,num);
        res += getNum(list);
        return new int[]{res,j};
    }
    public static int getNum(LinkedList<String> list){
        int ans = 0;
        boolean f = !list.peekFirst().equals("-");
        while(!list.isEmpty()){
            String s = list.pollFirst();
            if (s.equals("+") || s.equals("-"))
                f = s.equals("+");
            else
                ans = f ? ans + Integer.parseInt(s) : ans - Integer.parseInt(s);
        }
        return ans;
    }
    public static void addNum(LinkedList<String> list,int num){
        if (!list.isEmpty()){
            String s = list.pollLast();
            if (s.equals("*") || s.equals("/")){
                int n = Integer.parseInt(list.pollLast());
                num = s.equals("*") ? n * num : n / num;
            }else
                list.add(s);
        }
        list.add(String.valueOf(num));
    }
    //最长公共子串（极致的压缩空间,O(1)）
    public static int MaxComString(String s1,String s2){
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        int row = 0;
        int col = c2.length - 1;
        int max = 0;
        while(row < c1.length){
            int i = row;
            int j = col;
            int len = 0;
            while(i < c1.length && j < c2.length) {
                if (c1[i] == c2[j]) {
                    len++;
                } else {
                    len = 0;
                }
                if (len > max)
                    max = len;
                i++;
                j++;
            }
            if (col > 0){
                col--;
            }else{
                row++;
            }
        }
        return max;
    }
    //添加最少的字符让字符串变为回文字符串
    public static String getPalindrome(String s){
        char[] chs = s.toCharArray();
        int n = chs.length;
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 0;
            for (int j = i + 1; j < n; j++) {
                if(chs[i] == chs[j])
                    dp[i][j] = dp[i + 1][j - 1];
                else{
                    dp[i][j] = Math.min(dp[i + 1][j],dp[i][j - 1]) + 1;
                }
            }
        }
        char[] ans = new char[n + dp[0][n - 1]];
        int r = 0;
        int c = n - 1;
        int L = 0;
        int R = ans.length - 1;
        while(r <= c){
            if(chs[r] == chs[c]){
                ans[L++] = chs[r++];
                ans[R--] = chs[c--];
            }else if(dp[r + 1][c] < dp[r][c - 1]){
                ans[L++] = ans[R--] = chs[r++];
            }else{
                ans[L++] = ans[R--] = chs[c--];
            }
        }
        return String.valueOf(ans);
    }
    //给定一个字符串str，返回把str全部切成回文子串的最小分割数。
    public static int minCut(String s){
        char[] chs = s.toCharArray();
        int n = chs.length;
        boolean[][] f = process(chs);//用来判断是否是回文，预处理。用于降低时间复杂度
        int[] dp = new int[n + 1];//dp[i] 表示以i起始到字符串最后，能产生最少的回文子串个数
        dp[n] = 0;
        dp[n - 1] = 1;
        for (int i = n - 2; i >= 0 ; i--) {
            dp[i] = n - i;
            for (int j = i; j < n; j++) {
                if(f[i][j]){
                    dp[i] = Math.min(dp[i],dp[j + 1] + 1);
                }
            }
        }
        return dp[0] - 1;//分割次数就是最少回文子串个数-1
    }
    public static boolean[][] process(char[] chs){
        int n = chs.length;
        boolean[][] dp = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = true;
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = chs[i] == chs[j] && (j - i < 2 || dp[i + 1][j - 1]);
            }
        }
        return dp;
    }
    //提供一个字符串，通过移除字符使其变成回文字符串，空串不认为是回文。请返回其一共有多少次移除方案。
    //如果移除的字符依次构成的序列不一样就是不同方案
    public static int deleteToP(String s){
        char[] chs = s.toCharArray();
        int n = chs.length;
        int mod = 998244353;//如果字符串过长，需要模上一个值来减小数值
        int[][] dp = new int[n][n];//dp[i][j]表示在i到j范围内的字符串，他的移除方案数
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                //有四种可能
                //1.以i结尾，以j结尾
                //2.以i结尾，不以j结尾
                //3.不以i结尾，不以j结尾
                //4.不以i结尾，以j结尾
                //dp[i][j - 1]就是2和3的集合，同理dp[i + 1][j]是3和4的集合
                //需要单独计算出3的情况，dp[i + 1][j - 1]就是3的情况。
                //1的情况只有在下标为i的字符和下标为j的字符相等的时候才有效。
                //如果1不成立，那么dp[i][j] = dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1]
                //否则，1的答案由两部分组成，一个是移除i和j之间的所有字符（不包括i，j），还有一个就是dp[i + 1][j - 1]的值
                //那么最后答案就是dp[i][j] = dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1] + dp[i + 1][j - 1] + 1
                dp[i][j] = (dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1] + (chs[i] == chs[j] ? dp[i + 1][j - 1] + 1 : 0)) % mod;
                while(dp[i][j] < 0)//因为取模导致有的数取模了有的数没取模进行加减，不能反映原本的大小关系，需要及时加模值补偿
                    dp[i][j] += mod;
            }
        }
        return dp[0][n - 1];
    }
    //一个无序数组中，求最小的第k个数（BFPRT算法）
    public static int getKthMin(int[] arr,int i){
        return select(arr,0,arr.length - 1,i - 1);
    }
    public static int select(int[] arr,int begin,int end,int aim){
        if(begin == end)
            return arr[begin];
        int s = getMidofMid(arr,begin,end);
        int[] p = partition(arr,begin,end,s);
        if(aim < p[0]){
            return select(arr,begin,p[0] - 1,aim);
        }else if(aim > p[1])
            return select(arr,p[1] + 1,end,aim);
        else
            return arr[aim];
    }
    public static int[] partition(int[] arr,int begin,int end,int s){
        int L = begin - 1;
        int R = end + 1;
        int cur = begin;
        while(cur != R){
            if(arr[cur] < s){
                swap(arr,++L,cur++);
            }else if(arr[cur] > s){
                swap(arr,--R,cur);
            }else{
                cur++;
            }
        }
        return new int[]{L + 1,R - 1};
    }
    public static void swap(int[] arr,int a,int b){
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }
    public static int getMidofMid(int[] a,int begin,int end){
        int n = end - begin + 1;
        int offset = n % 5 == 0 ? 0 : 1;
        int m = n / 5 + offset;
        int[] midArr = new int[m];
        for (int i = 0; i < midArr.length; i++) {
            int beginI= begin + i * 5;
            int endI = beginI + 4;
            midArr[i] = getMid(a,beginI,Math.min(endI,end));
        }
        return select(midArr,0,midArr.length - 1,(midArr.length - 1) / 2);
    }
    public static int getMid(int[] arr,int begin,int end){
        insertSort(arr,begin,end);
        int mid = (begin + end) / 2;
        return arr[mid];
    }
    public static void insertSort(int[] a,int begin,int end){
        for (int i = begin; i <= end - 1; i++) {
            for (int j = i + 1; j <= end; j++) {
                if(a[i] > a[j])
                    swap(a,i,j);
            }
        }
    }
    //给定一个正数n，求裂开的方法数(递归)
    public static int getWay1(int n){
        if(n == 0) return 0;
        if(n == 1) return 1;
        return process(1,n);
    }
    public static int process(int pre,int rest){
        if(rest == 0) return 1;
        if(pre > rest) return 0;
        int res = 0;
        for (int i = pre; i <= rest; i++) {
            res += process(i,rest - i);
        }
        return res;
    }
    //给定一个正数n，求裂开的方法数(dp)
    public static int getWay(int n){
        if(n < 1) return 0;
        if(n == 1) return 1;
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
        }
        for (int pre = n; pre >= 1; pre--) {
            dp[pre][pre] = 1;
            for (int rest = pre + 1; rest <= n; rest++) {
                dp[pre][rest] = dp[pre][rest - pre] + dp[pre + 1][rest];//斜率优化
            }
        }
        return dp[1][n];
    }
    //完美洗牌问题
    //给定一个长度为偶数的数组啊让人，长度为2*N。前N个为左部分，后N个为右部分。
    // arr就可以表示为{L1,L2,...Ln,R1,R2...Rn}
    //把它调整为{R1,L1,R2,L2,...Rn,Ln}
    public static void shuffle(int[] arr,int L,int R){
//        heapSort(arr);
        while(R - L + 1 > 0) {
            int s = R - L + 1;
            int base = 3;
            int k = 0;
            while (base - 1 <= s) {
                base *= 3;
                k++;
            }
            base = base / 3;
            int half = (base - 1) >> 1;
            int m = (L + R) >> 1;
            rotate(arr,L + half,m,m + half);
            circle(arr,L,base - 1,k);
            L = L + base - 1;
        }
    }
    //位置公式
    public static int modifyIndex(int i,int s){
        int n = s >> 1;
        if(i <= n)
            return 2 * i;
        else return (i - n) * 2 - 1;
    }
    //替换函数
    public static void circle(int[] arr,int L,int s,int k){
        for (int i = 0,t = 1; i < k;i++,t *= 3) {
            int pre = arr[L + t - 1];
            int cur = modifyIndex(t,s);
            while(cur != t){
                int next = arr[L + cur - 1];
                arr[L + cur - 1] = pre;
                pre = next;
                cur = modifyIndex(cur,s);
            }
            arr[L + cur - 1] = pre;
        }
    }
    //反转函数
    public static void reverse(int[] arr,int L,int R){
        while(L < R){
            swap(arr,L++,R--);
        }
    }
    //旋转函数
    public static void rotate(int[] arr,int L,int m,int R){
        reverse(arr,L,m);
        reverse(arr,m + 1,R);
        reverse(arr,L,R);
    }
    public static void heapSort(int[] arr){
        for (int i = arr.length / 2 - 1; i >= 0; --i) {
            heapify(arr,i,arr.length);
        }
        int heapSize = arr.length;
        swap(arr,0,--heapSize);
        while(heapSize > 0){
            heapify(arr,0,heapSize);
            swap(arr,0,--heapSize);
        }
    }
    public static void heapInsert(int[] arr,int index){
        while(arr[(index - 1) / 2] < arr[index]){
            swap(arr,index,(index - 1) / 2);
            index = (index - 1) / 2;
        }
    }
    public static void heapify(int[] arr,int index,int heapSize){
        int left = index * 2 + 1;
        while(left < heapSize){
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[largest] > arr[index] ? largest : index;
            if(largest == index){
                break;
            }
            swap(arr,index,largest);
            index = largest;
            left = index * 2 + 1;
        }
    }
    public static void main(String[] args) {
//        System.out.println(maxDiff(new int[]{7,0,80,90,56,45,25,31,48,78,32}));
//        System.out.println(maxXorNum(new int[]{0}));
//        System.out.println(coins(new int[]{3,2,5},new int[]{1,2,4},5));
//        System.out.println(findKthNum(new int[]{8,100,200,300,400,500,1000,10000},new int[]{16,17,20,35,74,86},8));
//        System.out.println(getNo(3,5));
//        System.out.println(live(new int[]{1,3,5},20,0));
//        int[][] m = {{2,5,6},{1,7,4},{4,6,7},{3,6,5},{10,13,2},{9,11,3},{12,14,4},{10,12,5}};
//        List<List<Integer>> lists = buildingOutline(m);
//        System.out.println(getMaxLength(new int[]{1,1,1}, 8));
//        System.out.println(maxSubArrayLen(new int[]{1,1,-5,2,3},0));
//        System.out.println(getMaxLengthPlus(new int[]{50, -10, 5, -2, 20, 30}, 50));
//        System.out.println(nim(new int[]{1,2,1,2,1,2}));
//        System.out.println(getString(new char[]{'A','B','C','D','E','F','G','H','I','J','K',
//                'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'},8888));
//        System.out.println(getNum(new char[]{'A','B','C','D','E','F','G','H','I','J','K',
//                'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'},"SPRING"));
//        System.out.println(snake(new int[][]{{1, -4, 10}, {3, -2, -1}, {2, -1, 0}, {0, 5, -2}}));
//        System.out.println(result("-1"));
//        System.out.println(getPalindrome("ab123c32"));
//        System.out.println(minCut("abcdfcze"));
//        System.out.println(deleteToP("jksldk"));
//        System.out.println(getWay(20));
//        System.out.println(getWay1(20));
        System.out.println(getKthMin(new int[]{5,4,3,2,1},5));
        int[] a = {8,7,2,4,3,0,6,9};
        shuffle(a,0,7);
        System.out.println();
    }
}
