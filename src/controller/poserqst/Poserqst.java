package controller.poserqst;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.CurrentUser;
import utils.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class Poserqst {
    @FXML
    private Button publierBtn;

    @FXML
    private TextField questionTextField;

    @FXML
    private TextField titreQuestionTextField;


    public void acceuilOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../view/Acceuilmal.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void profileOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../view/Profilemal.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void contactOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../view/Contact.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void exitOnClick(ActionEvent event) {
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm exit");
        alert.setHeaderText(null);
        alert.setContentText("Do you really want to exit?");
        Optional<ButtonType> action =alert.showAndWait();

        if (action.get()==ButtonType.OK){
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void publierBtnOnClick(ActionEvent event) {
        addQuestion(titreQuestionTextField.getText(),questionTextField.getText(), CurrentUser.currentUserId);
    }
    public void addQuestion(String titre, String text , int idmalade) {
        if (titre.isEmpty() || text.isEmpty()){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("poser une question");
            alert.setHeaderText(null);
            alert.setContentText("remplir toutes les cases.");
            alert.show();
        }else{
        String sqlQuery = "INSERT INTO questions (titre_question, text_question ,IDmalade)" +
                "VALUES (?, ? ,? )";
        try(Connection c = DBConnection.getConnection();
            PreparedStatement pstm = c.prepareStatement(sqlQuery)){
            pstm.setString(1, titre);
            pstm.setString(2, text);
            pstm.setInt(3, idmalade);

            pstm.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        }
    }
}
