package algorithms;

import graph.Graph;
import graph.Vertex;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class Bellman extends ShortestPathAlgorithm {

    public Bellman(Graph graph) {
        super(graph);
    }

    @Override
    public void apply() {
        SearchingAlgorithm dfs = new DFS(graph);
        dfs.setStartVertex(startVertex);
        dfs.apply();

        ObservableList<Vertex> topologicOrder = dfs.getOrderOfDiscovery();
        topologicOrder.sort(Comparator.comparing(Vertex::getEndDate).reversed());

        //TODO
        // assert graph is oriented
        // assert graph is without circuit
        // assert startvertex and endvertex != null
    }
}
