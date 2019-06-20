package controllers;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import info.HelpText;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GraphPaneController {
    private final static float VERTEX_RADIUS = 15.0f;
    @FXML
    private Label infoText;
    @FXML
    private Pane graphPane;
    private MainController mainController;
    private Graph graph;
    private int vertexId;

    @FXML
    private void addVertex(MouseEvent event) {
        if (!event.isPrimaryButtonDown()) {
            return;
        }
        Vertex newVertex = new Vertex(event.getX(), event.getY(), VERTEX_RADIUS, Color.RED);
        newVertex.injectGraphPaneController(this);
        newVertex.setId(String.valueOf(vertexId++));
        newVertex.setText();
        graph.getVertices().add(newVertex);
        graphPane.getChildren().addAll(newVertex, newVertex.getEdge().getShapes(), newVertex.getText());
        newVertex.getEdge().getShapes().toBack();

        graphPane.setOnMouseDragReleased(mouseDragEvent -> {
            Vertex startVertex = (Vertex) mouseDragEvent.getGestureSource();
            startVertex.getEdge().resetEdge();
            startVertex.setFill(Vertex.getDefaultColor());
        });
    }

    public void addEdge(Edge newEdge) {
        graph.getEdges().add(newEdge);
        graphPane.getChildren().add(newEdge.getShapes());
        newEdge.getShapes().toBack();
    }

    public void renameVertex(Vertex vertex) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/RenameVertex.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Rename a Vertex");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(Main.getPrimaryStage());
            stage.setScene(new Scene(root, 200, 100));
            stage.setX(Main.getPrimaryStage().getX() + Main.getPrimaryStage().getWidth() / 2 - 100);
            stage.setY(Main.getPrimaryStage().getY() + Main.getPrimaryStage().getHeight() / 2 - 50);
            stage.show();
            RenameVertexController.setVertexToRename(vertex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeVertex(Vertex vertex) {
        // remove adjacent edges
        graph.getVertices().forEach(
                v -> v.getEdges()
                        .stream()
                        .filter(e -> e.getEnd() == vertex)
                        .collect(Collectors.toList())
                        .forEach(this::removeEdge)
        );
        new ArrayList<>(vertex.getEdges()).forEach(this::removeEdge);

        // remove vertex
        graph.getVertices().remove(vertex);
        graphPane.getChildren().removeAll(vertex.getText(), vertex.getEdge().getShapes(), vertex);

        // update vertices ids
        graph.getVertices()
                .stream()
                .filter(
                        v -> vertex.getId().matches("\\d+") &&
                                v.getId().matches("\\d+") &&
                                Integer.valueOf(v.getId()) > Integer.valueOf(vertex.getId())
                )
                .forEach(v -> v.setId(String.valueOf(Integer.valueOf(v.getId()) - 1)));
        vertexId--;
    }

    public void removeEdge(Edge edge) {
        edge.getStart().getEdges().remove(edge);
        edge.getEnd().getEdges().remove(edge);
        graph.getEdges().remove(edge);
        graphPane.getChildren().remove(edge.getShapes());
    }

    @FXML
    private void initialize() {
        vertexId = 1;
        graphPane.setOnMouseEntered(mouseEvent -> infoText.setText(HelpText.INFO_GRAPH));
        graphPane.setOnMouseExited(mouseEvent -> infoText.setText(HelpText.NULL));
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
        graph = mainController.getGraph();
    }

    public void clearGraph() {
        graphPane.getChildren().clear();
        vertexId = 1;
    }

    public boolean graphIsOriented() {
        return mainController.getInfosBoxController().getOrientedGraphCheckBox().isSelected();
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public Label getInfoText() {
        return infoText;
    }
}
