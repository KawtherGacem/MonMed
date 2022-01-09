package controller.inscriremal;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;


public class Inscriremal implements Initializable {
    @FXML
    private ComboBox<String> ComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComboBox.setItems(FXCollections.observableArrayList("Homme","Femme"));
    }
}
