public class BFPRT {
    public static void main(String[] args){
        System.out.println(getKthMinNum(new int[]{5,4,3,2},1));
    }
    public static int getKthMinNum(int[] arr,int k){
        return select(arr,0,arr.length - 1,k - 1);
    }
    public static int select(int[] arr,int begin,int end,int aim){
        if(begin == end) return arr[begin];
        int s = getMidofMid(arr,begin,end);
        int[] p = partition(arr,begin,end,s);
        if(aim < p[0]){
            return select(arr,begin,p[0] - 1,aim);
        }else if(aim > p[1]){
            return select(arr,p[1] + 1,end,aim);
        }else{
            return arr[aim];
        }
    }
    public static int getMidofMid(int[] arr,int begin,int end){
        int n = end - begin + 1;
        int m = n / 5;
        int offset = n % 5 == 0 ? 0 : 1;
        int[] mArr = new int[m + offset];
        for (int i = 0; i < mArr.length; i++) {
            int beginI = begin + i * 5;
            int endI = beginI + 4;
            mArr[i] = getUpMidNum(arr,beginI,Math.min(endI,end));
        }
        return select(mArr,0, mArr.length - 1,(mArr.length - 1) / 2);
    }
    public static int getUpMidNum(int[] arr,int begin,int end){
        insertSort(arr,begin,end);
        int mid = (begin + end) / 2;
        return arr[mid];
    }
    public static int[] partition(int[] arr,int begin,int end,int aim){
        int L = begin - 1;
        int R = end + 1;
        int cur = begin;
        while(cur != R){
            if(arr[cur] < aim){
                swap(arr,++L,cur++);
            }else if(arr[cur] > aim){
                swap(arr,cur,--R);
            }else{
                cur++;
            }
        }
        return new int[]{L + 1,R - 1};
    }
    public static void insertSort(int[] arr,int begin,int end){
        for (int i = begin; i <= end - 1; i++) {
            for (int j = i + 1; j <= end ; j++) {
                if(arr[i] > arr[j]){
                    swap(arr,i,j);
                }
            }
        }
    }
    public static void swap(int[] arr,int a,int b){
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }
}
