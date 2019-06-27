package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        topologicalOrder = topologicalOrder.subList(topologicalOrder.indexOf(startVertex), topologicalOrder.size());

        if (topologicalOrder.isEmpty()) {
            return;
        }

        Map<Vertex, Integer> distances = new HashMap<>(topologicalOrder.size());
        Map<Vertex, Vertex> predecessors = new HashMap<>(topologicalOrder.size());
        topologicalOrder.forEach(v -> {
            distances.put(v, Integer.MAX_VALUE);
            predecessors.put(v, startVertex);
        });
        distances.put(startVertex, 0);
        predecessors.remove(startVertex);
        System.out.println(predecessors);
        System.out.println(distances);

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
        // assert startvertex and endvertex != null
    }


}
