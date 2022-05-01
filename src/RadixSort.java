public class RadixSort {
    //基数排序，只针对非负数
    public static int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(arr[i], max);
        }
        int res = 0;
        while (max > 0) {
            res++;
            max = max / 10;
        }
        return res;
    }
    public static void radixSort(int[] arr,int L,int R,int digit){
        //确定进制
        int radix=10;
        int i=0;
        int j=0;
        //创建辅助空间，多少个数就多少空间
        int[] bucket=new int[R-L+1];
        for (int d = 1; d <=digit; d++) {//有多少位就进出几次
            int[] count=new int[radix];
            //count[0]表示当前位(d位)是0的数字有多少个
            //count[i]表示当前位(d位)是的数字有多少个
            for (i=L; i <=R ; i++) {
                j=getDigit(arr[i],d);
                count[j]++;
            }
            // count[i]表示当前为(d位)<=i的数字有几个
            for (i = 1; i < radix; i++) {
                count[i]+=count[i-1];
            }
            //从右向左遍历arr数组
            for (i=R;i>=L;i--){
                j=getDigit(arr[i],d);
                bucket[count[j]-1]=arr[i];
                count[j]--;
            }
            for(i=L,j=0;i<=R;j++,i++){
                arr[i]=bucket[j];
            }
        }
    }
    public static int getDigit(int x, int d) {
        return ((x / (int) Math.pow(10, d - 1)) % 10);
    }
    public static void main(String[] args) {
        int[] a = {9, 5, 3, 6, 4, 7, 8};
        radixSort(a,0,a.length-1,maxBits(a));
        for (int num:a){
            System.out.println(num);
        }
    }
}
