package controllers;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private SplitPane mainSplitPane;
    @FXML
    private GraphPaneController graphPaneController;
    @FXML
    private InfoBoxController infoBoxController;
    @FXML
    private MenuBarController menuBarController;

    private Graph graph;

    public Graph getGraph() {
        return graph;
    }

    @FXML
    private void initialize() {
        graph = new Graph();
        infoBoxController.injectMainController(this);
        graphPaneController.injectMainController(this);
        menuBarController.injectMainController(this);
        infoBoxController.bindInfoToGraph();
    }

    public void clearGraph() {
        graph.getVertices()
                .clear();
        graph.getEdges()
                .clear();
        graphPaneController.clearGraph();
        graph.setContainsCircuit(false);
    }

    public void save(File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println("# Graph properties (Oriented;CostVisible)");
            writer.println(graph.isOriented() + ";" + infoBoxController.getCostAreVisible().isSelected());
            writer.println("# Vertices (Id;posX;posY)");
            graph.getVertices().forEach(v -> writer.println(v.getStringData()));
            writer.println("# Edges (startID;endID;cost)");
            graph.getEdges().forEach(e -> writer.println(e.getStringData()));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void open(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String[]> vertices = new ArrayList<>();
            List<String[]> edges = new ArrayList<>();
            String[] properties;
            parseComment(reader);
            properties = reader.readLine().split(";");
            parseComment(reader);
            parseAndAddToList(reader, vertices);
            parseComment(reader);
            parseAndAddToList(reader, edges);
            reader.close();
            openGraph(properties, vertices, edges);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openGraph(String[] properties, List<String[]> vertices, List<String[]> edges) {
        clearGraph();
        infoBoxController.getOrientedGraph().setSelected(Boolean.parseBoolean(properties[0]));
        infoBoxController.getCostAreVisible().setSelected(Boolean.parseBoolean(properties[1]));
        vertices.forEach(v -> graphPaneController.addVertex(v[0], Double.valueOf(v[1]), Double.valueOf(v[2])));
        edges.forEach(e -> graphPaneController.addEdge(e[0], e[1], e[2]));

    }

    private void parseComment(BufferedReader reader) throws IOException {
        int BUFFER_SIZE = 1000;
        String line;
        do {
            reader.mark(BUFFER_SIZE);
            line = reader.readLine();
        } while (line != null && line.startsWith("#"));
        reader.reset(); // come back to the line before the end of comments
    }

    private void parseAndAddToList(BufferedReader reader, List<String[]> list) throws IOException {
        String line;
        while ((line = reader.readLine()) != null && !line.startsWith("#")) {
            list.add(line.split(";"));
        }
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public GraphPaneController getGraphPaneController() {
        return graphPaneController;
    }

    public InfoBoxController getInfoBoxController() {
        return infoBoxController;
    }

    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    ///////////////////////////////////////////// Setters /////////////////////////////////////////////
    public void setAllVertexEventsToNull() {
        graph.getVertices().forEach(Vertex::setAllMouseEventsToNull);
    }

    public void setAllEdgesEventsToNull() {
        graph.getEdges().forEach(Edge::setAllMouseEventsToNull);
    }

    public void setAllVertexEventsToDefault() {
        graph.getVertices().forEach(Vertex::setAllMouseEventsToDefault);
    }

    public void setAllEdgesEventsToDefault() {
        graph.getEdges().forEach(Edge::setAllMouseEventsToDefault);
    }

}
