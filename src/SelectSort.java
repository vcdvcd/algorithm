public class SelectSort {
    public int findKthLargest(int[] nums, int k) {
        selectSort(nums);
        return 0;
    }
    public static void swap(int[] arr,int a,int b){
        arr[a]=arr[a]^arr[b];
        arr[b]=arr[a]^arr[b];
        arr[a]=arr[a]^arr[b];
    }
    public static void selectSort(int[] arr){
        for(int i=0;i<arr.length-1;i++){
            for(int j=i+1;j<arr.length;j++){
                if(arr[j]<arr[i])
                    swap(arr,i,j);
            }
        }
    }

    public static void main(String[] args) {
        int[] a={5,13,5,7,83,2,4};
        selectSort(a);
        for (int num:
             a) {
            System.out.println(num);
        }
    }
}
