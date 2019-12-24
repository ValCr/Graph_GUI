package controllers;

import factory.EdgeFactory;
import factory.InfoTextFactory;
import graph.Constants;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphPaneController {
    private final static float VERTEX_RADIUS = 15.0f;
    @FXML
    private Slider animationSpeed;
    @FXML
    private Label infoAlgo;
    @FXML
    private Label helpInfo;
    @FXML
    private Pane graphPane;
    private MainController mainController;
    private Graph graph;
    private int vertexId;

    @FXML
    public void addVertex(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            addVertex(String.valueOf(vertexId), event.getX(), event.getY());
        }
    }

    public void addVertex(String id, Double x, Double y) {
        Vertex newVertex = new Vertex(x, y, VERTEX_RADIUS, Color.RED);
        newVertex.injectGraphPaneController(this);
        newVertex.setId(id);
        vertexId++;
        newVertex.setTextID();
        graph.getVertices().add(newVertex);
        graphPane.getChildren().addAll(newVertex, newVertex.getEdge().getShapes(), newVertex.getTextID());
        newVertex.getEdge().getShapes().toBack();
    }

    public void addEdge(Edge newEdge) {
        newEdge.getStart().getEdges().add(newEdge);
        if (!graph.isOriented()) {
            newEdge.getEnd().getEdges().add(newEdge);
        }
        newEdge.injectGraphPaneController(this);

        graph.getEdges().add(newEdge);
        graphPane.getChildren().add(newEdge.getShapes());
        newEdge.getShapes().toBack();
    }

    public void addEdge(String startID, String endID, String cost) {
        EdgeFactory factory = new EdgeFactory(graph);
        Edge newEdge = factory.makeEdge(graph.getVertexFromID(startID), graph.getVertexFromID(endID));
        newEdge.setCost(Double.valueOf(cost));
        addEdge(newEdge);
    }

    public void changeCost(Edge edge) {
        try {
            ChangeCostController.setEdgeToChangeCost(edge); // must be before call to createStage
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/ChangeCost.fxml"));
            createStage(root, "Change the cost");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renameVertex(Vertex vertex) {
        try {
            RenameVertexController.setMainController(mainController); // must be before call to createStage
            RenameVertexController.setVertexToRename(vertex); // must be before call to createStage
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/RenameVertex.fxml"));
            createStage(root, "Rename the vertex");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createStage(Parent root, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getPrimaryStage());
        stage.setScene(new Scene(root));
        stage.setX(Main.getPrimaryStage().getX() + Main.getPrimaryStage().getWidth() / 2 - 100);
        stage.setY(Main.getPrimaryStage().getY() + Main.getPrimaryStage().getHeight() / 2 - 50);
        stage.show();
    }

    public void removeVertex(Vertex vertex) {
        // remove adjacent edges
        graph.getVertices().forEach(
                v -> v.getEdges().stream().filter(e -> e.getEnd() == vertex).collect(Collectors.toList())
                        .forEach(this::removeEdge));
        new ArrayList<>(vertex.getEdges()).forEach(this::removeEdge);

        // remove vertex
        graph.getVertices().remove(vertex);
        graphPane.getChildren().removeAll(vertex.getTextID(), vertex.getEdge().getShapes(), vertex);

        // update vertices ids
        graph.getVertices().stream().filter(v -> vertex.getId().matches("\\d+") && v.getId().matches("\\d+") &&
                Integer.valueOf(v.getId()) > Integer.valueOf(vertex.getId()))
                .forEach(v -> v.setId(String.valueOf(Integer.valueOf(v.getId()) - 1)));
        vertexId--;
    }

    public void removeEdge(Edge edge) {
        edge.getStart().getEdges().remove(edge);
        edge.getEnd().getEdges().remove(edge);
        graph.getEdges().remove(edge);
        graphPane.getChildren().remove(edge.getShapes());
    }

    public void removeAllEdges() {
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        edges.forEach(this::removeEdge);
    }

    @FXML
    private void initialize() {
        vertexId = 1;
        graphPane.setOnMouseEntered(mouseEvent -> helpInfo.setText(InfoTextFactory.INFO_GRAPH));
        graphPane.setOnMouseExited(mouseEvent -> helpInfo.setText(InfoTextFactory.NULL));
        graphPane.setOnMouseDragReleased(mouseDragEvent -> {
            Vertex startVertex = (Vertex) mouseDragEvent.getGestureSource();
            startVertex.getEdge().resetEdge();
            startVertex.setFill(Constants.VERTEX_DEFAULT_COLOR);
        });
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
        graph = mainController.getGraph();
    }

    public void clearGraph() {
        graphPane.getChildren().clear();
        graphPane.getChildren().add(animationSpeed);
        vertexId = 1;
        infoAlgo.setText("");
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public Label getHelpInfo() {
        return helpInfo;
    }

    public Label getInfoAlgo() {
        return infoAlgo;
    }

    public Pane getGraphPane() {
        return graphPane;
    }

    public Slider getAnimationSpeed() {
        return animationSpeed;
    }

    public Graph getGraph() {
        return graph;
    }

    public MainController getMainController() {
        return mainController;
    }
}
