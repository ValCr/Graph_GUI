package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import main.Main;

import java.io.File;

public class MenuBarController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private CheckMenuItem help;
    private MainController mainController;

    @FXML
    void showHelp() {
        mainController.getGraphPaneController().getHelpInfo().setVisible(help.isSelected());
    }

    @FXML
    private void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(getFilter());
        fileChooser.setTitle("Open a graph");
        File file = fileChooser.showOpenDialog(Main.getPrimaryStage());

        if (file != null) {
            if (file.getName().endsWith(".graph")) {
                mainController.open(file);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Unable to open file : wrong extension for " + file.getName());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(getFilter());
        fileChooser.setTitle("Save the graph");
        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());

        if (file != null) {
            if (file.getName().endsWith(".graph")) {
                mainController.save(file);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Unable to save file : wrong extension for " + file.getName());
                alert.showAndWait();
            }
        }
    }

    private FileChooser.ExtensionFilter getFilter() {
        return new FileChooser.ExtensionFilter("graph files (*.graph)", "*.graph");
    }

    @FXML
    private void close() {
        System.exit(0);
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public CheckMenuItem getHelp() {
        return help;
    }
}
