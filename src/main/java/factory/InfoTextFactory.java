package factory;

import graph.Graph;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;

public class InfoTextFactory {
    public static final String INFO_GRAPH = "Left Click to place a vertex";
    public static final String NULL = "";
    private static final String INFO_ARC = "Right Click to remove this arc";
    private static final String INFO_EDGE = "Right Click to remove this edge";
    private static final String INFO_CHANGE_COST = "SHIFT + Left Click to change cost";
    private static final String INFO_VERTEX_EDGE =
            "Left Click and Drag to create an edge\n" + "Right Click to delete\n" + "CTRL + Left Click to move\n" +
                    "SHIFT + Left Click to rename";
    private static final String INFO_VERTEX_ARC =
            "Left Click and Drag to create an arc\n" + "Right Click to delete\n" + "CTRL + Left Click to move\n" +
                    "SHIFT + Left Click to rename";
    private final SimpleBooleanProperty graphIsOriented;

    public InfoTextFactory(Graph graph) {
        this.graphIsOriented = graph.orientedProperty();
    }

    public void setInfoText(Label helpInfo, boolean costAreVisible) {
        helpInfo.setText(graphIsOriented.get() ? InfoTextFactory.INFO_ARC : InfoTextFactory.INFO_EDGE);
        helpInfo.setText(
                costAreVisible ? helpInfo.getText() + "\n" + InfoTextFactory.INFO_CHANGE_COST : helpInfo.getText());
    }

    public void setInfoText(Label helpInfo) {
        helpInfo.setText(graphIsOriented.get() ? InfoTextFactory.INFO_VERTEX_ARC : InfoTextFactory.INFO_VERTEX_EDGE);
    }
}
