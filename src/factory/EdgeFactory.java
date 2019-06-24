package factory;

import graph.Arc;
import graph.Edge;
import graph.Vertex;

public class EdgeFactory {
    public Edge makeEdge(boolean graphIsOriented, Vertex startVertex, Vertex endVertex) {
        if (graphIsOriented) {
            return new Arc(startVertex,
                    endVertex);
        } else {
            return new Edge(startVertex,
                    endVertex);
        }

    }
}
