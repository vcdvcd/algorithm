public class InsertSort {
    public static void swap(int[] arr,int a,int b){
        arr[a]=arr[a]^arr[b];
        arr[b]=arr[a]^arr[b];
        arr[a]=arr[a]^arr[b];
    }
    public static void insertSort(int[] arr){
        for(int i=0;i<arr.length;i++){
            for(;i>0 && arr[i]<arr[i-1];i--){
                swap(arr,i-1,i);
            }
        }
    }
    public static void main(String[] args) {
        int[] a={1,5,2,4,4,1,2,55,2};
        insertSort(a);
        for (int num:a) {
            System.out.println(num);
        }
    }
}
