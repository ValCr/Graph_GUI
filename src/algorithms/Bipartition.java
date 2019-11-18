package algorithms;

import graph.Constants;
import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.HashMap;
import java.util.Map;

public class Bipartition extends ColorationAlgorithm {
    private Map<Vertex, Boolean> visited;

    public Bipartition(Graph graph) {
        super(graph);
        visited = new HashMap<>(graph.getOrder());
        graph.getVertices()
                .forEach(v -> visited.put(v, false));
    }

    @Override
    public void apply() {
        isBipartite = true;
        while (visited.values()
                .stream()
                .anyMatch(v -> !v)) {
            Vertex v = visited.entrySet()
                    .stream()
                    .filter(entry -> !entry.getValue())
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElseThrow();
            isBipartite = isBipartite(v) && isBipartite; //function must be called before field
        }
    }

    private boolean isBipartite(Vertex u) {
        Vertex v;
        visited.put(u, true);
        for (Edge e : u.getEdges()) {
            v = e.getOtherEnd(u);
            if (!visited.get(v)) {
                visited.put(v, true);
                colors.put(v, colors.get(u) == Constants.VERTEX_COLOR_WHEN_VISITED ? Constants.VERTEX_DEFAULT_COLOR :
                        Constants.VERTEX_COLOR_WHEN_VISITED);
                if (!isBipartite(v)) {
                    return false;
                }
            } else if (colors.get(v)
                    .equals(colors.get(u))) {
                return false;
            }
        }
        return true;
    }
}