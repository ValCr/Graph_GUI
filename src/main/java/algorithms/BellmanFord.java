package algorithms;

import graph.Constants;
import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.*;

public class BellmanFord extends ShortestPathAlgorithm {
    public BellmanFord(Graph graph) {
        super(graph);
    }

    @Override
    public void apply() {
        assert startVertex != null;
        assert endVertex != null;

        predecessors = new LinkedHashMap<>();
        distances = new HashMap<>(graph.getVertices().size());

        // initialize distances from startvertex too all other vertices as infinite
        graph.getVertices().forEach(v -> distances.put(v, Double.POSITIVE_INFINITY));
        distances.put(startVertex, 0.0);


        // update shortest for all vertices N - 1 where N is the number of vertices :
        // a shortest path from startvertex to any other vertex can only have at most N - 1 edges
        for (int i = 1; i < graph.getVertices().size(); i++) {
            graph.getEdges().forEach(e -> updateDistances(e.getStart(), e));
            if (!graph.isOriented()) {
                graph.getEdges().forEach(e -> updateDistances(e.getEnd(), e));
            }
        }
    }

    // check for negative-cost circuits
    @Override
    public boolean conditionsAreValid() {
        return graph.getEdges().stream()
                .noneMatch(e -> distances.get(e.getStart()) + e.getCost() < distances.get(e.getEnd()));
    }

    // print the negative-cost circuit found
    @Override
    public void updateInfoAlgo() {
        List<Vertex> negativeCircuit = getNegativeCircuit();
        negativeCircuit.forEach(v -> {
            v.setFill(Constants.VERTEX_COLOR_WHEN_VISITED);
            v.getEdgeFromAdjacentVertex(predecessors.get(v)).setStroke(Constants.VERTEX_COLOR_WHEN_VISITED);
        });
        mainController.getGraphPaneController().getInfoAlgo().setText(
                "Graph contains a circuit with negative cost :\n" + verticesToString(negativeCircuit));
    }

    private List<Vertex> getNegativeCircuit() {
        Edge e = graph.getEdges().stream()
                .filter(edge -> distances.get(edge.getStart()) + edge.getCost() < distances.get(edge.getEnd()))
                .findFirst().orElseThrow(() -> new NoSuchElementException("No circuit with negative cost found"));
        List<Vertex> visited = new ArrayList<>(), negativeCircuit = new ArrayList<>();
        Vertex u = e.getStart();

        while (!visited.contains(u)) {
            visited.add(u);
            u = predecessors.get(u);
        }

        while (u != visited.get(visited.size() - 1)) {
            negativeCircuit.add(u);
            u = predecessors.get(u);
        }
        negativeCircuit.add(u);
        negativeCircuit.add(negativeCircuit.get(0));

        return negativeCircuit;
    }
}
