package interactor;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private Pane graphSupport;

    @FXML
    public void addVertex(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            DrawVertex newVertex = new DrawVertex(event.getX(), event.getY(), 20.0f, Color.RED);
            graphSupport.getChildren().add(newVertex);
            graphSupport.getChildren().add(newVertex.getEdge());

            graphSupport.setOnMouseDragReleased(mouseDragEvent -> {
                DrawVertex startVertex = (DrawVertex) mouseDragEvent.getGestureSource();
                startVertex.getEdge().resetEdge();
            });

            newVertex.setOnMouseDragReleased(mouseDragEvent -> {
                if (mouseDragEvent.getTarget() != mouseDragEvent.getGestureSource()) {
                    DrawVertex startVertex = (DrawVertex) mouseDragEvent.getGestureSource();
                    DrawVertex endVertex = (DrawVertex) mouseDragEvent.getTarget();
                    startVertex.getEdge().setEnd(endVertex);
                    DrawEdge newEdge = new DrawEdge(startVertex.getEdge());
                    if (!startVertex.getEdges().contains(newEdge)) {
                        startVertex.getEdges().add(newEdge);
                        endVertex.getEdges().add(newEdge);
                        graphSupport.getChildren().add(newEdge);
                        newEdge.toBack();
                    }
                    startVertex.getEdge().resetEdge();
                } else {
                    DrawVertex startVertex = (DrawVertex) mouseDragEvent.getGestureSource();
                    startVertex.getEdge().resetEdge();
                }
            });
        }
    }
}
