package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

/**
 * Depth-first-search algorithm.
 * <p>
 * INPUT: A graph and a source vertex <b>s</b>.
 * <p>
 * OUTPUT: The shortest paths from <b>s</b> to all the other vertices if the graph is not weighted.
 * <p>
 * DFS can be used to check if the graph is connected.
 */
public class DFS extends SearchingAlgorithm {
    private int[] state;
    private int time;

    public DFS(Graph graph) {
        super(graph);
        time = 0;
    }

    @Override
    public void apply() {
        if (startVertex == null) {
            startVertex = graph.getVertices().get(0);
        }
        state = new int[graph.getVertices().size()];
        dfs(startVertex);
    }

    private void dfs(Vertex v) {
        int UNVISITED = 0, CLOSED = 2, OPEN = 3;

        v.setStartDate(++time);
        state[graph.getVertices().indexOf(v)] = OPEN;
        orderOfDiscovery.add(v);

        for (Edge e : v.getEdges()) {
            Vertex u = e.getOtherEnd(v);
            if (state[graph.getVertices().indexOf(u)] == UNVISITED) {
                dfs(u);
            } else if (state[graph.getVertices().indexOf(u)] == OPEN) {
                graph.setContainsCircuit(true);
            }
        }

        state[graph.getVertices().indexOf(v)] = CLOSED;
        v.setEndDate(++time);
    }
}