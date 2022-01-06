package controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class SignUpMalController {

    @FXML
    private TextField ageTextField;

    @FXML
    private TextField confirmerMotDePasseTextField;

    @FXML
    private TextField malNomTextField;

    @FXML
    private TextField motDePasseTextField;

    @FXML
    private TextField nomUtilisateurTextField;

    @FXML
    private Button sinscrireBtn;

    @FXML
    private TextField wilayaTextField;

    @FXML
    private Label alertLabel;

    @FXML
    void sinscrireBtnOnClick(ActionEvent event) throws IOException {
        if (sinscrire(malNomTextField.getText(),nomUtilisateurTextField.getText(),
                wilayaTextField.getText(),ageTextField.getText(),motDePasseTextField.getText(),
                confirmerMotDePasseTextField.getText()).equals("Success")){
            Parent root = FXMLLoader.load(getClass().getResource("../../view/login/Login.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public String sinscrire(String nom, String nomUtilisateur,String wilaya,String age,String motDePasse ,String confirmerMotDePasse){
        String status = "Success";
        try (Connection c = DBConnection.getConnection();) {
            nom.trim();
            nomUtilisateur.trim();

            if (nomUtilisateur.isEmpty() || motDePasse.isEmpty() || nom.isEmpty()) {
                alertLabel.setText("Please complete all the fills");
                alertLabel.setTextFill(RED);
                status = "fail";
            } else {
                if (motDePasse.length() < 6) {
                    alertLabel.setText("Password is too weak, please choose atleast 6 characters");
                    alertLabel.setTextFill(RED);
                    status = "fail";
                } else {
                    if (!motDePasse.equals(confirmerMotDePasse)) {
                        alertLabel.setText("Passwords do not match, please retype.");
                        alertLabel.setTextFill(RED);
                        status = "fail";
                    } else {

                        String sql = "select * from malades where nom_utilisateur_malade = ?";
                        PreparedStatement preparedStatement = c.prepareStatement(sql);
                        preparedStatement.setString(1, nomUtilisateur);
                        ResultSet rs = preparedStatement.executeQuery();
                        if (rs.next()) {
                            alertLabel.setText("Username already taken, please try another username");
                            alertLabel.setTextFill(RED);
                            status = "fail";
                        } else {
                            String sql2 = "insert into malades (nom_malade, nom_utilisateur_malade, password, age, wilaya) values(?,?,?,?,?)";
                            preparedStatement = c.prepareStatement(sql2);

                            preparedStatement.setString(1, nom);
                            preparedStatement.setString(2, nomUtilisateur);
                            preparedStatement.setString(3, motDePasse);
                            preparedStatement.setString(4, age);
                            preparedStatement.setString(5, wilaya);
                            preparedStatement.execute();

                            alertLabel.setText("Account successfully registered");
                            alertLabel.setTextFill(GREEN);
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }
}