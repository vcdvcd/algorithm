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
    public static void main(String[] args) {
        System.out.println(maxDiff(new int[]{7,0,80,90,56,45,25,31,48,78,32}));
        System.out.println(maxXorNum(new int[]{0}));
        System.out.println(coins(new int[]{3,2,5},new int[]{1,2,4},5));
    }
}
