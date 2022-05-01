import java.util.Arrays;

public class Yihuo {
    public static void printOddTimesNums(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
//        eor = a^b
//        eor !=0
//        eor在某一位上肯定是1
        int eor1 = 0;
        int rightOne = eor & (~eor + 1);//取出eor最右边为1的位置
        for (int i = 0; i < arr.length; i++) {
            if ((rightOne & arr[i]) == rightOne)//如果数组中的数与rightOne位置上都是1的，则与eor1进行异或运算
                eor1 ^= arr[i];
        }
        System.out.println(eor1 + " " + (eor ^ eor1));
    }

    public static void main(String[] args) {
//        System.out.println(1>>1);
    }
}
