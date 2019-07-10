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
        assert startVertex != null;
        assert endVertex != null;

        SearchingAlgorithm dfs = new DFS(graph);
        dfs.setStartVertex(startVertex);
        dfs.apply();

        List<Vertex> topologicalOrder = dfs.getOrderOfDiscovery();
        topologicalOrder.sort(Comparator.comparing(Vertex::getEndDate).reversed());

        predecessors = new LinkedHashMap<>();
        distances = new HashMap<>(topologicalOrder.size());

        if (conditionsAreValid()) {
            topologicalOrder.forEach(v -> {
                distances.put(v, Double.POSITIVE_INFINITY);
                predecessors.put(v, null);
            });
            distances.put(startVertex, 0.0);

            for (Vertex v : topologicalOrder) {
                for (Edge e : v.getEdges()) {
                    updateDistances(v, e);
                }
            }
        }
    }

    @Override
    public boolean conditionsAreValid() {
        return !graph.containsCircuit();
    }

    @Override
    public void updateInfoAlgo() {
        mainController.getGraphPaneController().getInfoAlgo().setText("Graph contains a circuit.");
    }
}
