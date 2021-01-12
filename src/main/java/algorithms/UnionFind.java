package algorithms;

import graph.Vertex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnionFind {
    private final Map<Vertex, Subset> subsets;

    public UnionFind(List<Vertex> vertices) {
        subsets = new HashMap<>(vertices.size());
        vertices.forEach(v -> subsets.put(v, new Subset(v)));
    }

    public Vertex find(Vertex v) {
        if (subsets.get(v) == null) {
            return null;
        } else if (subsets.get(v).parent != v) {
            subsets.get(v).parent = find(subsets.get(v).parent);
        }
        return subsets.get(v).parent;
    }

    public void union(Vertex u, Vertex v) {
        Vertex uRoot = find(u);
        Vertex vRoot = find(v);
        if (subsets.get(uRoot).rank < subsets.get(vRoot).rank) {
            subsets.get(uRoot).parent = vRoot;
        } else if (subsets.get(vRoot).rank < subsets.get(uRoot).rank) {
            subsets.get(vRoot).parent = uRoot;
        } else {
            subsets.get(uRoot).parent = vRoot;
            subsets.get(vRoot).rank++;
        }
    }

    private class Subset {
        Vertex parent;
        int rank;

        public Subset(Vertex v) {
            parent = v;
            rank = 0;
        }
    }
}
