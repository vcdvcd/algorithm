public class EorTrie {
    public static class Node{
        Node[] nexts = new Node[2];//设置路径，只有0和1
    }
    public static class NumTrie{
        Node head = new Node();
        public void add(int num){
            Node cur = head;
            for(int i = 31;i >= 0;--i){
                int path = (num >> i) & 1;//取出整数的每一位二进制，加入到前缀树中
                cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];//设置路径，如果不存在就新建
                cur = cur.nexts[path];
            }
        }
        /**
         *
         * @param num 是0 -> i的前缀异或和
         * @return
         */
        public int maxXOR(int num){
            Node cur = head;
            int res = 0;
            for(int i = 31;i >= 0;i--){//从高位开始
                int path = (num >> i) & 1;//取出一位二进制位
                int best = i == 31 ? path : (path ^ 1);//如果是符号位，那么理想路径就是和二进制位相同，否则就是不同（贪心策略）
                best = cur.nexts[best] == null ? best ^ 1 : best;//可能不存在理想路径，只能选择另一条路。
                res |= (path ^ best) << i;//每个二进制位选择路径以后的值，更新到res中
                cur = cur.nexts[best];
            }
            return res;
        }
    }
    public static void main(String[] args) {
        int[] arr = {3,-28,-29,2,500,10,-1000,50,60,0};
        NumTrie trie = new NumTrie();
        trie.add(0);//最开始的前缀异或和是0
        int max = Integer.MIN_VALUE;
        int s = 0;
        for(int i = 0;i < arr.length;i++){
            s ^= arr[i];//0 -> i的前缀异或和
            max = Math.max(max,trie.maxXOR(s));
            trie.add(s);//添加当前前缀异或和。
        }
        System.out.println(max);
    }
}
