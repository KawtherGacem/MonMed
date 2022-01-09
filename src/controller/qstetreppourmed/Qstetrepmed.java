package controller.qstetreppourmed;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Medecin;
import model.Question;
import model.Reponse;
import utils.CurrentUser;
import utils.DBConnection;

import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Qstetrepmed implements Initializable {

    @FXML
    private Button repondreBtn;

    @FXML
    private TextField reponseTextField;

    @FXML
    private JFXListView<HBox> reponsesListView = new JFXListView<HBox>();

    public void initialize (URL location, ResourceBundle resources) {
        reponsesListView.setExpanded(true);
        reponsesListView.depthProperty().set(1);
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
        VBox vbox = new VBox();
        vbox.getChildren().addAll(nomUtilisateur,specialite);



        Text textlabel = new Text(reponse.getText());
        textlabel.setFont(new Font("System",20));

        Label upLabel = new Label(String.valueOf(reponse.getUp()));
        upLabel.setPrefSize(50,70);
        upLabel.setAlignment(Pos.CENTER);
        upLabel.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        upLabel.setFont(new Font("System",20));

        JFXButton upBtn = new JFXButton("up");
        JFXButton downBtn = new JFXButton("down");

        upBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
//                khasni min ndrouk tjib id ta3 reponse bach tdir upvote liha
                Connection c = DBConnection.getConnection();
                String query = "UPDATE reponses SET up = (?) WHERE IDreponse = (?)";
                PreparedStatement statement = null;
                try {
                    statement = c.prepareStatement(query);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    statement.setInt(1, +1);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    ResultSet rs = statement.executeQuery();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        upBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

            }
        });

        Label downLabel = new Label(String.valueOf(reponse.getDown()));
        downLabel.setPrefSize(50,70);
        downLabel.setAlignment(Pos.CENTER);
        downLabel.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE,CornerRadii.EMPTY, Insets.EMPTY)));
        downLabel.setFont(new Font("System",20));


        HBox hbox = new HBox();
        hbox.setMaxHeight(50); hbox.setMaxWidth(400);
        hbox.getChildren().addAll(vbox,textlabel,upLabel,downLabel,upBtn,downBtn);
        HBox.setHgrow(textlabel, Priority.ALWAYS);


        reponsesListView.getItems().add(hbox);


    }


    @FXML
    void repondreBtnOnClick(ActionEvent event) {
        addReponse(reponseTextField.getText(),CurrentUser.currentUserId, CurrentUser.questionId);

    }
    public void addReponse(String text , int idMedecin ,int idQuestion) {
        if (text.isEmpty()){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ajouter une reponse");
            alert.setHeaderText(null);
            alert.setContentText("remolir la case s'il vous plait");
            alert.show();
        }else{
            String sqlQuery = "INSERT INTO reponses (text_reponse ,IDmedecin , IDquestion)" +
                    "VALUES (?, ? ,? )";
            try(Connection c = DBConnection.getConnection();
                PreparedStatement pstm = c.prepareStatement(sqlQuery)){
                pstm.setString(1, text);
                pstm.setInt(2, idMedecin);
                pstm.setInt(3, idQuestion);

                pstm.execute();
            }
            catch (SQLException e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

}
