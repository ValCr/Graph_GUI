package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

public class DFS extends SearchingAlgorithm {
    private int time;

    public DFS(Graph graph) {
        super(graph);
        time = 0;
    }

    @Override
    public void apply() {
        assert startVertex != null;
        dfs(startVertex);
    }

    private void dfs(Vertex v) {
        time++;
        v.setStartDate(time);

        discovered[graph.getVertices().indexOf(v)] = true;
        orderOfDiscovery.add(v);

        for (Edge e : v.getEdges()) {
            Vertex u = e.getOtherEnd(v);
            if (!discovered[graph.getVertices().indexOf(u)]) {
                dfs(u);
            }
        }

        time++;
        v.setEndDate(time);
    }
}