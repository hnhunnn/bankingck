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
import com.example.bankingck.Client.Controller.ScreenController;
import com.example.bankingck.Client.Controller.ClientCore;
import com.example.bankingck.Client.Controller.SignUpController;
import com.example.bankingck.Main;
import com.example.bankingck.Model.Request;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController implements Initializable , LoginCallBack {
    @FXML
    private ImageView Image_Password_closeEye;
    @FXML
    private ImageView Image_Password_openEye;
    @FXML
    private ImageView checkIcon;

    /*Password*/
    @FXML
    private PasswordField Pass_password;

    /*TextField*/
    @FXML
    private TextField Ho_text;
    @FXML
    private TextField Ten_Text;
    @FXML
    private TextField Pass_text_password;
    @FXML
    private TextField Text_email;
    @FXML
    private TextField SDT_user;

    /*Button*/
    @FXML
    private Button back_button;
    @FXML
    private Button login_button;
    @FXML
    private Button signUp_button;

    /*Label*/
    @FXML
    private Label emailCheck;
    @FXML
    private Label NumberPhone_check;
    @FXML
    private Label CheckAccount;
    private String password;
    private Stage preSignInStage ;
    private String User_SDT ;
    private Stage prevStage;

    public void setPreSignInStage(Stage preSignInStage) {
        this.preSignInStage = preSignInStage;
    }
    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }

    public String getUser_SDT() {
        return User_SDT;
    }
    public void setUser_SDT(String user_SDT) {
        User_SDT = user_SDT;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pass_text_password.setVisible(false);
        Image_Password_openEye.setVisible(false);

        SDT_user.textProperty().addListener(((observableValue, oldValue,newValue) -> {
            if(newValue.isEmpty()){
                NumberPhone_check.setVisible(false);
            }else{
                if(!CheckNumberPhone(newValue) && !emailCheck.isVisible() && !checkIcon.isVisible()){
                    NumberPhone_check.setVisible(true);
                    NumberPhone_check.setText("Bạn cần nhập SDT hợp lí");
                }
            }
        }));
        Text_email.textProperty().addListener(((observableValue, oldValue,newValue) ->{
            CheckFieldS();
            if (newValue.isEmpty()) {
                emailCheck.setVisible(false);
                checkIcon.setVisible(false);
            } else {
                if (!CheckEmail(newValue) && !NumberPhone_check.isVisible()) {
                    emailCheck.setVisible(true);
                    emailCheck.setText("Bạn cần nhập một email hợp lệ");
                    checkIcon.setVisible(true);
                } else {
                    emailCheck.setVisible(false);
                    checkIcon.setVisible(false);
                }
            }
        }));
        Pass_password.textProperty().addListener(((observableValue, oldValue,newValue) ->{
            CheckFieldS(); // Kiểm tra có empty hay ko
        }));
        Pass_text_password.textProperty().addListener(((observableValue, oldValue,newValue) -> {
            CheckFieldS() ; // Kiểm tra có empty hay ko
        }));

        login_button.setOnAction(e->backToSignIn());
        signUp_button.setOnAction(e->CreateAccount());
    }
    /*Password*/
    @FXML
    void ShowPasswordOnAction(KeyEvent event) {
        CheckFieldS();
        password = Pass_text_password.getText() ;
        Pass_password.setText(password);
    }
    @FXML
    void HidePasswordOnAction(KeyEvent event) {
        CheckFieldS();
        password = Pass_password.getText();
        Pass_text_password.setText(password);
    }
    @FXML
    void Open_Eye_OnAction(MouseEvent event) {
        /*Lúc mắt đóng*/ /*When eye close*/
        Pass_password.setVisible(true);
        Image_Password_closeEye.setVisible(true);
        Pass_text_password.setVisible(false);
        Image_Password_openEye.setVisible(false);
    }
    @FXML
    void close_eye_OnAction(MouseEvent event) {
        /*Lúc mắt mở*/ /*When eye open*/
        Pass_password.setVisible(false);
        Image_Password_closeEye.setVisible(false);
        Pass_text_password.setVisible(true);
        Image_Password_openEye.setVisible(true);

        Timer timer = new Timer() ;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> Open_Eye_OnAction(null));
            }
        },1000);
    }

    /*Confirm password*/
    private void CheckFieldS() {
        boolean isUsernameEmpty = SDT_user.getText().isEmpty() ;
        boolean isGmailEmpty = Text_email.getText().isEmpty() ;
        boolean isHoEmpty = Ho_text.getText().trim().isEmpty() ;
        boolean isTenEmpty = Ten_Text.getText().trim().isEmpty() ;
        boolean isPasswordEmpty = Pass_text_password.isVisible() ? Pass_text_password.getText().isEmpty() : Pass_password.getText().isEmpty()  ;
        if(!isUsernameEmpty && !isGmailEmpty && !isPasswordEmpty && !isHoEmpty && !isTenEmpty &&
                !emailCheck.isVisible() && !checkIcon.isVisible() && !NumberPhone_check.isVisible()) {
            signUp_button.setDisable(false);
        } else {
            signUp_button.setDisable(true);
        }
    }
    private boolean CheckEmail(String email){
        String regexPattern ="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        /*
        * 1. Cho phép các ký tự số từ 0 đến 9
          2. Cho phép cả chữ hoa và chữ thường từ a đến z
          3. Cho phép các ký tự đặc biệt gạch dưới “_”, gạch nối “-” và dấu chấm “.”
          4. Dấu chấm không được phép ở đầu và cuối phần local part
          5. Các dấu chấm liền nhau sẽ vi phạm
          6. Đối với phần local part, số lượng ký tự đối ta là 64
        * VD:
        username@domain.com
        user.name@domain.com
        user-name@domain.com
        username@domain.co.in
        user_name@domain.com
        * */

        //Tạo đối tượng Pattern
        Pattern pattern  = Pattern.compile(regexPattern);

        //Tạo đối tượng Matcher
        Matcher matcher = pattern.matcher(email) ;

        return matcher.matches();
    }
    public boolean CheckNumberPhone(String phone){
        for (char c : phone.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
    private void CreateAccount() {
        String Ho = Ho_text.getText().trim();
        String Ten = Ten_Text.getText().trim();
        String SDT = SDT_user.getText();
        String email = Text_email.getText();
        String password = Pass_password.isVisible() ? Pass_password.getText() : Pass_text_password.getText();
        new ClientCore(Ho,Ten,SDT,email,password,Request.SIGNUP,this) ;

    }
    private void backToSignIn() {
        try{
            Runnable openSignIn = new Runnable() {
                @Override
                public void run() {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("User.fxml"));
                        Parent root = fxmlLoader.load();

                        SignInController loginController = fxmlLoader.getController() ;
                        loginController.setPrevStage(preSignInStage);

                        preSignInStage.getScene().setRoot(root);
                        System.out.println("Chuyen ve login ");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            openSignIn.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void changeToMainScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
            Parent root = fxmlLoader.load();
            ScreenController screenController = fxmlLoader.getController();
            screenController.setPrevStage(preSignInStage);
            screenController.setUserID(getUser_SDT());

            Scene scene2 = new Scene(root, 338, 564);
            preSignInStage.setScene(scene2);
            preSignInStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load MainScreen.fxml or change scene.");
        }
    }

    @Override
    public void onLoginSuccess() {
    }

    @Override
    public void onLoginFailure(String message) {
    }

    // Sign up
    @Override
    public void OnSignUpSuccess() {
        setUser_SDT(SDT_user.getText());
        changeToMainScene();
    }

    @Override
    public void OnSignUpFailure(String message) {
        CheckAccount.setVisible(true);
        CheckAccount.setText(message);
    }

    @Override
    public void logOutSuccess() {

    }

    @Override
    public void GetUserNameSuccess(String accountName) {

    }

    @Override
    public void GetUseNameFail() {

    }

    public void Login(ActionEvent actionEvent) {

    }
}
