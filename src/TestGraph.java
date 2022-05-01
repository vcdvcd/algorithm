import java.util.*;

public class TestGraph {
    //拓扑排序
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        if (graph == null) return new ArrayList<>();
        HashMap<DirectedGraphNode, Integer> inMap = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode next : node.neighbors) {
                if (!inMap.containsKey(next)) {
                    inMap.put(next, 1);
                } else {
                    inMap.put(next, inMap.get(next) + 1);
                }
            }
        }
        Queue<DirectedGraphNode> zeroIn = new LinkedList<>();
        ArrayList<DirectedGraphNode> res = new ArrayList<>();
        for (DirectedGraphNode node : graph) {
            if (!inMap.containsKey(node)) {
                zeroIn.add(node);
            }
        }
        while (!zeroIn.isEmpty()) {
            DirectedGraphNode cur = zeroIn.poll();
            res.add(cur);
            for (DirectedGraphNode next : cur.neighbors) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0)
                    zeroIn.add(next);
            }
        }
        return res;
    }

    //最小生成树 kruskal算法
    public static class Mysets {
        public HashMap<String,List<String>> sets;
        Mysets(List<Connection1> connections){
            sets=new HashMap<>();
            for (Connection1 c:connections) {
                List<String> c1 =new ArrayList<>();
                List<String> c2 =new ArrayList<>();
                c1.add(c.city1);
                c2.add(c.city2);
                sets.put(c.city1,c1);
                sets.put(c.city2,c2);
            }
        }
        public boolean isSameSet(String n1,String n2){
            List<String> s1 = sets.get(n1);
            List<String> s2 = sets.get(n2);
            return s1==s2;
        }
        public void union(String n1,String n2){
            List<String> s1 = sets.get(n1);
            List<String> s2 = sets.get(n2);
            for (String n:s1) {
                s2.add(n);
                sets.put(n,s2);
            }
        }
    }

    public List<Connection1> lowestCost(List<Connection1> connections) {
        Mysets sets =new Mysets(connections);
        PriorityQueue<Connection1> priorityQueue =new PriorityQueue<>(new ConnectionComparator());
        for (Connection1 con:connections) {
            priorityQueue.add(con);
        }
        List<Connection1> res= new ArrayList<>();
        while(!priorityQueue.isEmpty()){
            Connection1 cur =priorityQueue.poll();
            if (!sets.isSameSet(cur.city1, cur.city2)){
                res.add(cur);
                sets.union(cur.city1, cur.city2);
            }
        }
        return res;
    }
    //最小生成树 prim算法
    public List<Connection1> lowestCost2(List<Connection1> connections) {
        HashMap<String,List<Connection1>> map =new HashMap<>();
        for (Connection1 con:connections) {
            String c1 =con.city1;
            String c2 =con.city2;
            if (!map.containsKey(c1))
                map.put(c1,new ArrayList<>());
            if (!map.containsKey(c2))
                map.put(c2,new ArrayList<>());
            List<Connection1> s1=map.get(c1);
            List<Connection1> s2 =map.get(c2);
            s1.add(con);
            s2.add(con);
        }
        List<Connection1> res =new ArrayList<>();
        HashSet<String> nodeSets =new HashSet<>();
        PriorityQueue<Connection1> priorityQueue =new PriorityQueue<>(new ConnectionComparator());
        String n =connections.get(0).city1;
        if (!nodeSets.contains(n)){
            nodeSets.add(n);
            priorityQueue.addAll(map.get(n));
            while(!priorityQueue.isEmpty()){
                Connection1 cur =priorityQueue.poll();
                String c1 =cur.city1;
                String c2 =cur.city2;
                if(!nodeSets.contains(c1)){
                    res.add(cur);
                    nodeSets.add(c1);
                    priorityQueue.addAll(map.get(c1));
                }
                if(!nodeSets.contains(c2)){
                    res.add(cur);
                    nodeSets.add(c2);
                    priorityQueue.addAll(map.get(c2));
                }
            }
        }
        Collections.sort(res,new ConnectionComparator());
        return res;
    }
    //Dijkstra算法解决最短路径问题
    public static HashMap<Node, Integer> dijkstra(Node head) {
        HashMap<Node,Integer> distanceMap =new HashMap<>();
        distanceMap.put(head,0);
        HashSet<Node> selectedNode = new HashSet<>();
        Node minNode =getMinNodeAndUnselected(distanceMap,selectedNode);
        while(minNode!=null){
            for (Edge edge:head.edges) {
                Node toNode =edge.to;
                if(!distanceMap.containsKey(toNode)){
                    distanceMap.put(toNode,distanceMap.get(minNode)+edge.weight);
                }else{
                    distanceMap.put(toNode,Math.min(distanceMap.get(toNode),distanceMap.get(minNode)+edge.weight));
                }
            }
            selectedNode.add(minNode);
            minNode=getMinNodeAndUnselected(distanceMap,selectedNode);
        }
        return distanceMap;
    }
    public static Node getMinNodeAndUnselected(HashMap<Node,Integer> distanceMap,HashSet<Node> selectedNode){
        Node minNode =null;
        int minDistance =Integer.MAX_VALUE;
        for (Node n:distanceMap.keySet()) {
            int distance =distanceMap.get(n);
            if(!selectedNode.contains(n) && distance<minDistance){
                minNode = n;
                minDistance=distance;
            }
        }
        return minNode;
    }
    public static class ConnectionComparator implements Comparator<Connection1>{
        public int compare(Connection1 o1,Connection1 o2){
            if (o1.cost!=o2.cost)
                return o1.cost-o2.cost;
            if(!o1.city1.equals(o2.city1))
                return o1.city1.compareTo(o2.city1);
            return o1.city2.compareTo(o2.city2);
        }
    }
    //======================test=====================
    //kruskal
    public List<Connection1> lowestCost3(List<Connection1> connections) {
        Mysets set =new Mysets(connections);
        PriorityQueue<Connection1> priorityQueue =new PriorityQueue<>(new ConnectionComparator());
        priorityQueue.addAll(connections);
        List<Connection1> res =new ArrayList<>();
        while(!priorityQueue.isEmpty()){
            Connection1 cur =priorityQueue.poll();
            if (!set.isSameSet(cur.city1, cur.city2)){
                res.add(cur);
                set.union(cur.city1,cur.city2);
            }
        }
        return res;
    }
    //prim
    public List<Connection1> lowestCost4(List<Connection1> connections) {
        HashMap<String,List<Connection1>> graph =buildGraph(connections);
        HashSet<String> set =new HashSet<>();
        List<Connection1> res =new ArrayList<>();
        PriorityQueue<Connection1> priorityQueue =new PriorityQueue<>(new ConnectionComparator());
        String firstNode =connections.get(0).city1;
        if (!set.contains(firstNode)){
            set.add(firstNode);
            priorityQueue.addAll(graph.get(firstNode));
            while(!priorityQueue.isEmpty()){
                Connection1 cur =priorityQueue.poll();
                String c1 =cur.city1;
                String c2 =cur.city2;
                if (!set.contains(c1)){
                    res.add(cur);
                    set.add(cur.city1);
                    priorityQueue.addAll(graph.get(cur.city1));
                }
                if (!set.contains(c2)) {
                    res.add(cur);
                    set.add(cur.city2);
                    priorityQueue.addAll(graph.get(cur.city2));
                }
            }
        }
        Collections.sort(res,new ConnectionComparator());
        return res;
    }
    public  HashMap<String,List<Connection1>> buildGraph(List<Connection1> connections){
        HashMap<String,List<Connection1>> res =new HashMap<>();
        for (Connection1 con:connections) {
            if (!res.containsKey(con.city1))
                res.put(con.city1,new ArrayList<>());
            if (!res.containsKey(con.city2))
                res.put(con.city2,new ArrayList<>());
            List<Connection1> c1 =res.get(con.city1);
            List<Connection1> c2 =res.get(con.city2);
            c1.add(con);
            c2.add(con);
        }
        return res;
    }
    //Dijkstra
    public HashMap<Node,Integer> dijkstra2(Node head){
        HashMap<Node,Integer> distanceMap =new HashMap<>();
        distanceMap.put(head,0);
        HashSet<Node> selected =new HashSet<>();
        Node minNode =getUnselectedMinNode(distanceMap,selected);
        while(minNode!=null){
            int distance =distanceMap.get(minNode);
            for (Edge edge: minNode.edges) {
                Node toNode =edge.to;
                if (!distanceMap.containsKey(toNode)){
                    distanceMap.put(toNode,distance+edge.weight);
                }else {
                    distanceMap.put(toNode,Math.min(distanceMap.get(toNode),distance+ edge.weight));
                }
            }
            selected.add(minNode);
            minNode=getUnselectedMinNode(distanceMap,selected);
        }
        return distanceMap;
    }
    public Node getUnselectedMinNode(HashMap<Node,Integer> distanceMap,HashSet<Node> selected){
        Node minNode =null;
        int minDistance =Integer.MAX_VALUE;
        for (Node node: distanceMap.keySet()) {
            int distance =distanceMap.get(node);
            if(!selected.contains(node) && distance<minDistance){
                minNode =node;
                minDistance=distance;
            }
        }
        return minNode;
    }
    //拓扑排序
    public ArrayList<DirectedGraphNode> topSort2(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode,Integer> inMap =new HashMap<>();
        for (DirectedGraphNode node:graph) {
            for (DirectedGraphNode next:node.neighbors) {
                if (!inMap.containsKey(next)){
                    inMap.put(next,0);
                }
                inMap.put(next,inMap.get(next)+1);
            }
        }
        ArrayList<DirectedGraphNode> res =new ArrayList<>();
        Queue<DirectedGraphNode> zeroIn =new LinkedList<>();
        for (DirectedGraphNode node:graph) {
            if (!inMap.containsKey(node))
                zeroIn.add(node);
        }
        while(!zeroIn.isEmpty()){
            DirectedGraphNode cur =zeroIn.poll();
            res.add(cur);
            for (DirectedGraphNode next:cur.neighbors) {
                inMap.put(next,inMap.get(next)-1);
                if (inMap.get(next)==0)
                    zeroIn.add(next);
            }
        }
        return res;
    }
}
class Trie2 {
    public TrieNode root;
    Trie2(){
        root =new TrieNode();
    }
    class TrieNode{
        public int pass;
        public int end;
        TrieNode[] nexts;
        TrieNode(){
            pass=0;
            end=0;
            nexts =new TrieNode[26];
        }
    }
    public void insert(String word){
        if (word == null) {
            return;
        }
        TrieNode cur =root;
        char[] ch =word.toCharArray();
        int index=0;
        cur.pass++;
        for (int i = 0; i < ch.length; i++) {
            index=ch[i]-'a';
            if (cur.nexts[index]==null){
                cur.nexts[index]=new TrieNode();
            }
            cur.nexts[index].pass++;
            cur=cur.nexts[index];
        }
        cur.end++;
    }
    int search(String word){
        TrieNode cur =root;
        int index =0;
        char[] ch =word.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            index=ch[i]-'a';
            if (cur.nexts[index]==null)
                return 0;
            cur=cur.nexts[index];
        }
        return cur.end;
    }
    int prefix(String word){
        TrieNode cur =root;
        int index =0;
        char[] ch =word.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            index=ch[i]-'a';
            if (cur.nexts[index]==null)
                return 0;
            cur=cur.nexts[index];
        }
        return cur.pass;
    }
    void delete(String word){
        if (search(word)!=0){
            TrieNode cur =root;
            int index =0;
            cur.pass--;
            char[] ch =word.toCharArray();
            for (int i = 0; i < ch.length; i++) {
                index=ch[i]-'a';
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
class Connection1 {
    public String city1, city2;
    public int cost;

    public Connection1(String city1, String city2, int cost) {
        this.city1 = city1;
        this.city2 = city2;
        this.cost = cost;
    }
}

//拓扑排序
class DirectedGraphNode {
    int label;
    List<DirectedGraphNode> neighbors;

    DirectedGraphNode(int x) {
        label = x;
        neighbors = new ArrayList<DirectedGraphNode>();
    }
}