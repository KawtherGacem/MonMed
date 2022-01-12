package controller.qstetreppourmal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Medecin;
import model.Question;
import model.Reponse;
import utils.CurrentUser;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class Qstetrepmal implements Initializable {

    @FXML
    private Text questionText;

    @FXML
    private Text titreText;

    @FXML
    private JFXListView<HBox> reponsesListView = new JFXListView<HBox>();

    public void initialize (URL location, ResourceBundle resources){

        try (Connection c = DBConnection.getConnection()){
            String query = "SELECT * FROM questions where IDquestion = (?)";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setInt(1, CurrentUser.questionId);
            ResultSet rs = statement.executeQuery();
            Question question;

            while(rs.next()){
                question = new Question(
                        rs.getInt("IDquestion"),
                        rs.getString("titre_question"),
                        rs.getString("text_question"),
                        rs.getInt("up"),
                        rs.getInt("down"));
                titreText.setText(question.getTitre());
                questionText.setText(question.getText());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        reponsesListView.depthProperty().set(1);
        reponsesListView.setStyle("-fx-background-radius: 2em;");
        String sqlQuery = "SELECT * FROM reponses";

        try (Connection c = DBConnection.getConnection()) {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            Reponse reponse;

            while (rs.next()) {
                reponse = new Reponse(
                        rs.getString("text_reponse"),
                        rs.getInt("up"),
                        rs.getInt("down"),
                        rs.getInt("IDmedecin")
                );

                createHbox(reponse,getMedecin(reponse.getIdMedecin()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    public Medecin getMedecin(int idMedecin) throws SQLException {
        Medecin medecin = null;

        Connection c = DBConnection.getConnection();
        String query = "SELECT * FROM medecins where IDmedecin = (?)";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setInt(1, idMedecin);
        ResultSet rs = statement.executeQuery();

        while(rs.next()){
            medecin = new Medecin(
                    rs.getString("nom_utilisateur_medecin"),
                    rs.getString("specialite"));
        }

        return medecin;

    }
    public void createHbox(Reponse reponse , Medecin medecin){

        Text nomUtilisateur = new Text(medecin.getUsername());
        System.out.println(medecin.getUsername());
        nomUtilisateur.setFont(new Font("system",20));
        Text specialite =new Text(medecin.getSpecialite());
        specialite.setFont(new Font("system",15));
        VBox vbox = new VBox();
        vbox.getChildren().addAll(nomUtilisateur,specialite);
        vbox.setStyle("-fx-background-radius : 5em;");



        Text textlabel = new Text(reponse.getText());
        textlabel.setFont(new Font("System",15));

        Label upLabel = new Label(String.valueOf(reponse.getUp()));
        upLabel.setPrefSize(30,30);
        upLabel.setAlignment(Pos.CENTER);
        upLabel.setFont(new Font("System",15));



        Label downLabel = new Label(String.valueOf(reponse.getDown()));
        downLabel.setPrefSize(30,30);
        downLabel.setAlignment(Pos.CENTER);
        downLabel.setFont(new Font("System",15));


        HBox hbox = new HBox();
        hbox.setMaxHeight(50); hbox.setMaxWidth(400);
        hbox.getChildren().addAll(vbox,textlabel,upLabel,downLabel);
        hbox.setStyle("-fx-background-radius : 2em; ");
        HBox.setHgrow(textlabel, Priority.ALWAYS);


        reponsesListView.getItems().add(hbox);

    }
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


}
