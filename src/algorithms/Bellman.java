package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Bellman extends ShortestPathAlgorithm {

    public Bellman(Graph graph) {
        super(graph);
    }

    @Override
    public void apply() {
        SearchingAlgorithm dfs = new DFS(graph);
        dfs.setStartVertex(startVertex);
        dfs.apply();

        List<Vertex> topologicalOrder = dfs.getOrderOfDiscovery();
        topologicalOrder.sort(Comparator.comparing(Vertex::getEndDate).reversed());

        if (topologicalOrder.isEmpty()) {
            return;
        }


        predecessors = new LinkedHashMap<>();
        distances = new HashMap<>(topologicalOrder.size());

        topologicalOrder.forEach(v -> {
            distances.put(v, Integer.MAX_VALUE);
            predecessors.put(v, null);
        });
        distances.put(startVertex, 0);

        Vertex u;
        for (Vertex v : topologicalOrder) {
            for (Edge e : v.getEdges()) {
                u = e.getOtherEnd(v);
                if (distances.get(v) + e.getCost() < distances.get(u)) {
                    distances.put(u, distances.get(v) + e.getCost());
                    predecessors.put(u, v);
                }
            }
        }

        //TODO
        // assert graph is oriented
        // assert graph is without circuit
    }


}
