public class QuickSort {
    //这里就不能取巧的交换了，出现了大量的自己交换自己就不能用异或交换和加减交换了。
    public static void swap(int[] arr, int a, int b) {
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }

    public static void main(String[] args) {
        int[] a = {5,2,3,4,3,4,-1,48,31,34,0,312,34,32,13,4,90};
        quickSort(a, 0, a.length - 1);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static int[] partition(int[] arr, int L, int R) {
        int less = L - 1;
        int more = R;//此处相当于将最后一位先排除出数组
        while (L < more) {
            if (arr[L] < arr[R]) {
                swap(arr, ++less, L++);
            } else if (arr[L] > arr[R]) {
                swap(arr, --more, L);
            } else
                L++;
        }
        swap(arr, more, R);//完成循环后此时<边界与>边界重合，再将之前排除在外的元素交换到有序位置，该元素后续不需要再次排序了。
        return new int[]{less + 1, more};//more不用减1，因为之前>边界已经向前多取了一个位置。
    }

    public static void QuickSort(int[] arr, int L, int R) {
        if (L < R) {
            swap(arr, L + (int) (Math.random() * (R - L + 1)), R);//非常关键，可以让算法效率提升很多。
            int[] p = partition(arr, L, R);
            QuickSort(arr, L, p[0] - 1);
            QuickSort(arr, p[1] + 1, R);
        }
    }
    //简化版的快排
    public static void QuickSort2(int[] arr, int L, int R) {
        if (L < R) {
            int p = partition2(arr, L, R);
            QuickSort2(arr, L, p - 1);
            QuickSort2(arr, p + 1, R);
        }
    }

    public static int partition2(int[] arr, int L, int R) {
        int less = L - 1;
//        int more=R;//此处相当于将最后一位先排除出数组
        while (L < R) {
            if (arr[L] <=arr[R]) {
                swap(arr, ++less, L++);
            } else
                L++;
        }
        swap(arr,less+1,R);
        return less + 1;
    }
    public static void quickSort(int[] arr,int L,int R){
        if(L < R){
            swap(arr,L + (int)(Math.random() * (R - L + 1)),R);
            int[] p = partition1(arr,L,R);
            quickSort(arr,L,p[0] - 1);
            quickSort(arr,p[1]+ 1,R);
        }
    }
    public static int[] partition1(int[] arr,int L,int R){
        int less = L - 1;
        int more = R;
        while(L < more){
            if(arr[L] < arr[R]){
                swap(arr,++less,L++);
            }else if(arr[L] > arr[R]){
                swap(arr,--more,L);
            }else{
                L++;
            }
        }
        swap(arr,R,less + 1);
        return new int[]{less + 1,more};
    }















}

