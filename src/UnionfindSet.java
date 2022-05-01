import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//并查集
public class UnionfindSet<V> {
    //给每个元素都套上一圈，可以处理多种类型
    public HashMap<V, Element<V>> elementMap;
    //每个元素他们这个集合的代表元素
    public HashMap<Element<V>, Element<V>> fatherMap;
    //代表元素所在集合有几个元素
    public HashMap<Element<V>, Integer> sizeMap;

    UnionfindSet(List<V> list) {
        elementMap = new HashMap<>();
        fatherMap = new HashMap<>();
        sizeMap = new HashMap<>();
        for (V value : list) {
            Element<V> element = new Element<>(value);
            elementMap.put(value, element);
            fatherMap.put(element, element);
            sizeMap.put(element, 1);
        }
    }

    //查找代表元素，同时路径压缩
    public Element<V> findHead(Element<V> a) {
        if (a == fatherMap.get(a))
            return a;
        Element e = findHead(fatherMap.get(a));
        fatherMap.put(a, e);
        return e;
    }

    //代表元素相同就认为集合相同
    public boolean isSameSet(V a, V b) {
        if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
            return findHead(elementMap.get(a)) == findHead(elementMap.get(b));
        }
        return false;
    }

    //合并集合
    public void union(V a, V b) {
        if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
            Element aF = findHead(elementMap.get(a));//找到a元素的代表元素
            Element bF = findHead(elementMap.get(b));//找到b元素的代表元素
            if (aF != bF) {
                Element big = sizeMap.get(aF) >= sizeMap.get(bF) ? aF : bF;//找出较大总量的集合
                Element small = big == aF ? bF : aF;
                sizeMap.put(big, sizeMap.get(aF) + sizeMap.get(bF));//更新较大集合的总数
                fatherMap.put(small, big);//合并两个集合
                sizeMap.remove(small);//删除较小集合
            }
        }
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.addAll(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7}));
        UnionfindSet<Integer> unionfindSet = new UnionfindSet<>(list);
        unionfindSet.union(1,2);
        unionfindSet.union(1,5);
        unionfindSet.union(1,7);
        System.out.println(unionfindSet.isSameSet(1, 2));
    }
}

class Element<V> {
    public V value;

    Element(V value) {
        this.value = value;
    }
}