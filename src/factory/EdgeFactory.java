package factory;

import graph.*;
import javafx.beans.property.SimpleBooleanProperty;

public class EdgeFactory {
    private final SimpleBooleanProperty graphIsFlowNetWork;
    private final SimpleBooleanProperty graphIsOriented;

    public EdgeFactory(Graph graph) {
        this.graphIsOriented = graph.orientedProperty();
        this.graphIsFlowNetWork = graph.flowNetworkProperty();
    }

    public Edge makeEdge(Vertex startVertex, Vertex endVertex) {
        return graphIsOriented.get() ?
                graphIsFlowNetWork.get() ? new FlowEdge(startVertex, endVertex) : new Arc(startVertex, endVertex) :
                new Edge(startVertex, endVertex);
    }
}
