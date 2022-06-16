import java.util.*;
public class Dp {
    //挑选硬币使得总和为rest，求最小硬币个数
    public static int f1(int[] arr, int rest, int index) {
        if (rest < 0) return -1;
        if (rest == 0) return 0;
        if (index == arr.length) return -1;
        int t1 = f1(arr, rest - arr[index], index + 1);
        int t2 = f1(arr, rest, index + 1);
        if (t1 == -1 && t2 == -1) {
            return -1;
        } else {
            if (t1 == -1)
                return t2;
            if (t2 == -1)
                return t1 + 1;
        }
        return Math.min(t1 + 1, t2);
    }

    public static int f4(int[] arr, int rest) {
        if (rest < 0) return -1;
        int[][] dp = new int[rest + 1][arr.length + 1];
        for (int c = 0; c <= arr.length; c++) {
            dp[0][c] = 0;
        }
        for (int r = 1; r <= rest; r++) {
            dp[r][arr.length] = -1;
        }
        for (int c = arr.length - 1; c >= 0; c--) {
            for (int r = 1; r <= rest; r++) {
                int t1 = -1;
                if (r - arr[c] >= 0) {
                    t1 = dp[r - arr[c]][c + 1];
                }
                int t2 = dp[r][c + 1];
                if (t1 == -1 && t2 == -1) {
                    dp[r][c] = -1;
                } else if (t1 == -1) {
                    dp[r][c] = t2;

                } else if (t2 == -1) {
                    dp[r][c] = t1 + 1;
                }
                if (t1 != -1 && t2 != -1) {
                    dp[r][c] = Math.min(t1 + 1, t2);
                }
            }
        }
        return dp[rest][0];
    }


    //象棋中马走到目标地点的方法数
    public static int f2(int x, int y, int k) {
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }
        if (k == 0) {
            if (x == 0 && y == 0)
                return 1;
            return 0;
        }
        return f2(x + 1, y + 2, k - 1) + f2(x + 2, y + 1, k - 1) + f2(x - 1, y + 2, k - 1)
                + f2(x - 2, y + 1, k - 1) + f2(x - 1, y - 2, k - 1) + f2(x - 2, y - 1, k - 1)
                + f2(x + 1, y - 2, k - 1) + f2(x + 2, y - 1, k - 1);
    }

    public static int f3(int x, int y, int k) {
        if (x < 0 || x > 8 || y < 0 || y > 9 || k < 0) {
            return 0;
        }
        int[][][] dp = new int[9][10][k + 1];
        dp[0][0][0] = 1;
        for (int h = 1; h <= k; h++) {
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 10; c++) {
                    dp[r][c][h] += getValue(dp, r + 1, c + 2, h - 1);
                    dp[r][c][h] += getValue(dp, r + 1, c - 2, h - 1);
                    dp[r][c][h] += getValue(dp, r - 1, c + 2, h - 1);
                    dp[r][c][h] += getValue(dp, r - 1, c - 2, h - 1);
                    dp[r][c][h] += getValue(dp, r + 2, c + 1, h - 1);
                    dp[r][c][h] += getValue(dp, r + 2, c - 1, h - 1);
                    dp[r][c][h] += getValue(dp, r - 2, c + 1, h - 1);
                    dp[r][c][h] += getValue(dp, r - 2, c - 1, h - 1);
                }
            }
        }
        return dp[x][y][k];
    }
    public static int getValue(int[][][] dp, int r, int c,int h) {
        if (r < 0 || r > 8 || c < 0 || c > 9) {
            return 0;
        }
        return dp[r][c][h];
    }
    public static int f31(int x, int y, int k) {
        if (x < 0 || x > 8 || y < 0 || y > 9 || k < 0) {
            return 0;
        }
        int[][] dp = new int[9][10];
        dp[0][0] = 1;
        for (int h = 1; h <= k; h++) {
            int[][] newDp = new int[9][10];
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 10; c++) {
                    newDp[r][c] = newDp[r][c] + getValue(dp, r + 1, c + 2);
                    newDp[r][c] = newDp[r][c] + getValue(dp, r + 1, c - 2);
                    newDp[r][c] = newDp[r][c] + getValue(dp, r - 1, c + 2);
                    newDp[r][c] = newDp[r][c] + getValue(dp, r - 1, c - 2);
                    newDp[r][c] = newDp[r][c] + getValue(dp, r + 2, c + 1);
                    newDp[r][c] = newDp[r][c] + getValue(dp, r + 2, c - 1);
                    newDp[r][c] = newDp[r][c] + getValue(dp, r - 2, c + 1);
                    newDp[r][c] = newDp[r][c] + getValue(dp, r - 2, c - 1);
                }
            }
            dp = newDp;
        }
        return dp[x][y];
    }
    public static int getValue(int[][] dp, int r, int c) {
        if (r < 0 || r > 8 || c < 0 || c > 9) {
            return 0;
        }
        return dp[r][c];
    }

    //先手后手，获胜者最大分数
    //主函数
    public static int p(int[] arr) {
        return Math.max(first(arr, 0, arr.length - 1), second(arr, 0, arr.length - 1));
    }

    //先手函数
    public static int first(int[] arr, int L, int R) {
        if (L == R) return arr[L];
        return Math.max(arr[L] + second(arr, L + 1, R), arr[R] + second(arr, L, R - 1));
    }

    //后手函数
    public static int second(int[] arr, int L, int R) {
        if (L == R) return 0;
        return Math.min(first(arr, L + 1, R), first(arr, L, R - 1));
    }

    //DP 版本
    public static int p1(int[] arr) {
        int[][] dp1 = new int[arr.length][arr.length];
        int[][] dp2 = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i == j) {
                    dp1[i][j] = arr[j];
                    dp2[i][j] = 0;
                }
            }
        }
        for (int n = 1; n < arr.length; n++) {
            for (int m = 0; m < arr.length - n; m++) {
                dp1[m][n + m] = Math.max(arr[n + m] + dp2[m][n + m - 1], arr[m] + dp2[m + 1][n + m]);
                dp2[m][n + m] = Math.min(dp1[m][n + m - 1], dp1[m + 1][n + m]);
            }
        }
        return Math.max(dp1[0][arr.length - 1], dp2[0][arr.length - 1]);
    }

    //对数器
    public static void logarithm() {
        int a = 10;
        int b = 100;
        int times = 10000;
        for (int i = 0; i < times; i++) {
            int[] res = generator(a, b);
            int random = (int) (Math.random() * b);
            if (p(res) != p1(res))
                System.out.println("nope!");
        }
        System.out.println("god!");
    }

    public static int[] generator(int len, int m) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * m);
        }
        return ans;
    }

    public static int change(int amount, int[] coins) {
        return f(amount, coins, 0);
    }

    public static int f(int amount, int[] arr, int k) {
        if (k == arr.length) {
            return amount == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int i = 0; arr[k] * i <= amount; i++) {
            ways += f(amount - arr[k] * i, arr, k + 1);
        }
        return ways;
    }

    public static List<Integer> diffWaysToCompute(String expression) {
        String[] nums = expression.split("[+\\-*]");
        String op = expression.replaceAll("[0-9]", "");
        int n = nums.length;
        List<Integer>[][] dp = new List[n][n];
        for (int i = n - 1; i >= 0; --i) {
            dp[i][i] = Collections.singletonList(Integer.parseInt(nums[i]));
            for (int j = i + 1; j < n; ++j) {
                dp[i][j] = new ArrayList<>();
                for (int k = i; k < j; ++k) {
                    char c = op.charAt(k);
                    for (int left : dp[i][k]) {
                        for (int right : dp[k + 1][j]) {
                            dp[i][j].add(c == '+' ? left + right : c == '-' ? left - right : left * right);
                        }
                    }
                }
            }
        }
        return dp[0][n - 1];
    }
    public static boolean isSubsequence(String s, String t) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        int index = 0;
        int j;
        for(int i = 1;i <= n;i++){
            j = index;
            while(j < t.length()){
                dp[i] = dp[i - 1] && s.charAt(i - 1) == t.charAt(j);
                if(dp[i]){
                    if(j >= index){
                        index = j + 1;
                        break;
                    }else{
                        dp[i] = false;
                    }
                }
                j++;
            }
        }
        return dp[n];
    }
    public static int maxRotateFunction(int[] nums) {
        int n = nums.length;
        int max = Integer.MIN_VALUE;
        for(int i = 0;i < n;i++){
            int cur = 0;
            for(int j = 0;j < n;j++){
                cur += nums[j] * ((j + i) % n);
            }
            max = Math.max(max,cur);
        }
        return max;
    }
    public static boolean makesquare(int[] matchsticks) {
        int sum = 0;
        int n = matchsticks.length;
        for(int i = 0;i < n;i++){
            sum += matchsticks[i];
        }
        Arrays.sort(matchsticks);
        if(sum % 4 != 0 || matchsticks[n - 1] > sum / 4) return false;
        int target = sum / 4;
        int[] dp = new int[1 << n];
        Arrays.fill(dp,-1);
        dp[0] = 0;
        for(int i = 0;i < (1 << n);i++){
             if(dp[i] == -1) continue;
            for(int j = 0;j < n;j++){
                if(((1 << j) & i) != 0) continue;
                int next = i | (1 << j);
                if(dp[next] != -1) continue;
                if(dp[i] % target + matchsticks[j] <= target){
                    dp[next] = dp[i] + matchsticks[j];
                }else{
                    break;
                }
            }
        }
        return dp[(1 << n) - 1] == sum;
    }
    // ==========for test ==============
    static int[][] dp = new int [15][16];
    public static void beg01(int[] w,int[] v,int W){
        int m = v.length;
        for (int i = w[0]; i <= W; i++) {
            dp[0][i] = v[0];
        }
        for(int i = 1;i < m;i++){
            for(int j = 1 ;j <= W;j++){
                if(j - w[i] >= 0){
                    dp[i][j] = Math.max(dp[i - 1][j],dp[i - 1][j - w[i]] + v[i]);
                }
                else{
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
    }
    public static void main(String[] args) {
        //System.out.println(f1(new int[]{2,3,3,5,7},19,0));
        //System.out.println(f4(new int[]{2,3,3,5,7},19));
//        System.out.println(f2(7,7,10));
//        System.out.println(f3(7,7,10));
//        System.out.println(f31(7,7,10));
        //System.out.println(p(new int[]{2,4,5,6,7}));
        //System.out.println(p1(new int[]{2,4,5,6,7}));
        //change(500,new int[]{3,5,7,8,9,10,11});
        //logarithm();
//        List<Integer> list = diffWaysToCompute("2*3-4*5-23+46");
//        list.forEach(System.out::println);
//        isSubsequence("abc", "ahbgdc");
//        System.out.println(maxRotateFunction(new int[]{}));
//        StringBuilder sb = new StringBuilder("abc");
//        long a = (long) (10e8 + 7);
//        System.out.println(a);
//        src.remove(1);
//        map.put(des,2);
        Deque d = new LinkedList();
        beg01(new int[]{3,2,1,5,10},new int[]{4,5,1,7,9},15);
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
    }
}