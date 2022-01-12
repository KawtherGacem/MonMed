package controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.DBConnection;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SignUpMalController implements Initializable {

    @FXML
    private Label alertLabel;

    @FXML
    private TextField confirmerMotDePasseTextField;

    @FXML
    private TextField motDePasseTextField;

    @FXML
    private TextField nomTextField;

    @FXML
    private ComboBox<String> sexeComboBox;

    @FXML
    private TextField nomUtilisateurTextField;

    @FXML
    private TextField prenomTextField;

    @FXML
    private Button sinscrireBtn;

    @FXML
    private Button backBtn;

    @FXML
    private DatePicker datePicker = new DatePicker();
    @FXML
    void medecinOnClick(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../view/Inscriremed.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize (URL location, ResourceBundle resources){

        sexeComboBox.getItems().addAll("Femalle","Male");
    }

    public int getAge(){
        LocalDate birthdate =  LocalDate.of(datePicker.getValue().getYear(),datePicker.getValue().getMonthValue(),datePicker.getValue().getDayOfMonth());
        LocalDate now = LocalDate.now();
        int age = Period.between(birthdate, now).getYears();
        return age;
    }


    @FXML
    void sinscrireBtnOnClick(ActionEvent event) throws IOException {
        if (sinscrire(nomTextField.getText(),prenomTextField.getText(),nomUtilisateurTextField.getText(),
                datePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),sexeComboBox.getValue(),motDePasseTextField.getText(),
                confirmerMotDePasseTextField.getText()).equals("Success")){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inscription");
            alert.setHeaderText(null);
            alert.setContentText("Vous avez inscrit, vous pouvez connecter avec votre compte maintenant");
//            Image image = new Image("../../../view/images/information-icone.png") ;
//            ImageView  imageView = new ImageView(image);
//            alert.setGraphic(imageView);
            alert.show();
            Parent root = FXMLLoader.load(getClass().getResource("../../view/Login.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public String sinscrire(String nom,String prenom, String nomUtilisateur,String age,String sexe,String motDePasse ,String confirmerMotDePasse){
        String status = "Success";
        try (Connection c = DBConnection.getConnection();) {
            nom.trim();
            nomUtilisateur.trim();
            if(getAge()<18){
                alertLabel.setText("vous avez moins de 18ans, vous pouvez pas inscrire");
                alertLabel.setStyle("-fx-text-fill : #ff0033 ;");
                status = "fail";

            }
            else {
                if (nom.isEmpty() || prenom.isEmpty() || nomUtilisateur.isEmpty() || age.isEmpty() || sexe.isEmpty() || motDePasse.isEmpty() || confirmerMotDePasse.isEmpty()) {
                    alertLabel.setText("Remplir toutes les cases s'il vous plait ");
                    alertLabel.setStyle("-fx-text-fill : #ff0033 ;");
                    status = "fail";
                } else {
                    if (motDePasse.length() < 6) {
                        alertLabel.setText("Le mot de passe doit contenir au moins 6 caractères");
                        alertLabel.setStyle("-fx-text-fill : #ff0033 ;");
                        status = "fail";
                    } else {
                        if (!motDePasse.equals(confirmerMotDePasse)) {
                            alertLabel.setText("Les deux mots de passe ne sont pas identiques");
                            alertLabel.setStyle("-fx-text-fill : #ff0033 ;");
                            status = "fail";
                        } else {

                            String sql1 = "select * from malades where nom_utilisateur_malade = ?";
                            String sql2 = "select * from medecins where nom_utilisateur_medecin = ?";
                            PreparedStatement preparedStatement1 = c.prepareStatement(sql1);
                            PreparedStatement preparedStatement2 = c.prepareStatement(sql2);

                            preparedStatement1.setString(1, nomUtilisateur);
                            preparedStatement2.setString(1, nomUtilisateur);

                            ResultSet rs1 = preparedStatement1.executeQuery();
                            ResultSet rs2 = preparedStatement2.executeQuery();

                            if (rs1.next() || rs2.next()) {
                                alertLabel.setText("ce nom d'utilisateur est déjà utilisé. essayez un autre nom");
                                alertLabel.setStyle("-fx-text-fill : #ff0033 ;");
                                status = "fail";
                            } else {
                                String sql3 = "insert into malades (nom_malade, prenom_malade, nom_utilisateur_malade, password, age, sexe) values(?,?,?,?,?,?)";
                                PreparedStatement preparedStatement = c.prepareStatement(sql3);

                                preparedStatement.setString(1, nom);
                                preparedStatement.setString(2, prenom);
                                preparedStatement.setString(3, nomUtilisateur);
                                preparedStatement.setString(4, motDePasse);
                                preparedStatement.setString(5, age);
                                preparedStatement.setString(6, sexe);
                                preparedStatement.execute();

                            }
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    @FXML
    void backBtnOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../view/Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
