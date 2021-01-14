package algorithms;

import graph.Edge;
import graph.Graph;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Bellman-Ford's algorithm.
 * <p>
 * INPUT: A graph.
 * <p>
 * OUTPUT: A minimum spanning forest. If the graph is connected, it finds a minimum spanning tree.
 */
public class Prim extends MinSpanningTreeAlgorithm {
    public Prim(Graph graph) {
        super(graph);
    }

    @Override
    public void apply() {
        double minCost = graph.getEdges()
                .stream()
                .map(Edge::getCost)
                .min(Comparator.comparingDouble(Double::valueOf))
                .orElse(0.0);
        if (minCost < 0) {
            graph.getEdges().forEach(e -> e.setCost(e.getCost() - minCost));
        }
        Dijsktra dijsktra = new Dijsktra(graph);
        dijsktra.setStartVertex(vertices.get(0));
        dijsktra.setEndVertex(vertices.get(0));
        dijsktra.apply();
        tree = dijsktra.getPredecessors()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().getEdgeFromAdjacentVertex(entry.getValue()))
                .collect(Collectors.toList());
        tree.sort(Comparator.comparing(Edge::getCost));
        if (minCost < 0) {
            graph.getEdges().forEach(e -> e.setCost(e.getCost() + minCost));
        }
    }
}
