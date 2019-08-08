package factory;

import graph.Arc;
import graph.Edge;
import graph.FlowEdge;
import graph.Vertex;

public class EdgeFactory {
    public Edge makeEdge(boolean graphIsOriented, boolean graphIsFlowNetwork, Vertex startVertex, Vertex endVertex) {
        return graphIsOriented ?
                graphIsFlowNetwork ? new FlowEdge(startVertex, endVertex) : new Arc(startVertex, endVertex) :
                new Edge(startVertex, endVertex);
    }
}
