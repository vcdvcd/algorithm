import java.util.*;

public class Recursive {
    //暴力递归
    //汉诺塔问题（经典）
    public static void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
        f(A.size(),A,B,C);
    }
    public static void f(int i,List<Integer> from, List<Integer> other, List<Integer> end){
        if (i==1){
            end.add(from.remove(from.size()-1));
        }else{
            f(i-1,from,end,other);
            end.add(from.remove(from.size()-1));
            f(i-1,other,from,end);
        }
    }
    //n皇后问题
    //1.朴素解法
    public static List<List<String>> NQueen(int n){
        int[] record =new int[n];
        List<List<String>> res =new ArrayList<>();
        process(res,n,0,record);
        return res;
    }
    public static void process(List<List<String>> res,int n,int i,int[] record){
        if (i==n){
            res.add(processRecord(record,n));
            return;
        }
        for (int j=0;j<n;j++){
            if (isValid(record,i,j)){
                record[i]=j;
                process(res,n,i+1,record);
            }
        }
    }
    public static boolean isValid(int[] record,int i,int j){
        for(int k=0;k<i;k++){
            if (record[k]==j || Math.abs(record[k]-j)==Math.abs(k-i))
                return false;
        }
        return true;
    }
    public static List<String> processRecord(int[] record,int n){
        List<String> res =new ArrayList<>();
        for (int i = 0; i < record.length; i++) {
            char[] ans =new char[n];
            Arrays.fill(ans,'.');
            ans[record[i]]='Q';
            res.add(new String(ans));
        }
        return res;
    }
//第二种解法
    //2.位运算加速
    public static List<List<String>> NQueenII(int n){
        int limit =(1<<n)-1;
        int[] record =new int[n];
        List<List<String>> res =new ArrayList<>();
        process2(res,record,limit,0,0,0,0);
        return res;
    }
    public static void process2(List<List<String>> res,int[] record,int limit,int down,int left,int right,int i){
        if (down==limit){
            res.add(processRecord(record,record.length));
            return;
        }
        int pos = limit & ~(down | right | left);
        while(pos!=0){
            int rightOne =pos & (~pos+1);
            int position =Integer.bitCount(rightOne-1);
            record[i]=position;
            pos =pos-rightOne;
            process2(res,record,limit,down | rightOne,(left | rightOne)<<1,
                    (right | rightOne)>>1,i+1);
        }
    }
    //输出字符串的子序列
    public static void printAllSubsquence(String s){
        char[] ans =s.toCharArray();
        recursive(ans,0);
    }
    public static void recursive(char[] str,int i){
        if(i==str.length){
            System.out.println(String.valueOf(str).replace("\0",""));
            return;
        }
        recursive(str,i+1);
        char tmp =str[i];
        str[i]=0;
        recursive(str,i+1);
        str[i]=tmp;
    }
    //输出字符串的全部序列，且没有重复
    public static String[] permutation(String s) {
        char[] ans =s.toCharArray();
        List<String> res=new ArrayList<>();
        permutationHelper(ans,0,res);
        return res.toArray(new String[res.size()]);
    }
    public static void permutationHelper(char[] str,int i,List<String> res) {
        if (i==str.length){
            res.add(String.valueOf(str));
        }
        boolean[] flag =new boolean[26];
        for (int j = i; j < str.length; j++) {
            if (!flag[str[j]-'a']) {
                flag[str[j] - 'a'] = true;
                swap(str, i, j);
                permutationHelper(str, i + 1, res);
                swap(str, i, j);
            }
        }
    }
    public static void swap(char[] str,int a,int b){
        char tmp =str[a];
        str[a]=str[b];
        str[b]=tmp;
    }
    //Alice 和 Bob 用几堆石子在做游戏。石子排成一行；每堆都有正整数颗石子，数目为 piles[i]。
    //游戏以谁手中的石子最多来决出胜负。石子的总数是奇数，所以没有平局。
    //Alice 和 Bob 轮流进行，Alice 先开始 。 每回合，玩家从行的 开始 或 结束 处取走整堆石头。
    // 这种情况一直持续到没有更多的石子堆为止，此时手中 石子最多 的玩家 获胜 。
    public static int stoneGame(int[] piles) {
        return Math.max(f(piles,0,piles.length-1),s(piles,0,piles.length-1));
    }
    //先手函数
    public static int f(int[] piles,int L,int R){
        if (L==R)
            return piles[L];
        return Math.max(piles[L]+s(piles,L+1,R),piles[R]+s(piles,L,R-1));
    }
    //后手函数
    public static int s(int[] piles,int L,int R){
        if (L==R)
            return 0;
        return Math.min(f(piles,L+1,R),f(piles,L,R-1));
    }
    //逆序栈
    public static int f(Stack<Integer> stack){
        int res =stack.pop();
        if (stack.isEmpty()){
            return res;
        }else{
            int last =f(stack);
            stack.push(res);
            return last;
        }
    }
    public static void reverse(Stack<Integer> stack){
        if(stack.isEmpty())
            return;
        int last =f(stack);
        reverse(stack);
        stack.push(last);
    }
    //字符串转化 例如"111",可以转化成"AAA"或"KA"或者"AK"
    //返回转化个数
    //
    public static int process(char[] str,int i){
        if (i==str.length){
            return 1;
        }
        if (str[i]=='0')
            return 0;
        if (str[i]=='1'){
            int cnt = process(str,i+1);
            if (i+1<str.length)
                cnt+=process(str,i+2);
            return cnt;
        }
        if (str[i]=='2'){
            int cnt = process(str,i+1);
            if(i+1<str.length && str[i+1]<'6')
                cnt += process(str,i+2);
            return cnt;
        }
        return process(str,i+1);
    }
    //返回最大货物
    public static int max2(int[] weights,int[] values,int i,int alreadyWeight,int bag){
        if (alreadyWeight>bag)//①位置的代码提前添加了values[i]，
            return -values[i-1];// 因此无论是否超重都会添加上超重的value值，因此应返回-values[i]
        if (i==values.length)
            return 0;
        return Math.max(max2(weights,values,i+1,alreadyWeight,bag),
                values[i]+max2(weights, values, i+1, alreadyWeight+weights[i], bag));//①
    }
    public static int max(int[] weights,int[] values,int i,int alreadyWeight,int alreadyValue,int bag){
        if (alreadyWeight>bag)
            return 0;
        if (i==values.length)
            return alreadyValue;
        return Math.max(max(weights,values,i+1,alreadyWeight,alreadyValue,bag),
                max(weights, values, i+1, alreadyWeight+weights[i], alreadyValue+values[i], bag));
    }
    public static void main(String[] args) {
        List<Integer> A =new ArrayList<>();
        List<Integer> B =new ArrayList<>();
        List<Integer> C =new ArrayList<>();
        A.add(2);
        A.add(1);
        A.add(0);
//        hanota(A,B,C);
//        C.forEach(System.out::println);
//        List<List<String>> lists = NQueenII(4);
//        printAllSubsquence("abcd");
//        String[] abcds = permutation("aaa");
//        for (String s:abcds) {
//            System.out.println(s);
//        }
//        System.out.println(stoneGame(new int[]{1,100,4}));
        Stack<Integer> stack =new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        reverse(stack);
        String s ="1111";
//        System.out.println(process(s.toCharArray(), 0));
        int[] w =new int[]{1,2,100,100,100};
        int [] v =new int[]{2,5,7,15,100};
        System.out.println(max2(w, v, 0, 0,10));
    }
}
