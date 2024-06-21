package com.example.bankingck.Client.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.bankingck.Model.Request;
import com.example.bankingck.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable, LoginCallBack {

    @FXML
    private TextField SDT_text;
    @FXML
    private TextField pass;
    @FXML
    private ImageView open_eye;
    @FXML
    private Button login_button;
    @FXML
    private Button signUpButton;
    @FXML
    private PasswordField pass_hide;
    @FXML
    private Label information;

    private Stage prevStage;
    private String User_ID;

    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public void ShowPasswordOnAction(KeyEvent keyEvent) {
        CheckFields();
        pass_hide.setText(pass.getText());
    }

    public void HidePasswordOnAction(KeyEvent keyEvent) {
        CheckFields();
        pass.setText(pass_hide.getText());
    }

    public void open_eye_OnAction(MouseEvent mouseEvent) {
        pass_hide.setVisible(true);
        pass.setVisible(false);
        open_eye.setVisible(false);
    }

    public void close_eye_OnAction(MouseEvent mouseEvent) {
        pass_hide.setVisible(false);
        pass.setVisible(true);
        open_eye.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        information.setVisible(false);
        pass.setVisible(false);
        open_eye.setVisible(false);

        SDT_text.textProperty().addListener((observable, oldValue, newValue) -> CheckFields());
        pass.textProperty().addListener((observable, oldValue, newValue) -> CheckFields());
        pass_hide.textProperty().addListener((observable, oldValue, newValue) -> CheckFields());

        signUpButton.setOnAction(e -> changeScene());
        login_button.setOnAction(e -> sendData());
    }

    private void CheckFields() {
        boolean isUsernameEmpty = SDT_text.getText().isEmpty();
        boolean isPasswordEmpty = pass.isVisible() ? pass.getText().isEmpty() : pass_hide.getText().isEmpty();
        login_button.setDisable(isUsernameEmpty || isPasswordEmpty);
    }

    private void sendData() {
        String SDT = SDT_text.getText();
        String password = pass.isVisible() ? pass.getText() : pass_hide.getText();
        String request = Request.LOGIN;

        new ClientCore(SDT, password, request, this);
    }

    private void changeScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SignUp.fxml"));
            Parent root = fxmlLoader.load();

            SignUpController signUpController = fxmlLoader.getController();
            signUpController.setPrevStage(new Stage());

            Stage signUpStage = new Stage();
            signUpStage.setScene(new Scene(root));
            signUpController.setPrevStage(signUpStage);

            signUpStage.show();
            if (prevStage != null) {
                prevStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Sign Up screen");
        }
    }

    private void changeToMainScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
            Parent root = fxmlLoader.load();

            ScreenController screenController = fxmlLoader.getController();
            screenController.setPrevStage(new Stage());
            screenController.setUserID(SDT_text.getText());

            Stage mainStage = new Stage();
            mainStage.setScene(new Scene(root));
            mainStage.show();

            if (prevStage != null) {
                prevStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Main Screen");
        }
    }

    @Override
    public void onLoginSuccess() {
        Platform.runLater(() -> {
            information.setText("");
            setUser_ID(SDT_text.getText());
            changeToMainScene();
        });
    }

    @Override
    public void onLoginFailure(String message) {
        Platform.runLater(() -> {
            information.setVisible(true);
            information.setText(message);
        });
    }

    @Override
    public void OnSignUpSuccess() {
        // Optional: Handle sign up success action if needed
    }

    @Override
    public void OnSignUpFailure(String message) {
        // Optional: Handle sign up failure action if needed
    }

    @Override
    public void logOutSuccess() {
        // Optional: Handle logout success action if needed
    }

    @Override
    public void GetUserNameSuccess(String accountName) {
        // Optional: Handle getting username success action if needed
    }

    @Override
    public void GetUseNameFail() {
        // Optional: Handle getting username failure action if needed
    }
}
