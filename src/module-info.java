module Graph_interactor {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    opens controllers to javafx.graphics, javafx.fxml, javafx.controls;
    opens main to javafx.graphics, javafx.fxml, javafx.controls;
    opens fxml;
}