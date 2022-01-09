package controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import model.Malade;
import utils.CurrentUser;
import utils.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class LoginController {

    @FXML
    private Button connecterBtn;

    @FXML
    private PasswordField motDePasse;

    @FXML
    private TextField nomUtilisateur;

    @FXML
    private Button sinscrireBtn;

    @FXML
    private Ellipse shape1;

    @FXML
    private Polyline shape2;

    @FXML
    private Label alertLabel;

    @FXML
    void connecterBtnOnClick(ActionEvent event) throws IOException {
        if(logIn(nomUtilisateur.getText().trim(),motDePasse.getText()).equals("success")){
            if (CurrentUser.currentUserType.equals("malade")) {
                Parent root = FXMLLoader.load(getClass().getResource("../../view/Acceuilmal.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setY(0);
                stage.setX(0);

                stage.show();
            }else if(CurrentUser.currentUserType.equals("medecin")) {
                Parent root = FXMLLoader.load(getClass().getResource("../../view/Acceuilmed.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setY(0);
                stage.setX(0);
                stage.show();
            }
            System.out.println(CurrentUser.currentUserType);
        }

    }

    @FXML
    void sinscrireBtnOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../view/inscriremal.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private String logIn(String nomUtilisateur, String motDePasse) {

        String status = "success";
        if(nomUtilisateur.isEmpty() || motDePasse.isEmpty()) {
            shape1.setVisible(true);
            shape2.setVisible(true);
            alertLabel.setText("remplire toutes les cases");
            alertLabel.setStyle("-fx-text-fill : #ff0033 ;");
            status = "fail";
        } else {
            String sql = "SELECT * FROM malades Where nom_utilisateur_malade = ? and password = ?";
            try (Connection c = DBConnection.getConnection();){
                PreparedStatement preparedStatement = c.prepareStatement(sql);
                preparedStatement.setString(1, nomUtilisateur);
                preparedStatement.setString(2, motDePasse);
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()){
                    status="success";
                    CurrentUser.currentUserId=rs.getInt("IDmalade");
                    CurrentUser.currentUserType="malade";
                } else {
                    sql ="SELECT * FROM medecins Where nom_utilisateur_medecin = ? and password = ?";
                    preparedStatement = c.prepareStatement(sql);
                    preparedStatement.setString(1, nomUtilisateur);
                    preparedStatement.setString(2, motDePasse);
                    rs = preparedStatement.executeQuery();
                    if (rs.next()) {
                        status="success";
                        CurrentUser.currentUserId=rs.getInt("IDmedecin");
                        CurrentUser.currentUserType="medecin";

                    } else {
                        shape1.setVisible(true);
                        shape2.setVisible(true);
                        alertLabel.setText("Nom d'utilisateur ou Mot de passe " +'\n'+
                                " incorrecte");
                        alertLabel.setStyle("-fx-text-fill : #ff0033 ;");
                        status = "Error";
                    }
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }


}
