import java.util.HashMap;
//dijkstra 改进算法 用堆加速
public class Dijkstra {
    public static class NodeRecord{
        public Node node;
        public int distance;
        NodeRecord(Node n,int d){
            node=n;
            distance=d;
        }
    }
    public static class NodeHeap{
        public Node[] nodes;
        public HashMap<Node,Integer> indexMap;
        public HashMap<Node,Integer> distanceMap;
        public int size;
        NodeHeap(int size){
            nodes=new Node[size];
            indexMap=new HashMap<>();
            distanceMap=new HashMap<>();
            this.size=0;
        }
        public boolean isEmpty(){
            return size==0;
        }
        public boolean isEntered(Node head){
            return indexMap.containsKey(head);
        }
        public boolean inHeap(Node head){
            return indexMap.containsKey(head) && indexMap.get(head)!=-1;
        }
        public void swap(int index1,int index2){
            indexMap.put(nodes[index1],index2);
            indexMap.put(nodes[index2],index1);
            Node tmp = nodes[index1];
            nodes[index1]=nodes[index2];
            nodes[index2]=tmp;
        }
        public void insertHeapify(int index){
            while(distanceMap.get(nodes[index])<distanceMap.get(nodes[(index-1)/2])){
                swap(index,(index-1)/2);
                index=(index-1)/2;
            }
        }
        public void heapify(int index,int heapSize){
            int left =index*2+1;
            while(left<heapSize){
                int smallest = left+1<heapSize &&
                        distanceMap.get(nodes[left+1])<distanceMap.get(nodes[left])?left+1:left;
                smallest = distanceMap.get(nodes[index])<distanceMap.get(nodes[smallest])?index:smallest;
                if (smallest==index)
                    break;
                swap(index,smallest);
                index=smallest;
                left=index*2+1;
            }
        }
        public void addOrUpdateOrIgnore(Node node,int distance){
            if (!isEntered(node)){
                nodes[size]=node;
                indexMap.put(node,size);
                distanceMap.put(node,distance);
                insertHeapify(size++);
            }
            if (inHeap(node)){
                distanceMap.put(node,Math.min(distanceMap.get(node),distance));
                insertHeapify(indexMap.get(node));
            }
        }
        public NodeRecord pop(){
            int distance =distanceMap.get(nodes[0]);
            Node node =nodes[0];
            swap(0,size-1);
            indexMap.put(nodes[size-1],-1);
            distanceMap.remove(nodes[size-1]);
            nodes[size-1]=null;
            heapify(0,--size);
            return new NodeRecord(node,distance);
        }
    }
    public static HashMap<Node,Integer> dijkstra(Node head,int size){
        NodeHeap nodeHeap =new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head,0);
        HashMap<Node,Integer> res =new HashMap<>();
        while(!nodeHeap.isEmpty()){
            NodeRecord nodeRecord =nodeHeap.pop();
            Node node = nodeRecord.node;
            int distance = nodeRecord.distance;
            for (Edge edge:node.edges) {
                Node toNode =edge.to;
                nodeHeap.addOrUpdateOrIgnore(toNode,distance+edge.weight);
            }
            res.put(node,distance);
        }
        return res;
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
        HashMap<Node, Integer> dijkstra = dijkstra(a, 4);
        for (Node node:dijkstra.keySet()) {
            System.out.println(node.value+" "+dijkstra.get(node));
        }
    }
}
