import java.util.*;

public class Graph {
    public HashMap<Integer, Node> nodes;
    public HashSet<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

    //BFS遍历图
    public static void graphBFS(Node node) {
        if (node == null) {
            return;
        }
        Queue<Node> q = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        q.add(node);
        set.add(node);
        while (!q.isEmpty()) {
            node = q.poll();
            System.out.println(node.value);
            for (Node n : node.nexts) {
                //BFS一次走一层，因此走完所有后续点
                if (!set.contains(n)) {
                    q.add(n);
                    set.add(n);
                }
            }
        }
    }

    //DFS遍历图
    public static void graphDFS(Node node) {
        if (node == null) {
            return;
        }
        Stack<Node> s = new Stack<>();
        HashSet<Node> set = new HashSet<>();//用于检验点是否被遍历过
        s.push(node);
        set.add(node);
        System.out.println(node.value);//输出第一个点，后面的代码不会输出第一个点
        while (!s.isEmpty()) {
            node = s.pop();
            for (Node n : node.nexts) {
                //一次只走一条路径
                //因此需要break
                if (!set.contains(n)) {
                    System.out.println(n.value);
                    s.push(node);//该点可能还有其他路径，需要将他放回栈中
                    s.push(n);
                    set.add(n);
                    break;
                }
            }
        }
    }

    //拓扑排序 有向图 无环
    public static List<Node> sortedTopology(Graph graph) {
        HashMap<Node, Integer> inMap = new HashMap<>();//保存节点，和他剩余的入度
        Queue<Node> zeroIn = new LinkedList<>();//入度为0的点才入队
        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0)
                zeroIn.add(node);
        }
        List<Node> res = new ArrayList<>();
        while (!zeroIn.isEmpty()) {
            Node cur = zeroIn.poll();
            res.add(cur);
            //抹去当前点对其他点的关系（将当前点的后续点的入度减1）
            for (Node node : cur.nexts) {
                inMap.put(node, inMap.get(node) - 1);
                //抹去关系后有入度为0的，入队
                if (inMap.get(node) == 0)
                    zeroIn.add(node);
            }
        }
        return res;
    }

    //最小生成树（K算法） 无向图
    public static class MySets {
        public HashMap<Node, List<Node>> setMap;

        public MySets(List<Node> nodes) {
            setMap = new HashMap<>();
            for (Node node : nodes) {
                List<Node> n = new ArrayList<>();
                n.add(node);
                setMap.put(node, n);
            }
        }

        public boolean isSameSet(Node from, Node to) {
            List<Node> fromSets = setMap.get(from);
            List<Node> toSets = setMap.get(to);
            return fromSets == toSets;
        }

        public void union(Node from, Node to) {
            List<Node> fromSets = setMap.get(from);
            List<Node> toSets = setMap.get(to);
            for (Node node : toSets) {
                fromSets.add(node);
                setMap.put(node, fromSets);
            }
        }
    }

    public static class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> kruskalMST(Graph graph) {
        MySets mySets = new MySets(new ArrayList<Node>(graph.nodes.values()));
        PriorityQueue<Edge> queue = new PriorityQueue<>(new EdgeComparator());
        for (Edge edge : graph.edges) {
            queue.add(edge);
        }
        Set<Edge> res = new HashSet<>();
        while (!queue.isEmpty()) {
            Edge cur = queue.poll();
            if (!mySets.isSameSet(cur.from, cur.to)) {
                res.add(cur);
                mySets.union(cur.from, cur.to);
            }
        }
        return res;
    }

    //最小生成树（P算法） 无向图
    //左神的代码原本是直接返回res，他的题目不需要考虑输出顺序，如果有输出顺序要求就要对res进行排序再返回！！！！！
    public static List<Edge> primMST(Graph graph) {
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        HashSet set = new HashSet();
        List<Edge> res = new ArrayList<>();
        //可能给的图不是连通图（有多个小图），所以加个foreach循环，来使得各个图都生成最小生成树
        //如果不考虑该因素，这个foreach循环可以不加
        for (Node node : graph.nodes.values()) {
            if (!set.contains(node)) {//判断该点是否被遍历过
                set.add(node);
                priorityQueue.addAll(node.edges);//将新加入的点所有的边放入优先队列排序
                while (!priorityQueue.isEmpty()) {
                    Edge cur = priorityQueue.poll();
                    Node to = cur.to;//可能是新的点
                    Node from = cur.from;
                    if (!set.contains(to)) {//如果是新的点就放入答案中并且添加到set中，最后将新加入的点所有的边放入优先队列排序
                        res.add(cur);
                        set.add(to);
                        priorityQueue.addAll(to.edges);
                    }
                    if (!set.contains(from)) {
                        res.add(cur);
                        set.add(from);
                        priorityQueue.addAll(from.edges);
                    }
                }
            }
        }
        Collections.sort(res, new EdgeComparator());//要排序！！！！！
        return res;
    }

    //Dijkstra算法解决最短路径问题
    //要求是边的权值不能为负值
    public static HashMap<Node, Integer> dijkstra(Node head) {
        //distanceMap 表示从head点到某点的距离
        //key 某点
        //value head到key点的距离
        //函数返回head到key点的最短距离
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        //查看完了点就放入selectedSet中，说明该点已经是最短路径了
        HashSet<Node> selectedSet = new HashSet<>();
        //head点到自己定义为0，没有出现在distanceMap的点认为，head到该点距离为无穷大
        distanceMap.put(head, 0);
        //挑选出一个没有被遍历过且距离head点距离最小的点（以下简称最小点）
        Node minNode = getMinNodeAndUnselected(distanceMap, selectedSet);
        while (minNode!=null){
            int distance = distanceMap.get(minNode);
            for (Edge edge:minNode.edges) {
                Node toNode =edge.to;//选出这个最小点以后，那么提取边的另一端也就是edge.to
                //如果不存在就直接将距离输入
                //否则就要对之前输入的距离进行比较，取较小值
                if (!distanceMap.containsKey(toNode)){
                    distanceMap.put(toNode,distance+ edge.weight);
                }else{
                    distanceMap.put(toNode,Math.min(distanceMap.get(toNode),distance+ edge.weight));
                }
            }
            //循环结束说明，所有与最小点有关系的边和点都已经进行了比较
            selectedSet.add(minNode);
            //选择下个最小点
            minNode=getMinNodeAndUnselected(distanceMap,selectedSet);
        }
         return distanceMap;
    }

    public static Node getMinNodeAndUnselected(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedSet) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (!selectedSet.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }

    public static void main(String[] args) {
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(4);
        Edge e1=new Edge(2,a,b);
        Edge e11=new Edge(2,b,a);
        Edge e2=new Edge(10,a,c);
        Edge e12=new Edge(2,c,a);
        Edge e3=new Edge(15,a,d);
        Edge e13=new Edge(2,d,a);
        Edge e4=new Edge(10,b,d);
        Edge e14=new Edge(2,d,b);
        Edge e5=new Edge(3,b,c);
        Edge e15=new Edge(2,c,b);
        Edge e6=new Edge(6,c,d);
        Edge e16=new Edge(2,d,c);
        a.edges.add(e1);
        a.edges.add(e2);
        a.edges.add(e3);
        b.edges.add(e4);
        b.edges.add(e5);
        b.edges.add(e11);
        c.edges.add(e6);
        c.edges.add(e12);
        c.edges.add(e15);
        d.edges.add(e13);
        d.edges.add(e14);
        d.edges.add(e16);
        a.nexts.add(b);
        b.nexts.add(a);
        a.nexts.add(c);
        c.nexts.add(a);
        a.nexts.add(d);
        d.nexts.add(a);
        b.nexts.add(d);
        d.nexts.add(b);
        b.nexts.add(c);
        c.nexts.add(b);
        c.nexts.add(d);
        d.nexts.add(c);
//        graphBFS(a);
        HashMap<Node, Integer> dijkstra = dijkstra(a);
        for (Node node:dijkstra.keySet()) {
            System.out.println(node.value+" "+dijkstra.get(node));
        }
    }
}

class Node {
    public int value;
    public int in;
    public int out;
    public ArrayList<Node> nexts;
    public ArrayList<Edge> edges;

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}

class Edge {
    public int weight;
    public Node from;
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
