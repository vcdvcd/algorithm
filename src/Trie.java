//前缀树结构 通过后一个节点来存储相关字符的信息
//      O
//   a/  \c
//  /     \
//O        O
public class Trie {
    TrieNode root;

    Trie() {
        root = new TrieNode();
    }

    class TrieNode {
        public int pass;//记录某个字符的个数
        public int end;//记录以某个字符为结尾的字符串的个数
        //处理多种字符时采用以下两种结构
        //HashMap<char,TrieNode>
        //TreeMap<char,TrieNode>
        //数组只解决单一类型字符
        TrieNode[] nexts;//后续字符的节点集（一个字符用一个节点，但是节点不存储字符）

        TrieNode() {
            pass = 0;
            end = 0;
            nexts = new TrieNode[26];
        }
    }
//以下所有方法都针对小写英文字符
    public void insert(String word) {
        TrieNode cur = root;
        char[] ch = word.toCharArray();
        int index = 0;
        cur.pass++;
        for (int i = 0; i < ch.length; i++) {
            index = ch[i] - 'a';
            if (cur.nexts[index] == null) {
                cur.nexts[index] = new TrieNode();
            }
            cur.nexts[index].pass++;
            cur = cur.nexts[index];
        }
        cur.end++;
    }

    //查找word有几个
    public int search(String word) {
        TrieNode cur = root;
        char[] ch = word.toCharArray();
        int index = 0;
        for (int i = 0; i < ch.length; i++) {
            index = ch[i] - 'a';
            if (cur.nexts[index] == null)
                return 0;
            cur = cur.nexts[index];
        }
        return cur.end;
    }

    //以pre为前缀的字符串的个数
    public int prefixNumber(String pre) {
        TrieNode cur = root;
        char[] ch = pre.toCharArray();
        int index = 0;
        for (int i = 0; i < ch.length; i++) {
            index = ch[i] - 'a';
            if (cur.nexts[index] == null)
                return 0;
            cur = cur.nexts[index];
        }
        return cur.pass;
    }
//从前缀树中删除word
    public void delete(String word) {
        if (search(word) != 0) {
            TrieNode cur = root;
            cur.pass--;
            int index = 0;
            char[] ch = word.toCharArray();
            for (int i = 0; i < ch.length; i++) {
                index = ch[i]-'a';
                //如果pass为0，说明该字符已经不存在与前缀树中，那么后续字符都可以删除了
                if (--cur.nexts[index].pass==0){
                    cur.nexts[index]=null;
                    return;
                }
                cur=cur.nexts[index];
            }
            cur.end--;
        }
    }
}
