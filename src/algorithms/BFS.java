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
    public void apply() {
        Queue<Vertex> q = new ArrayDeque<>();

        discovered[graph.getVertices()
                .indexOf(startVertex)] = true;
        orderOfDiscovery.add(startVertex);
        q.add(startVertex);

        while (!q.isEmpty()) {
            startVertex = q.poll();

            int uPos;
            for (Edge e : startVertex.getEdges()) {
                Vertex endVertex = e.getOtherEnd(startVertex);
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
