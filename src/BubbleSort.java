public class BubbleSort {
    public static void swap(int[] arr,int a,int b){
        arr[a]=arr[a]^arr[b];
        arr[b]=arr[a]^arr[b];
        arr[a]=arr[a]^arr[b];
    }
    public static void bubbleSort(int[] arr){
        for(int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-i-1;j++){
                if(arr[j]>arr[j+1])
                    swap(arr,j+1,j);
            }
        }
    }

    public static void main(String[] args) {
        int[] a={5,6,1,7,0,35,1,5};
        bubbleSort(a);
        for (int num:a) {
            System.out.println(num);
        }
    }
}
