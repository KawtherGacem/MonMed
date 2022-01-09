package controller.acceuilmal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Medecin;
import model.Question;
import utils.CurrentUser;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class Acceuilmal implements Initializable {

    @FXML
    private JFXTreeTableView<?> medecinsTableView;

    @FXML
    private Button poserQstBtn;

    @FXML
    private JFXTreeTableView<Question> questionsTableView;

    @FXML
    private TextField rechercheMedecinTextField;

    @FXML
    private TextField rechercheQstTextField;


    public void initialize (URL location, ResourceBundle resources){

        JFXTreeTableColumn<Question,String > titleColumn = new JFXTreeTableColumn("Titre");
        titleColumn.setPrefWidth(400);
        JFXTreeTableColumn<Question,Integer> upColumn = new JFXTreeTableColumn("Upvotes");
        JFXTreeTableColumn<Question,Integer> downColumn = new JFXTreeTableColumn("Downvotes");




        titleColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("titre"));
        upColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("up"));
        downColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("down"));


        questionsTableView.getColumns().addAll(titleColumn,upColumn,downColumn);

        questionsTableView.addEventHandler( MouseEvent.MOUSE_CLICKED, e -> {
            CurrentUser.questionId = questionsTableView.getSelectionModel().getSelectedItem().getValue().getId();
            System.out.println(questionsTableView.getSelectionModel().getSelectedItem().getValue().getId());
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../../view/Qstetrepmal.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        });

        ObservableList<Question> questionsList = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM questions";

        try (Connection c = DBConnection.getConnection()){
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            Question question;

            while(rs.next()){
                question = new Question(
                        rs.getInt("IDquestion"),
                        rs.getString("titre_question"),
                        rs.getInt("up"),
                        rs.getInt("down"));
                questionsList.add(question);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        TreeItem<Question> root = new RecursiveTreeItem<>(questionsList, RecursiveTreeObject::getChildren);
        questionsTableView.setRoot(root);
        questionsTableView.setShowRoot(false);

    }

    @FXML
    void poserQstOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../view/Poserqst.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
