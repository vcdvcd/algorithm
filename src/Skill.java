import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public static void main(String[] args) {
        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(1,map.getOrDefault(1,0) + 1);
        int[][] m = {{0,1,2,3},{4,5,6,7},{8,9,10,11},{12,13,14,15}};
        int[][] m1 = {{0,1,2,3},{4,5,6,7},{8,9,10,11}};
        rotate(m);
        zigzag(m1);
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }

    }
}
