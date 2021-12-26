package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Question;
import utils.DBConnection;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.awt.Color.RED;
import static utils.DBConnection.getConnection;

public class Test implements Initializable {

    @FXML
    private JFXButton addBtn;

    @FXML
    private TextArea textArea;

    @FXML
    private JFXListView<HBox> listView = new JFXListView<HBox>();

    public void initialize (URL location, ResourceBundle resources){


        String sqlQuery = "SELECT * FROM question";

        try (Connection c = DBConnection.getConnection()){
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            Question question;

            while(rs.next()){
                question = new Question(
                        rs.getString("titre"),
                        rs.getInt("up"),
                        rs.getInt("down"));
                createHbox(question);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

// asfiajoifjawrigaorwgfoaeirhga;eori;ago

    }
    public void createHbox(Question question){
        Label titleLabel = new Label(question.getTitre());
            titleLabel.setMaxHeight(50);
            titleLabel.setMaxWidth(260);
            titleLabel.setFont(new Font("System",20));

        Label upLabel = new Label(String.valueOf(question.getUp()));
            upLabel.setPrefSize(50,70);
            upLabel.setAlignment(Pos.CENTER);
            upLabel.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY, Insets.EMPTY)));
            upLabel.setFont(new Font("System",20));

        Label downLabel = new Label(String.valueOf(question.getDown()));
            downLabel.setPrefSize(50,70);
            downLabel.setAlignment(Pos.CENTER);
            downLabel.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY, Insets.EMPTY)));
            downLabel.setFont(new Font("System",20));


        HBox hbox = new HBox();
            hbox.setMaxHeight(50); hbox.setMaxWidth(400);
            hbox.getChildren().addAll(titleLabel,upLabel,downLabel);
            HBox.setHgrow(titleLabel, Priority.ALWAYS);


            listView.getItems().add(hbox);

    }

    @FXML
    void addBtnOnClick(ActionEvent event) {

    }
}
