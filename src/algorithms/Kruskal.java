package algorithms;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kruskal extends MinSpanningTreeAlgorithm {
    public Kruskal(Graph graph) {
        super(graph);
    }

    @Override
    public void apply() {
        tree = new ArrayList<>(graph.getEdges().size());
        UnionFind set = new UnionFind(vertices);
        List<Edge> orderedEdges = new ArrayList<>(graph.getEdges());
        orderedEdges.sort(Comparator.comparing(Edge::getCost));

        orderedEdges.forEach(e -> {
            if (set.find(e.getStart()) != set.find(e.getEnd())) {
                tree.add(e);
                set.union(e.getStart(), e.getEnd());
            }
        });
    }
}