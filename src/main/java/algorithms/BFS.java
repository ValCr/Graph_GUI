package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Breadth-first-search algorithm.
 * <p>
 * INPUT: A graph and a source vertex <b>s</b>.
 * <p>
 * OUTPUT: The shortest paths from <b>s</b> to all the other vertices if the graph is not weighted.
 * <p>
 * BFS can be used to check if the graph is connected.
 */
public class BFS extends SearchingAlgorithm {
    private final boolean[] discovered;

    public BFS(Graph graph) {
        super(graph);
        discovered = new boolean[graph.getVertices().size()];
    }

    @Override
    public void apply() {
        if (startVertex == null) {
            startVertex = graph.getVertices().get(0);
        }
        Queue<Vertex> q = new ArrayDeque<>();

        discovered[graph.getVertices().indexOf(startVertex)] = true;
        orderOfDiscovery.add(startVertex);
        q.add(startVertex);

        Vertex v;
        int uPos;
        while (!q.isEmpty()) {
            v = q.poll();
            for (Edge e : v.getEdges()) {
                Vertex endVertex = e.getOtherEnd(v);
                uPos = graph.getVertices().indexOf(endVertex);
                if (!discovered[uPos]) {
                    discovered[uPos] = true;
                    orderOfDiscovery.add(endVertex);
                    q.add(endVertex);
                }
            }
        }

    }
}
