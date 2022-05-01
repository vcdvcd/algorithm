public class Manacher {
    //将字符串加工成由‘#’隔开的新字符串
    //例如 1221 -> #1#2#2#1#
    public static char[] getNewStr(String s) {
        char[] chs = s.toCharArray();
        char[] newStr = new char[chs.length * 2 + 1];
        for (int i = 0; i < newStr.length; i++) {
            newStr[i] = (i & 1) == 0 ? '#' : chs[i / 2];
        }
        return newStr;
    }

    public static int manacher(String s) {
        if (s == null || s.length() < 1)
            return 0;
        if (s.length() == 1)
            return 1;
        int R = -1;//回文右边界再往右一个位置 最右的有效区是R-1位置
        int C = -1;//记录回文中心位置
        char[] newStr = getNewStr(s);
        //pArr数组表示回文半径，同时也是可以跳过检验的字符串数
        int[] pArr = new int[newStr.length];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < newStr.length; i++) {
            //该语句包含了三种情况
            //第一种i在回文右边界外，直接暴力扩
            //第二种i在回文内：
            //i‘为i关于大回文中心对称的位置
            //1.i‘的回文范围在大回文的范围内，那么i的回文半径就是i‘的回文半径
            //2.i‘的回文范围有部分在大回文外，那么i到右边界（R-1）就是i的回文半径
            //3.i‘的回文范围的左边界与大回文的左边界重合，那么i的回文半径不可确认，要看右边界后一位（R）的情况，需要继续扩
            //对于上述情况，下面这一句就全部解决了
            //回文半径默认是1，因为如果第一次扩展就失败，那么回文就是i本身
            //2 * C - i其实就是i‘的位置    R-i就是i到右边界的范围
            //如果i‘超出大回文的范围那么它必定大于R-i，那么下面这条语句必定取R-i，符合第二种情况的2
            //如果i‘的回文没有超出大回文的范围 那么pArr[2 * C - i]必定小于R-i，符合第二种情况的1
            //如果i‘的回文左边界与大回文的左边界重合 那么pArr[2 * C - i]会等于R-i
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            //无论你是那种情况我都扩，但是我先跳过不需要检验的字符串，这样哪怕是不需要扩的情况也会在第一次判断就被终止
            while (i + pArr[i] < newStr.length && i - pArr[i] > -1) {
                if (newStr[i + pArr[i]] == newStr[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            //如果发现i加上它的回文半径超出了R，就更新R，并且更新回文中心
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            max = Math.max(max, pArr[i]);
        }
        //最大回文半径的大小与原字符串的回文长度的关系是 最大回文半径-1=原字符串的回文长度
        //例如 #1#2#2#1# -> 它最后的最长回文长度 等于最大回文半径（#2#1#） 5-1=4（1221）
        return max - 1;
    }
    public static void main(String[] args) {
        String s = "abcecbab";
        System.out.println(manacher(s));
    }
}
