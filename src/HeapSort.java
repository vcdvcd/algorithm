import java.util.PriorityQueue;

public class HeapSort {
    public static void swap(int[] arr, int a, int b) {
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }
//从下到上的比较
    public static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }
//从上到下的比较
    public static void heapify(int[] arr, int index, int heapSize) {
        int left = 2 * index + 1;//左孩子的下标
//        左孩子存在
        while (left < heapSize) {
//            如果右孩子存在并且大于左孩子，就记录右孩子的下标，否则记录左孩子下标
            int largest = left + 1 < heapSize && arr[left] < arr[left + 1] ? left + 1 : left;
//            记录父节点与较大孩子中大的一方的下标
            largest = arr[index] > arr[largest] ? index : largest;
            if (largest == index)
                break;
            swap(arr, index, largest);
            index = largest;
            left = 2 * index + 1;
        }
    }
    public static void heapSort(int[] arr,int index){
        if (arr==null || arr.length<2)
            return;
//        for (int i = 0; i < arr.length; i++) {//O(N)
//            heapInsert(arr,i);//O(logN)
//        }
        //这个改进比之前快一点点，它的时间复杂度为O(N)，证明原理为错位相减
        for (int i = arr.length/2-1; i >= 0; i--) {
            heapify(arr,i,arr.length);
        }
        int heapSize=arr.length;
        while(heapSize>0){//O(N)
            swap(arr,0,--heapSize);//O(1)
            heapify(arr,index,heapSize);//O(logN)
        }
    }
    public static void sortedArrDistanceLessK(int[] arr,int k){
        //加一弹一可以用系统的小根堆，如果不是这个要求建议使用手写堆。
        //系统的小根堆它的扩容的空间复杂度为O（logN）
        PriorityQueue<Integer> heap =new PriorityQueue<>();
        int index=0;
        for (; index <Math.min(arr.length,k) ; index++) {
            heap.add(arr[index]);
        }
        int i=0;
        for (;index<arr.length;i++,index++){
            heap.add(arr[index]);
            arr[i]=heap.poll();
        }
        while(!heap.isEmpty()){
            arr[i++]=heap.poll();
        }
    }
    public static void main(String[] args) {
        int[] a = {5,6,8,1,5};
//        heapSort(a,0);
        sortedArrDistanceLessK(a,3);
        for (int num:
             a) {
            System.out.println(num);
        }
    }
}
