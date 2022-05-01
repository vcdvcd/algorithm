public class Hash {
    //bitmap 位图
    public static void main(String[] args) {
        int[] arr = new int[10]; //这个数组一共320bit
        //构建一个bit数组 查看第i个的状态
        int i = 178; //求第178个bit的状态
        int arrIndex = i / 32;
        int bitNum = i % 32;
        //拿到第178位的状态
        int s = (arr[arrIndex] >> bitNum) & 1;
        //将第178位状态改为1
        arr[arrIndex] = arr[arrIndex] | (1 << bitNum);
        //将第178位状态改为0
        arr[arrIndex] = arr[arrIndex] & (~(1 << bitNum));

        //任意bit 拿出状态
        int bit = (arr[i / 32] >> (i % 32)) & 1;
    }
}
