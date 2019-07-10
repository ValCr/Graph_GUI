package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.ArrayDeque;
import java.util.Queue;

public class BFS extends SearchingAlgorithm {
    protected boolean[] discovered;

    public BFS(Graph graph) {
        super(graph);
        discovered = new boolean[graph.getVertices().size()];
    }

    @Override
    public void apply() {
        assert startVertex != null;

        Queue<Vertex> q = new ArrayDeque<>();

        discovered[graph.getVertices()
                .indexOf(startVertex)] = true;
        orderOfDiscovery.add(startVertex);
        q.add(startVertex);

        Vertex v;
        int uPos;
        while (!q.isEmpty()) {
            v = q.poll();
            for (Edge e : v.getEdges()) {
                Vertex endVertex = e.getOtherEnd(v);
                uPos = graph.getVertices()
                        .indexOf(endVertex);
                if (!discovered[uPos]) {
                    discovered[uPos] = true;
                    orderOfDiscovery.add(endVertex);
                    q.add(endVertex);
                }
            }
        }

    }
}
