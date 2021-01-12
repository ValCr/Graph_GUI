package graph;

import javafx.scene.paint.Color;

public final class Constants {
    // vertices
    public final static Color VERTEX_DEFAULT_COLOR = Color.web("#FF2C16");
    public final static Color VERTEX_COLOR_WHEN_SELECTED = Color.web("#0E0FA8");
    public final static Color VERTEX_SECOND_COLOR = Color.web("#EB8243");
    public static final Color VERTEX_COLOR_WHEN_VISITED = Color.web("#00CC14");

    // edges
    public final static Color EDGE_DEFAULT_COLOR = Color.web("#1D2129");
    public static final Double EDGE_DEFAULT_COST = 1.0;
    public final static Color EDGE_SECOND_COLOR = Color.web("#3F5E7F");
    public final static float EDGE_DEFAULT_STROKE_WIDTH = 3.0f;

    // arc
    public final static float ARC_ARROW_LENGTH = 15.0f;
    public final static float ARC_ARROW_HALF_WIDTH = 10.0f;

    // flow edges
    public static final Integer FLOW_EDGE_DEFAULT_CAPACITY = 1;

    private Constants(){}

}