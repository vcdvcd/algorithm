import java.util.*;
public class KMP {
    //str1 是主字符串，看看包不包含str2
    //str2是子字符串
    public static int getIndexOf(String str1, String str2) {
        if (str2.length() < 1)
            return 0;
        if (str1 == null || str2 == null || str1.length() < str2.length())
            return -1;
        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();
        int[] nextArr = getNextArr(ch2);//是str2的前缀后缀匹配数组！！！
        int i = 0;
        int j = 0;
        while (i < ch1.length && j < ch2.length) {
            if (ch1[i] == ch2[j]) {
                i++;
                j++;
            } else if (nextArr[j] == -1) {//
                i++;
            } else {
                j = nextArr[j];
            }
        }
        return j == ch2.length ? i - j : -1;
    }

    //获取前缀与后缀最大匹配的数组
    public static int[] getNextArr(char[] str) {
        if (str.length == 1)
            return new int[]{-1};
        int[] res = new int[str.length];
        res[0] = -1;//人为规定 前缀与后缀最大匹配数组的第一位为-1
        res[1] = 0;
        int i = 2;//最开始的位置
        int j = 0;//相对与i来说前一位的res数组的值 例如i=2 那么j就是res[1]的值
        while (i < res.length) {
            if (str[i - 1] == str[j]) {
                res[i++] = ++j;
            } else if (j == 0) {//如果来到了第一位都不与i-1位相同，说明为0
                res[i++] = 0;
            } else {
                j = res[j];
            }
        }
        return res;
    }
    public static void main(String[] args) {
        String s1 = "aacecaaa";
        String s2 = "ce";
        System.out.println(getIndexOf(s1, s2));
    }
}

