package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import static utils.DBConnection.getConnection;

public class Test implements Initializable {

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXMasonryPane masonaryPane;

    @FXML
    private TextArea textArea;

    @FXML
    private JFXListView<HBox> listView = new JFXListView<HBox>();

    public void initialize (URL location, ResourceBundle resources){







//        String sqlQuery = "SELECT * FROM products";

//        try (Connection c = DBUtils.getConnection()){
//            Statement st = c.createStatement();
//            ResultSet rs = st.executeQuery(sqlQuery);
//            Product product;
//
//            while(rs.next()){
//                product = new Product(rs.getInt("product_id"),
//                        rs.getString("product_name"),
//                        rs.getDouble("purchased_price"),
//                        rs.getDouble("sold_price"),
//                        rs.getString("expiration_date"),
//                        rs.getString("category"),
//                        rs.getInt("quantity"),
//                        rs.getInt("quantity") * rs.getDouble("purchased_price"));
//                list.add(product);
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }



    }

    void addBtnOnClick(ActionEvent event) {
        Pane pane = new Pane();
        Label textLabel = new Label();
        textLabel.setText(textArea.getText());
        textLabel.setLayoutX(11);
        pane.getChildren().add(textLabel);
        masonaryPane.getChildren().add(pane);
    }
    Connection connection = getConnection();
}
