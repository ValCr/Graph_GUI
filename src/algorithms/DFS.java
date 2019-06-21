package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.Stack;

public class DFS extends SearchingAlgorithm {
    public DFS(Graph graph) {
        super(graph);
    }

    @Override
    public void apply(Vertex v) {
        Stack<Vertex> stack = new Stack<>();
        int vPos;
        stack.push(v);

        while (!stack.empty()) {
            v = stack.pop();

            vPos = graph.getVertices()
                    .indexOf(v);
            if (discovered[vPos]) {
                continue;
            }

            discovered[vPos] = true;
            orderOfDiscovery.add(v);

            for (Edge e : v.getEdges()) {
                Vertex u = e.getOtherEnd(v);
                if (!discovered[graph.getVertices()
                        .indexOf(u)]) {
                    stack.push(u);
                }
            }

        }
    }

}