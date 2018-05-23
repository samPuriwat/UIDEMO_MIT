package sample;

import adminDashBoard.dashBoardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private final String id = "admin";
    private final String pass = "1234";

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button btnLogin;
    @FXML
    private Label dbStatus;

    private loginModel loginModel = new loginModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.loginModel.isDatabaseConnection()) {
            this.dbStatus.setText("Connected to DB.");
        } else {
            this.dbStatus.setText("Not Connect to DB.");
        }



    }

    @FXML
    public void Login(ActionEvent event){
//        try {
//            if (id.equals(username.getText()) && pass.equals(password.getText())) {
//                JOptionPane.showMessageDialog(null,
//                        "Welcome to MIT SAIYAI APP");
//            } else {
//                JOptionPane.showMessageDialog(null,
//                        "Your username or password is invalid.",
//                        "Warning Message",
//                        JOptionPane.WARNING_MESSAGE);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            if (this.loginModel.isLogin(this.username.getText(), this.password.getText())) {
//                JOptionPane.showMessageDialog(null,
//                        "Welcome to MIT SAIYAI APP");
                Stage stage = (Stage) this.btnLogin.getScene().getWindow();
                stage.close();
                dashBoard();

            } else {
                JOptionPane.showMessageDialog(null,
                        "Your username or password is invalid.",
                        "Warning Message",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }//Login

    private void dashBoard() {
        try {
            Stage dashBoardStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass()
                    .getResource("/adminDashBoard/dashBoard.fxml").openStream());
            dashBoardController db = loader.getController();

            Scene scene = new Scene(root);
            dashBoardStage.setScene(scene);
            dashBoardStage.setTitle("MIT DashBoard");
            dashBoardStage.setResizable(false);
            dashBoardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }//dashBoard

}//class
