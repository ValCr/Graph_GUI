package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.ArrayDeque;
import java.util.Queue;

public class BFS extends SearchingAlgorithm {
    public BFS(Graph graph) {
        super(graph);
    }

    @Override
    public void apply(Vertex v) {
        Queue<Vertex> q = new ArrayDeque<>();

        discovered[graph.getVertices()
                .indexOf(v)] = true;
        orderOfDiscovery.add(v);
        q.add(v);

        while (!q.isEmpty()) {
            v = q.poll();

            int uPos;
            for (Edge e : v.getEdges()) {
                Vertex u = e.getOtherEnd(v);
                uPos = graph.getVertices()
                        .indexOf(u);
                if (!discovered[uPos]) {
                    discovered[uPos] = true;
                    orderOfDiscovery.add(u);
                    q.add(u);
                }
            }

        }
    }
}
