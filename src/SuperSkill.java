import java.util.HashMap;

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
    public static void main(String[] args) {
        System.out.println(maxDiff(new int[]{7,0,80,90,56,45,25,31,48,78,32}));
        System.out.println(maxXorNum(new int[]{0}));
        System.out.println(coins(new int[]{3,2,5},new int[]{1,2,4},5));
        System.out.println(findKthNum(new int[]{8,100,200,300,400,500,1000,10000},new int[]{16,17,20,35,74,86},8));
        System.out.println(getNo(3,5));
        System.out.println(live(new int[]{1,3,5},20,0));
    }
}
