package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Dijsktra's algorithm.
 * <p>
 * INPUT: A graph with no negative cost and a source vertex <b>s</b>.
 * <p>
 * OUTPUT: The shortest paths from <b>s</b> to all the other vertices.
 */
public class Dijsktra extends ShortestPathAlgorithm {

    public Dijsktra(Graph graph) {
        super(graph);
    }

    @Override
    public void apply() {
        assert startVertex != null;
        assert endVertex != null;

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
            distancesCopy.remove(v);
            for (Edge e : v.getEdges()) {
                Vertex u = e.getOtherEnd(v);
                if (distancesCopy.containsKey(u)) {
                    updateDistances(v, e);
                    distancesCopy.put(u, distances.get(u));
                }
            }
        }
    }

    @Override
    protected boolean conditionsAreValid() {
        return graph.getEdges().stream().allMatch(e -> e.getCost() >= 0) && startVertex != null && endVertex != null;
    }

    @Override
    public void updateInfoAlgo() {
        mainController.getGraphPaneController()
                .getInfoAlgo()
                .setText("Graph contains an " + (graph.isOriented() ? "arc" : "edge") + " with negative cost.");
    }
}

