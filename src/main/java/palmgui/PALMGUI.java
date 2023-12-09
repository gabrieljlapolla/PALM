package palmgui;

import palm.Item;
import palm.PALM;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class PALMGUI extends Application {
    @FXML
    public Button quitButton;
    @FXML
    public Button exportButton;
    @FXML
    public Button createButton;
    @FXML
    public Button searchButton;
    @FXML
    public Button viewAllButton;
    @FXML
    public ListView<String> listViewItems;
    @FXML
    public TextArea itemDetailsTextArea;

    // https://coolors.co/151e3f-030027-c97d60-247357-faf3dd

    public static void main(String[] args) {
        launch();
    }

    public void viewAll(ActionEvent e) {
        System.out.println("View all button clicked");
    }

    public void search(ActionEvent e) {
        System.out.println("Search button clicked");
    }

    public void create(ActionEvent e) {
        System.out.println("Create button clicked");
        (new CreateController()).start(new Stage());
    }

    public void export(ActionEvent e) {
        System.out.println("Export button clicked");
    }

    public void exit(ActionEvent e) {
        System.out.println("Exit button clicked");
        Platform.exit();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Login page
            (new LoginController()).start(new Stage());
            // After successful login, PALM now contains user items
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_application.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            primaryStage.setTitle("PALM - Password and Login Manager");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Unable to start application");
            Platform.exit();
        }

        // Add listener to listView to show item details for currently selected item
        listViewItems.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal)
                -> itemDetailsTextArea.setText(PALM.getItems().get(newVal).toString()));

        ObservableList<String> ol = FXCollections.observableArrayList();
        listViewItems.setItems(ol);
        for (Item i : PALM.getItems()) {
            ol.add(i.getName());
        }

        if (ol.isEmpty()) { // Check if no items

        }
    }
}
