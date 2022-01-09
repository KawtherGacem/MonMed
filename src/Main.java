import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        primaryStage.setTitle("Test");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("view/Css/Css.css")).toExternalForm());
    }



 
    public static void main(String[] args) {
        launch(args);
    }
}
