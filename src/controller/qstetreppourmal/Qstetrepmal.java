package controller.qstetreppourmal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import model.Question;
import utils.CurrentUser;
import utils.DBConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Qstetrepmal implements Initializable {

    @FXML
    private Text questionText;

    @FXML
    private Text titreText;

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


    }
}
