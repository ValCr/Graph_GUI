package graph;

import factory.EdgePropertiesFactory;
import javafx.beans.property.SimpleIntegerProperty;

public class FlowEdge extends Arc {
    public static final Integer DEFAULT_CAPACITY = 1;
    private final SimpleIntegerProperty capacity;

    public FlowEdge(Vertex start, Vertex end) {
        super(start, end);
        EdgePropertiesFactory factory = new EdgePropertiesFactory();
        this.capacity = factory.makeCapacity(
                start.getGraphPaneController() //graphPaneController isn't initialized, we must get one from the starting vertex
                        .getMainController()
                        .getInfoBoxController()
                        .getFlowNetwork(), this, shapes);
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public int getCapacity() {
        return capacity.get();
    }

    ///////////////////////////////////////////// Setters /////////////////////////////////////////////
    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public SimpleIntegerProperty capacityProperty() {
        return capacity;
    }
}
