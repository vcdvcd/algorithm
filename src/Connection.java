import java.util.*;

//题目要求
//给定一个Connections，即Connection类（边缘两端的城市名称和它们之间的开销），找到可以连接所有城市并且花费最少的边缘。
//如果可以连接所有城市，则返回连接方法，否则返回空列表。
//返回按成本排序的连接方法，或者如果其成本相同，就按照city1名称进行排序，如果city1名称也相同，则按照city2进行排序。
public class Connection {
    public String city1, city2;
    public int cost;

    public Connection(String city1, String city2, int cost) {
        this.city1 = city1;
        this.city2 = city2;
        this.cost = cost;
    }
    //prim算法解决最小生成树
    public List<Connection> lowestCost(List<Connection> connections) {
        // Write your code here
        HashMap<String,List<Connection>> graph = buildGraph(connections);
        HashSet<String> set =new HashSet<>();
        PriorityQueue<Connection> priorityQueue =new PriorityQueue<>(new ConnectionComparator());
        List<Connection> res =new ArrayList<>();
        String con =connections.get(0).city1;
        if(!set.contains(con)){
            set.add(con);
            priorityQueue.addAll(graph.get(con));
            while(!priorityQueue.isEmpty()){
                Connection cur =priorityQueue.poll();
                String c2 =cur.city2;
                String c1 =cur.city1;
                if(!set.contains(c2)){
                    set.add(c2);
                    res.add(cur);
                    priorityQueue.addAll(graph.get(c2));
                }
                if(!set.contains(c1)){
                    set.add(c1);
                    res.add(cur);
                    priorityQueue.addAll(graph.get(c1));
                }
            }
        }
        if(set.size()!=graph.keySet().size())
            return new ArrayList<>();
        Collections.sort(res,new ConnectionComparator());
        return res;
    }
    public HashMap<String,List<Connection>> buildGraph(List<Connection> connections){
        HashMap<String,List<Connection>> graph =new HashMap<>();
        for (Connection con  : connections) {
            if(!graph.containsKey(con.city1))
                graph.put(con.city1,new ArrayList<>());
            if(!graph.containsKey(con.city2))
                graph.put(con.city2,new ArrayList<>());
            List<Connection> c1 =graph.get(con.city1);
            List<Connection> c2 =graph.get(con.city2);
            c1.add(con);
            c2.add(con);
        }
        return graph;
    }
    public static class ConnectionComparator implements Comparator<Connection> {
        public int compare(Connection o1, Connection o2) {
            if (o1.cost != o2.cost) {
                return o1.cost - o2.cost;
            }
            if (!o1.city1.equals(o2.city1)) {
                return o1.city1.compareTo(o2.city1);
            }
            return o1.city2.compareTo(o2.city2);
        }
    }
    //kruskal算法解决最小生成树
    public List<Connection> lowestCost2(List<Connection> connections) {
        // Write your code here
        Mysets sets =new Mysets(connections);
        PriorityQueue<Connection> priorityQueue =new PriorityQueue<>(new ConnectionComparator());
        for (Connection c : connections) {
            priorityQueue.add(c);
        }
        List<Connection> res =new ArrayList<>();
        while(!priorityQueue.isEmpty()){
            Connection cur =priorityQueue.poll();
            if(!sets.isSameSet(cur.city1, cur.city2)){
                res.add(cur);
                sets.union(cur.city1, cur.city2);
            }
        }
        List<String> check = sets.setMap.get(connections.get(0).city1);
        for(int i=1;i<sets.setMap.size();i++){
            if(sets.setMap.get(connections.get(i).city1)!=check ||
                    sets.setMap.get(connections.get(i).city2)!=check)
                return new ArrayList<Connection>();
        }
        return res;
    }
    public static class Mysets {
        public HashMap<String, List<String>> setMap;

        public Mysets(List<Connection> connections) {
            setMap = new HashMap<>();
            for (Connection con : connections) {
                List<String> v1 = new ArrayList<>();
                List<String> v2 = new ArrayList<>();
                v1.add(con.city1);
                v2.add(con.city2);
                setMap.put(con.city1, v1);
                setMap.put(con.city2, v2);
            }
        }

        public boolean isSameSet(String city1, String city2) {
            List<String> c1 = setMap.get(city1);
            List<String> c2 = setMap.get(city2);
            return c1 == c2;
        }

        public void union(String city1, String city2) {
            List<String> c1 = setMap.get(city1);
            List<String> c2 = setMap.get(city2);
            for (String c : c2) {
                c1.add(c);
                setMap.put(c, c1);
            }
        }
    }
}
