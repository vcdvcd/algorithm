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
            else return true;
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
    public static void main(String[] args) {
        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(1,map.getOrDefault(1,0) + 1);
        for(int i : map.values()){

        }
    }
}
