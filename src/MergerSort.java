import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MergerSort {
    public static void merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R)
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        while (p1 <= M)
            help[i++] = arr[p1++];
        while (p2 <= R)
            help[i++] = arr[p2++];
        for (int j = 0; j < help.length; j++) {
            arr[L + j] = help[j];
        }
    }

    public static void process(int[] arr, int L, int R) {
        if (L == R)
            return;
        int mid = L + ((R - L) >> 1);
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    //    归并排序求小和（小和为一个数它的左边比它小的数的和，数组的小和就是所有元素的小和总和）
    public static int process2(int[] arr, int L, int R) {
        if (L == R)
            return 0;
        int mid = L + ((R - L) >> 1);//求中点
        return process2(arr, L, mid) + process2(arr, mid + 1, R) + merge2(arr, L, mid, R);
    }

    public static int merge2(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int p1 = L;
        int p2 = M + 1;
        int i = 0;
        int res = 0;
        while (p1 <= M && p2 <= R) {
            res += arr[p1] < arr[p2] ? arr[p1] * (R - p2 + 1) : 0;
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while(p1<=M)
            help[i++]=arr[p1++];
        while(p2<=R)
            help[i++]=arr[p2++];
        for (int j = 0; j < help.length; j++) {
            arr[L+j]=help[j];
        }
        return res;
    }
    //    数组中的逆序对
    public static int  merge3(int[] arr,int l,int m,int r){
        int[] help =new int[r-l+1];
        int p1=l;
        int p2=m+1;
        int res=0;
        int i=0;
        while(p1<=m && p2<=r){
            res+= arr[p1]>arr[p2]?1*(r-p2+1):0;
            help[i++] = arr[p1] > arr[p2] ? arr[p1++] : arr[p2++];
        }
        while(p1<=m)
            help[i++]=arr[p1++];
        while(p2<=r)
            help[i++]=arr[p2++];
        for(int j=0;j<help.length;j++){
            arr[l+j]=help[j];
        }
        PriorityQueue<Integer> q =new PriorityQueue<Integer>();
        return res;
    }
    public static int reversePairs(int[] nums,int l,int r) {
        if(l==r)
            return 0;
        int mid =l+((r-l)>>1);
        return reversePairs(nums,l,mid)+reversePairs(nums,mid+1,r)+merge3(nums,l,mid,r);
    }
    private static int[] tmp;
    private static int[] index;
    private static int[] tmpindex;
    private static int[] ans;
    public static List<Integer> countSmaller(int[] nums) {
        List<Integer> res =new ArrayList<Integer>();
        if(nums == null || nums.length<2){
            res.add(0);
            return res;
        }
        tmp=new int[nums.length];
        index=new int[nums.length];
        tmpindex=new int[nums.length];
        ans=new int[nums.length];
        for(int i=0;i<nums.length;i++){
            index[i]=i;
        }
        process3(nums,0,nums.length-1);
        for(int num:ans){
            res.add(num);
        }
        return res;
    }
    public static void process3(int[] arr,int L,int R){
        if(L==R){
            return;
        }
        int mid =L+((R-L)>>1);
        process3(arr,L,mid);
        process3(arr,mid+1,R);
        merge4(arr,L,mid,R);
    }
    public static void merge4(int[] arr,int L,int M,int R){
        int i =L;
        int p1=L;
        int p2=M+1;
        while(p1<=M && p2<=R){
            if(arr[p1]>arr[p2]){
                tmp[i]=arr[p1];
                tmpindex[i]=index[p1];
                ans[index[p1]]+=(R-p2+1);
                ++p1;
                ++i;
            }else{
                tmp[i]=arr[p2];
                tmpindex[i]=index[p2];
                ++p2;
                ++i;
            }
        }
        while(p1<=M){
            tmp[i]=arr[p1];
            tmpindex[i]=index[p1];
            ++i;
            ++p1;
        }
        while(p2<=R){
            tmp[i]=arr[p2];
            tmpindex[i]=index[p2];
            ++i;
            ++p2;
        }
        for(int j=L;j<=R;j++){
            index[j]=tmpindex[j];
            arr[j]=tmp[j];
        }
    }
    public static void mergeSort(int[] arr,int L,int R){
        if(L < R){
            int M = L + (R - L) / 2;
            mergeSort(arr,L,M);
            mergeSort(arr,M + 1,R);
            merge5(arr,L,M,R);
        }
    }
    public static void merge5(int[] arr,int L,int M,int R){
        int t1 = L;
        int t2 = M + 1;
        int index = 0;
        int[] help = new int[R - L + 1];
        while(t1 <= M && t2 <= R){
            help[index++] = arr[t1] < arr[t2] ? arr[t1++] : arr[t2++];
        }
        while(t1 <= M){
            help[index++] = arr[t1++];
        }
        while(t2 <= R){
            help[index++] = arr[t2++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }
    public static void main(String[] args) {
        int[] arr = {5,2,6,5,87,8,9,5,2,1,4,0,0,45};
//        List<Integer> integers = countSmaller(arr);
//        for (int nums:
//             integers) {
//            System.out.println(nums);
//        }
        mergeSort(arr,0,arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }

    }
}
