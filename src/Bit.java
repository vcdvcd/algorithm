public class Bit {
    //判断一个数是否是2次幂
    public static boolean is2Power(int n) {
        //两种方式都可以
        //return (n & (n - 1)) == 0;
        return (n & (~n + 1)) == n;
    }
    //判断一个数是否是4次幂
    public static boolean is4Power(int n) {
        //先判断是否只有1个1 再判断这个1是否在偶数位上
        return (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
    }
    //不能使用+ 和 - 实现两数相加
    //前提：这两个数相加不会溢出
    // ^ (异或运算可以看做是无进位相加)
    // &  (与运算会记录进位情况)
    public static int sum(int a,int b){
        int sum = a;
        while(b != 0){
            sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }
        return a;
    }
    // 减法运算
    public static int subtract(int a,int b){
        b = sum(~b,1);
        return sum(a,b);
    }
    //乘法运算
    public static int multi(int a,int b){
        int ans = 0;
        while(b != 0){
            if((b & 1) == 1){
                ans = sum(ans, a);
            }
            a <<= 1;
            b >>>= 1;
        }
        return ans;
    }
    //除法运算 (正整数)
    //向下取整
    public static int div(int a,int b){
        if(b == 0)
            throw  new ArithmeticException("被除数不能为0");
        int ans = 0;
        for (int i = 31; i >= 0; i = subtract(i,1)) {
            if((a >> i) >= b){
               a = subtract(a,(b << i));
               ans |= (1 << i);
            }
        }
        return ans;
    }
    //返回两数最大值
    public static int sign(int a){
        return rebellion((a >> 31) & 1);
    }
    public static int rebellion(int a){
        return a ^ 1;
    }
    public static int getMax(int a,int b){
        int c = a - b;
        int sa = sign(a);
        int sb = sign(b);
        int sc = sign(c);
        int difSab = sa ^ sb;
        int samSab = rebellion(difSab);
        int returnA = sc * samSab + sa * difSab;
        int returnB = rebellion(returnA);
        return returnA * a + returnB * b;

    }
    public static void main(String[] args) {
        //System.out.println(is4Power(2));
        //System.out.println(0xfffffffc);
        //System.out.println(sum(Integer.MAX_VALUE,Integer.MIN_VALUE));
        //System.out.println(subtract(-100,500));
        //System.out.println(multi(200,0));
        //System.out.println(div(2,3));
        //System.out.println(div(6,-2));
        System.out.println(getMax(Integer.MAX_VALUE,Integer.MIN_VALUE));
    }
}
