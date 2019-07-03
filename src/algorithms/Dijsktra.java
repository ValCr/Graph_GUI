package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Dijsktra extends ShortestPathAlgorithm {

    public Dijsktra(Graph graph) {
        super(graph);
    }

    @Override
    public void apply() {
        predecessors = new LinkedHashMap<>();
        distances = new HashMap<>(graph.getVertices().size());

        if (conditionsAreValid()) {
            graph.getVertices().forEach(v -> distances.put(v, Double.POSITIVE_INFINITY));
            distances.put(startVertex, 0.0);
        }
        Map<Vertex, Double> distancesCopy = new HashMap<>(distances);
        Vertex v;
        while (!distancesCopy.isEmpty()) {
            v = Collections.min(distancesCopy.entrySet(), Map.Entry.comparingByValue()).getKey();
            if (!predecessors.containsKey(v)) {
                predecessors.put(v, null);
            }
            distancesCopy.remove(v);
            for (Edge e : v.getEdges()) {
                Vertex u = e.getOtherEnd(v);
                updateDistances(v, e);
                if (distancesCopy.containsKey(u)) {
                    distancesCopy.put(u, distances.get(u));
                }
            }
        }
    }

    @Override
    public boolean conditionsAreValid() {
        return graph.getEdges().stream().allMatch(e -> e.getCost() >= 0);
    }

    @Override
    public void updateInfoAlgo() {
        mainController.getGraphPaneController().getInfoAlgo()
                .setText("Graph contains an " + (graph.isOriented() ? "arc" : "edge") + " with negative cost.");
    }
}

