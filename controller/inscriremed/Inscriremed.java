package controller.inscriremed;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;


public class Inscriremed implements Initializable {
    @FXML
    private ComboBox<String> combobox1;
    @FXML
    private ComboBox<String> combobox2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox1.setItems(FXCollections.observableArrayList("Dérmatologue","Pédiatre",
                "Oarele","Géniecologue"));
        combobox2.setItems(FXCollections.observableArrayList("homme","Femme"));
    }
}