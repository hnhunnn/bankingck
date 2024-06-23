package com.example.bankingck.Client.Controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.example.bankingck.Client.Controller.ClientCore;
import com.example.bankingck.Client.Controller.LoginCallBack;
import com.example.bankingck.Client.Controller.Screen_Interface;
import com.example.bankingck.Client.Controller.SignInController;
import com.example.bankingck.Main;
import com.example.bankingck.Model.DAO.UserDAO;
import com.example.bankingck.Model.Request;
import com.example.bankingck.Model.User;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScreenController implements Initializable,LoginCallBack, Screen_Interface {

    @FXML private AnchorPane main_menu;
    @FXML private AnchorPane expandedView;
    @FXML private AnchorPane initialView;
    @FXML private AnchorPane upper_infor;
    @FXML private AnchorPane user_pane;
    @FXML private AnchorPane change_Pass_Pane;
    @FXML private AnchorPane check_pane;
    @FXML private AnchorPane home_screen;
    @FXML private AnchorPane chuyen_tien_sreen1;
    @FXML private AnchorPane chuyen_tien_sreen2;
    @FXML private AnchorPane pin_Otp_1;

    @FXML private Label Money1;
    @FXML private Label Name_user;
    @FXML private Label SDT1;
    @FXML private Label TrueOrNot;
    @FXML private Label user_SDT_1;
    @FXML private Label user_name_1;
    @FXML private Label Money2;
    @FXML private Label user_SDT_2;
    @FXML private Label user_name_2;
    @FXML private Label pass_check;
    @FXML private Label soTienChuyen;

    @FXML private Button user_Home;
    @FXML private Button dangXuat;
    @FXML private Button Search;
    @FXML private Button User_Infor;
    @FXML private Button notification;
    @FXML private Button toggleButton;
    @FXML private Button ChuyenTien_button;
    @FXML private Button toggleButtonExpanded;
    @FXML private Button thaydoimatkhau;
    @FXML private Button Change_pass_send;
    @FXML private Button Move_back_1;
    @FXML private Button Move_back_2;
    @FXML private Button Move_back_3;
    @FXML private Button Move_back_4 ;
    @FXML private Button Home_button_1;
    @FXML private Button Home_button_2;
    @FXML private Button Change_pass_continue;
    @FXML private Button CT_chuyentien;

    @FXML private ImageView close_eye_1;
    @FXML private ImageView close_eye_2;
    @FXML private ImageView close_eye_3;

    @FXML private ImageView open_eye_1;
    @FXML private ImageView open_eye_2;
    @FXML private ImageView open_eye_3;

    @FXML private ImageView not_1;
    @FXML private ImageView not_2;
    @FXML private ImageView not_3;

    @FXML private ImageView yes_1;
    @FXML private ImageView yes_2;
    @FXML private ImageView yes_3;

    @FXML private TextField pass_text_1;
    @FXML private TextField pass_text_2;
    @FXML private TextField pass_text_3;
    @FXML private PasswordField MaPin;


    @FXML private PasswordField password_1;
    @FXML private PasswordField password_2;
    @FXML private PasswordField password_3;

    // Chuyển tiền
    @FXML private Pane Noi_Dung_Pane;
    @FXML private TextField So_Tai_Khoan;
    @FXML private TextField Ten_Tai_Khoan;
    @FXML private TextField So_Tien;
    @FXML private TextField loi_nhan;
    @FXML private ImageView delete_all_balance;
    @FXML private ImageView show_notify;
    @FXML private Button confirm_changeMoney;
    // Xác nhận chuyển khoản
    @FXML private Label xntt_SDT_TaiKhoanDi;
    @FXML private Label xntt_SDT_TaiKhoanToi;
    @FXML private Label xntt_TaiKhoanDi;
    @FXML private Label xntt_TaiKhoanToi;
    @FXML private Label SoTienBangChu_1;
    @FXML private Label NoiDungChuyenKhoan;

    private boolean isExpanded = false;
    private boolean isChange1 = false;
    private boolean isChange2 = false;
    private boolean isChange3 = false;
    private boolean isChange4 = false;
    private boolean isChange5 = false;

    private Stage prevStage;
    private String Password1;
    private String Password2;
    private String Password3;
    private LoginCallBack loginCallBack;
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
        SDT1.setText(userID);
        user_SDT_1.setText(userID);
        user_SDT_2.setText(userID);
        DisplayUserName();
    }
    private void DisplayUserName() {
        String fullName = UserDAO.ChangeName(userID);
        Name_user.setText(fullName);
        BigDecimal sdt = UserDAO.UpdateBalance(userID);
        Money1.setText(ConvertBalance(sdt));
        Money2.setText(ConvertBalance(sdt));

        user_name_1.setText(fullName);
        user_name_2.setText(fullName);
        loi_nhan.setText(fullName + " chuyen khoan");
    }
    public Stage getPrevStage() {
        return prevStage;
    }
    public void setPrevStage(Stage prevStage) {
        this.prevStage = prevStage;
    }
    private void LogOut() {
        String userID = getUserID();
        String request = Request.SignOff;
        new Thread(() -> new ClientCore(userID, request, (LoginCallBack) this)).start();
    }
    private void handleToggle() {
        Platform.runLater(() -> {
            if (isExpanded) {
                // Slide up to collapse
                slidePane(expandedView, -358);
                slidePane(initialView, 0);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        toggleButtonExpanded.setVisible(false);
                        toggleButton.setVisible(true);
                    }
                }, 100);
            } else {
                // Slide down to expand
                slidePane(expandedView, 0);
                slidePane(initialView, 300);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        toggleButtonExpanded.setVisible(true);
                        toggleButton.setVisible(false);
                    }
                }, 200);
            }
            isExpanded = !isExpanded;
        });
    }

    private void slidePane(AnchorPane pane, double toY) {
        Platform.runLater(() -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(200), pane);
            transition.setToY(toY);
            transition.play();
        });
    }

    private void slidePaneToX(AnchorPane pane, double toX) {
        Platform.runLater(() -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(200), pane);
            transition.setToX(toX);
            transition.play();
        });
    }
    private void newPane() {
        Platform.runLater(() ->{
            Timer timer = new Timer() ;
            change_Pass_Pane.setVisible(true);
            if(toggleButtonExpanded.isVisible()){
                handleToggle();
                toggleButton.setVisible(false);
                if(isChange1){
                    toggleButton.setVisible(false);
                    slidePane(home_screen,-564);
                    slidePane(change_Pass_Pane,0);
                    slidePane(user_pane,0);
                }else{
                    slidePane(home_screen,0);
                    slidePane(user_pane,+564);
                    slidePane(change_Pass_Pane,+ 564);
                    change_Pass_Pane.setVisible(false);
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            toggleButton.setVisible(true);
                            toggleButtonExpanded.setVisible(false);
                        }
                    },200);
                }
            }else{
                toggleButton.setVisible(false);
                if(isChange1){
                    slidePane(home_screen,-564);
                    slidePane(change_Pass_Pane,0);
                    slidePane(user_pane,0);
                    toggleButtonExpanded.setVisible(false);
                }else{
                    slidePane(home_screen,0);
                    slidePane(user_pane,+564);
                    slidePane(change_Pass_Pane,+ 564);
                    change_Pass_Pane.setVisible(false);
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            toggleButton.setVisible(true);
                            toggleButtonExpanded.setVisible(false);
                        }
                    },200);
                }
            }
        });
        isChange1 =!isChange1;
    }
    private void ManHinhDoi() {
        Platform.runLater(() ->{
            if (isChange2) {
                slidePaneToX(change_Pass_Pane, 0);
                slidePaneToX(user_pane, -340);
            } else {
                slidePaneToX(change_Pass_Pane, +340);
                slidePaneToX(user_pane, 0);
            }
        });
        isChange2 = !isChange2;
    }
    private void ManHinhDoi2(){
        Timer timer = new Timer() ;
        Platform.runLater(() ->{
            if(isChange3){
                slidePane(chuyen_tien_sreen1,0);
                slidePane(home_screen,-564);
                slidePane(chuyen_tien_sreen2,0);
                slidePane(pin_Otp_1,0);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        toggleButton.setVisible(false);
                    }
                },90);
                chuyen_tien_sreen2.setVisible(true);
            }else{
                slidePane(chuyen_tien_sreen1,+564);
                slidePane(home_screen,0);
                slidePane(chuyen_tien_sreen2,+564);
                slidePane(pin_Otp_1,+564);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        toggleButton.setVisible(true);
                    }
                },200);
                chuyen_tien_sreen2.setVisible(false);
            }
        });
        isChange3 = !isChange3;
    }
    private void Check_changeMoney_Empty(){
        String soTaiKhoan = So_Tai_Khoan.getText();
        String tenTaiKhoan = Ten_Tai_Khoan.getText();
        String soTienString = So_Tien.getText();
        String loiNhan = loi_nhan.getText();
        boolean fieldsNotEmpty = !soTaiKhoan.isEmpty() && !tenTaiKhoan.isEmpty() && !soTienString.isEmpty() && !loiNhan.isEmpty();

        if (fieldsNotEmpty) {
            try {
                BigDecimal enteredMoney = new BigDecimal(soTienString);
                BigDecimal availableBalance = new BigDecimal(Money2.getText().replaceAll(",", ""));
                // Xóa đi các dấy phẩy
                if (enteredMoney.compareTo(availableBalance) <= 0) {
                    Change_pass_continue.setDisable(false);
                    xntt_SDT_TaiKhoanToi.setText(soTaiKhoan);
                    xntt_TaiKhoanToi.setText(tenTaiKhoan);
                    SoTienBangChu_1.setText((convertNumberToWords(enteredMoney)+" Việt Nam Đồng").substring(0,1).toUpperCase() + (convertNumberToWords(enteredMoney)+" Việt Nam Đồng").substring(1));
                    NoiDungChuyenKhoan.setText(loiNhan);
                    BigDecimal tien = new BigDecimal(So_Tien.getText());
                    soTienChuyen.setText(ConvertBalance(tien));
                    xntt_SDT_TaiKhoanDi.setText(user_SDT_2.getText());
                    xntt_TaiKhoanDi.setText(user_name_2.getText());
                } else {
                    Change_pass_continue.setDisable(true);
                }
            } catch (NumberFormatException e) {
                Change_pass_continue.setDisable(true);
            }
        } else {
            Change_pass_continue.setDisable(true);
        }
    }
    private void ManHinhDoi3(){
        Platform.runLater(() ->{
            if(isChange4){
                slidePaneToX(chuyen_tien_sreen1,-340);
                slidePaneToX(chuyen_tien_sreen2,0);
                pin_Otp_1.setVisible(true);
            }else{
                pin_Otp_1.setVisible(false);
                slidePaneToX(chuyen_tien_sreen1,0);
                slidePaneToX(chuyen_tien_sreen2,+340);
            }
        });
        isChange4 =!isChange4;
    }
    private void ManHinhDoi4(){
        Platform.runLater(() -> {
            if(isChange5){
                slidePaneToX(chuyen_tien_sreen2,-340);
                slidePaneToX(pin_Otp_1,0);
            }else{
                slidePaneToX(pin_Otp_1,+340);
                slidePaneToX(chuyen_tien_sreen2,0);
            }
            isChange5 = !isChange5 ;
        });
    }
    private void ManHinhChinh1() {
        Platform.runLater(() ->{
            Timer timer = new Timer();
            slidePane(home_screen,0);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        toggleButton.setVisible(true);
                    });
                }
            }, 200);
            user_pane.setVisible(false);
            slidePane(change_Pass_Pane,+564);
            slidePane(user_pane,+564);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        slidePaneToX(change_Pass_Pane,+340);
                        slidePaneToX(user_pane,0);
                        user_pane.setVisible(true);
                        change_Pass_Pane.setVisible(false);
                    });
                }
            }, 900);
        });
    }
    private void ManHinhChinh2() {
        Platform.runLater(()->{
            Timer timer = new Timer() ;
            chuyen_tien_sreen1.setVisible(false);
            slidePane(home_screen,0);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        toggleButton.setVisible(true);
                    });
                }
            }, 200);
            slidePane(chuyen_tien_sreen2,+564);
            slidePane(chuyen_tien_sreen1,+564);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    slidePaneToX(chuyen_tien_sreen1,0);
                    slidePaneToX(chuyen_tien_sreen2,+340);
                    chuyen_tien_sreen1.setVisible(true);
                    chuyen_tien_sreen2.setVisible(false);
                    So_Tai_Khoan.setText("");
                    So_Tien.setText("");
                    Ten_Tai_Khoan.setText("");
                }
            },900);

        });
    }
    private void ManHinhChinh3(){
        Platform.runLater(() -> {
            chuyen_tien_sreen1.setVisible(false);
            chuyen_tien_sreen2.setVisible(false);
            Timer timer = new Timer() ;
            slidePane(home_screen,0);
            slidePane(pin_Otp_1,+564);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    toggleButton.setVisible(true);
                }
            },200);
            slidePane(chuyen_tien_sreen1,+564);
            slidePane(chuyen_tien_sreen2,+564);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    slidePaneToX(chuyen_tien_sreen1,0);
                    slidePaneToX(chuyen_tien_sreen2,+340);
                    slidePaneToX(pin_Otp_1,+340);
                    pin_Otp_1.setVisible(false);
                    chuyen_tien_sreen2.setVisible(false);
                }
            },900);
        });
    }

    // Đăng xuất
    private void backToSignIn() {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SignIN.fxml"));
                Parent root = fxmlLoader.load();

                SignInController loginController = fxmlLoader.getController();
                loginController.setPrevStage(prevStage);

                Scene scene = new Scene(root);
                prevStage.setScene(scene);
                prevStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    @FXML void HidePasswordOnAction_1(KeyEvent event) {Password1 = password_1.getText();pass_text_1.setText(Password1);}
    @FXML void HidePasswordOnAction_2(KeyEvent event) {Password2 = password_2.getText();pass_text_2.setText(Password2);}
    @FXML void HidePasswordOnAction_3(KeyEvent event) {Password3 = password_3.getText();pass_text_3.setText(Password3);}
    @FXML void ShowPasswordOnAction_1(KeyEvent event) {Password1 = pass_text_1.getText();password_1.setText(Password1);}
    @FXML void ShowPasswordOnAction_2(KeyEvent event) {Password2 = pass_text_2.getText();password_2.setText(Password2);}
    @FXML void ShowPasswordOnAction_3(KeyEvent event) {Password3 = pass_text_3.getText();password_3.setText(Password3);}
    @FXML void Close_Eye_ClickOnAction_1(MouseEvent event) {open_eye_1.setVisible(true);pass_text_1.setVisible(true);password_1.setVisible(false);close_eye_1.setVisible(false);Timer timer = new Timer();timer.schedule(new TimerTask() {
        @Override
        public void run() {
            Platform.runLater(() -> Open_Eye_ClickOnAction_1(null));}
    }, 1000);
    }
    @FXML void Close_Eye_ClickOnAction_2(MouseEvent event) {open_eye_2.setVisible(true);pass_text_2.setVisible(true);password_2.setVisible(false);close_eye_2.setVisible(false);Timer timer = new Timer();timer.schedule(new TimerTask() {
        @Override
        public void run() {
            Platform.runLater(() -> Open_Eye_ClickOnAction_2(null));}
    }, 1000);
    }
    @FXML void Close_Eye_ClickOnAction_3(MouseEvent event) {open_eye_3.setVisible(true);pass_text_3.setVisible(true);password_3.setVisible(false);close_eye_3.setVisible(false);Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> Open_Eye_ClickOnAction_3(null));
            }
        }, 1000);}
    @FXML void Open_Eye_ClickOnAction_1(MouseEvent event) {open_eye_1.setVisible(false);pass_text_1.setVisible(false);password_1.setVisible(true);close_eye_1.setVisible(true);}
    @FXML void Open_Eye_ClickOnAction_2(MouseEvent event) {open_eye_2.setVisible(false);pass_text_2.setVisible(false);password_2.setVisible(true);close_eye_2.setVisible(true);}
    @FXML void Open_Eye_ClickOnAction_3(MouseEvent event) {open_eye_3.setVisible(false);pass_text_3.setVisible(false);password_3.setVisible(true);close_eye_3.setVisible(true);}

    public boolean Check(String password2) {
        boolean length = password2.length() < 8;
        boolean number = containsNumber(password2);
        boolean special = SpecialCharacter(password2);
        boolean Upper_value = containsUpperCase(password2);
        if (length) {
            not_3.setVisible(true);
            yes_3.setVisible(false);
        } else {
            yes_3.setVisible(true);
            not_3.setVisible(false);
        }
        if (number || special) {
            yes_2.setVisible(true);
            not_2.setVisible(false);
        } else {
            yes_2.setVisible(false);
            not_2.setVisible(true);
        }
        if (Upper_value) {
            yes_1.setVisible(true);
            not_1.setVisible(false);
        } else {
            yes_1.setVisible(false);
            not_1.setVisible(true);
        }
        return length && (number || special) && Upper_value;
    }

    private boolean containsNumber(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean SpecialCharacter(String str) {
        String regexPattern = "[!@#$%&*()_+=|<>?{}\\[\\]~-]";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(str);

        return matcher.find();
    }

    private boolean containsUpperCase(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private void CheckFields() {
        boolean pass1 = password_1.getText().isEmpty();
        boolean pass2 = password_2.getText().isEmpty();
        boolean pass3 = password_3.getText().isEmpty();
        Change_pass_send.setDisable(pass1 || pass2 || pass3);
    }
    private String ConvertBalance(BigDecimal balance){
        DecimalFormat df = new DecimalFormat("#,##0");
        //giúp chuyển từ 5000000.00 thành 5,000,000
        return df.format(balance);
    }

    private void initializeComponents() {
        upper_infor.setVisible(true);
        toggleButton.setVisible(true);
        initialView.setVisible(true);
        expandedView.setVisible(true);
        toggleButtonExpanded.setVisible(false);
        user_pane.setVisible(true);
        change_Pass_Pane.setVisible(false);
        check_pane.setVisible(false);
        chuyen_tien_sreen1.setVisible(true);
        home_screen.setVisible(true);
        chuyen_tien_sreen1.setVisible(true);
        chuyen_tien_sreen2.setVisible(false);
        pin_Otp_1.setVisible(false);

        pin_Otp_1.setTranslateY(+564);
        pin_Otp_1.setTranslateX(+340);
        expandedView.setTranslateY(-358);
        user_pane.setTranslateY(+564);
        change_Pass_Pane.setTranslateY(+564);
        change_Pass_Pane.setTranslateX(+340);
        chuyen_tien_sreen1.setTranslateY(+564);
        chuyen_tien_sreen2.setTranslateY(+564);
        chuyen_tien_sreen2.setTranslateX(+340);
        CheckFields();
    }
    private void setButtonActionS(){
        toggleButton.setOnAction(e -> {
            AutoUpdateTheBalance();
            new Thread(() -> handleToggle()).start();
        });
        toggleButtonExpanded.setOnAction(e ->new Thread(()-> handleToggle()).start());
        User_Infor.setOnAction(e -> {newPane();user_pane.setVisible(true);});
        user_Home.setOnAction(e -> newPane());
        dangXuat.setOnAction(e -> new Thread(() -> LogOut()).start());
        thaydoimatkhau.setOnAction(e -> ManHinhDoi());
        Move_back_1.setOnAction(e -> ManHinhDoi());
        ChuyenTien_button.setOnAction(e -> {ManHinhDoi2();chuyen_tien_sreen1.setVisible(true);});
        Move_back_2.setOnAction(e -> {ManHinhDoi2();
            So_Tai_Khoan.setText("");
            Ten_Tai_Khoan.setText("");
            So_Tien.setText("");
            loi_nhan.setText("");
        });
        Move_back_2.setOnAction(e -> ManHinhDoi2());
        Home_button_1.setOnAction(e -> ManHinhChinh1());
        Move_back_3.setOnAction(e -> ManHinhDoi3());
        Change_pass_continue.setOnAction(e -> {
            Check_changeMoney_Empty();
            ManHinhDoi3();
        });
        Home_button_2.setOnAction(e -> ManHinhChinh2());
        delete_all_balance.setOnMouseClicked(e -> {
            So_Tien.setText("");
        });
        So_Tai_Khoan.setOnKeyReleased(event -> populateAccountName());
        // Thực hiện khi người dùng nhả phím
        Change_pass_send.setOnAction(e -> {
            ChangsePassword();
        });
        confirm_changeMoney.setOnAction(e -> {
            ManHinhDoi4();
        });
        Move_back_4.setOnAction(e -> {
            ManHinhDoi4();
        });
        CT_chuyentien.setOnAction(e -> {XacThucMaPIN();});
    }
    private void XacThucMaPIN() {
        new Thread(() -> {
            String sdt = user_SDT_2.getText();
            String PIN = MaPin.getText();
            String request = Request.Check_Ma_PIN;
            new ClientCore(sdt, PIN, request, (Screen_Interface) this);
        }).start();
    }
    private void ChangsePassword() {
        String user = user_SDT_1.getText() ;
        String account = password_1.isVisible()?password_1.getText():pass_text_2.getText() ;
        String change = password_2.isVisible()?password_2.getText():pass_text_2.getText() ;
        String request = Request.ChangePassword ;
        new Thread(() -> {
            new ClientCore(user,account,change,request,this);
        }).start();
    }

    private void populateAccountName() {
        String account = So_Tai_Khoan.getText() ;
        if(account != null &&  !account.trim().isEmpty()){
            new Thread(() -> {
                new ClientCore(account,Request.GetAccountName,this) ;
            }).start();
        }
    }

    private void SetTextProperty(){
        password_2.textProperty().addListener((observable, oldValue, newValue) -> {
            Check(newValue);
        });

        password_2.focusedProperty().addListener((observable, oldValue, newValue) -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(200), password_3);
            TranslateTransition transition1 = new TranslateTransition(Duration.millis(200), close_eye_3);
            if (newValue) {
                check_pane.setVisible(true);
                transition.setToY(transition.getByY() + 68);
                transition.play();
                transition1.setToY(transition1.getByY() + 68);
                transition1.play();
            } else {
                check_pane.setVisible(false);
                transition.setToY(transition.getByY());
                transition.play();
                transition1.setToY(transition1.getByY());
                transition1.play();
            }
        });

        password_3.textProperty().addListener((observable, oldValue, newValue) -> {
            String password = (password_2.isVisible()) ? password_2.getText() : pass_text_2.getText();
            if (!newValue.equals(password)) {
                TrueOrNot.setText("Không khớp vui lòng thử lại");
            } else {
                TrueOrNot.setText("");
                CheckFields();
            }
        });
        So_Tai_Khoan.textProperty().addListener((observable, oldValue, newValue) -> {
            Check_changeMoney_Empty();
        });
        Ten_Tai_Khoan.textProperty().addListener((observable, oldValue, newValue) -> {
            Check_changeMoney_Empty();
        });
        So_Tien.textProperty().addListener((observable, oldValue, newValue) -> {
            Check_changeMoney_Empty();
        });
        loi_nhan.textProperty().addListener((observable, oldValue, newValue) -> {
            Check_changeMoney_Empty();
        });
    }

    // Phần chuyển tiền
    private static final String[] units = {"", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};
    private static final String[] tens = {"", "mười", "hai mươi", "ba mươi", "bốn mươi", "năm mươi", "sáu mươi", "bảy mươi", "tám mươi", "chín mươi"};
    private static final String[] hundreds = {"", "một trăm", "hai trăm", "ba trăm", "bốn trăm", "năm trăm", "sáu trăm", "bảy trăm", "tám trăm", "chín trăm"};
    private String convertNumberToWords(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) == 0) {
            return "Không Việt Nam Đồng";
        }

        StringBuilder words = new StringBuilder();

        BigDecimal[] divAndRemainder;
        BigDecimal billion = new BigDecimal("1000000000");
        BigDecimal million = new BigDecimal("1000000");
        BigDecimal thousand = new BigDecimal("1000");
        BigDecimal hundred = new BigDecimal("100");
        BigDecimal ten = new BigDecimal("10");

        if (number.compareTo(billion) >= 0) {
            divAndRemainder = number.divideAndRemainder(billion);
            words.append(convertNumberToWords(divAndRemainder[0])).append(" tỷ ");
            number = divAndRemainder[1];
        }
        if (number.compareTo(million) >= 0) {
            divAndRemainder = number.divideAndRemainder(million);
            words.append(convertNumberToWords(divAndRemainder[0])).append(" triệu ");
            number = divAndRemainder[1];
        }
        if (number.compareTo(thousand) >= 0) {
            divAndRemainder = number.divideAndRemainder(thousand);
            words.append(convertNumberToWords(divAndRemainder[0])).append(" nghìn ");
            number = divAndRemainder[1];
        }
        if (number.compareTo(hundred) >= 0) {
            divAndRemainder = number.divideAndRemainder(hundred);
            words.append(hundreds[divAndRemainder[0].intValue()]).append(" ");
            number = divAndRemainder[1];
        }
        if (number.compareTo(BigDecimal.ZERO) > 0) {
            if (number.compareTo(ten) < 0) {
                words.append(units[number.intValue()]);
            } else if (number.compareTo(new BigDecimal("20")) < 0) {
                words.append("mười ").append(units[number.subtract(ten).intValue()]);
            } else {
                divAndRemainder = number.divideAndRemainder(ten);
                words.append(tens[divAndRemainder[0].intValue()]).append(" ").append(units[divAndRemainder[1].intValue()]);
            }
        }

        return words.toString().trim();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            initializeComponents();
            setButtonActionS();
            SetTextProperty();
        });
    }

    @Override
    public void GetUserNameSuccess(String account) {
        Platform.runLater(() ->{
            if(account.equals(user_name_2.getText())){
                Ten_Tai_Khoan.setText("");
            }else{
                Ten_Tai_Khoan.setText(account);
            }
        });
    }

    @Override
    public void GetUseNameFail() {}

    /*LoginBack*/
    @Override
    public void onLoginSuccess() {}
    @Override
    public void onLoginFailure(String message) {}
    @Override
    public void OnSignUpSuccess() {}
    @Override
    public void OnSignUpFailure(String message) {}
    @Override
    public void logOutSuccess() {
        backToSignIn();
    }

    @Override
    public void Check_Last_Password(String mes) {
        Platform.runLater(() -> {
            pass_check.setText(mes);
        });
    }

    @Override
    public void Change_Password_Success() {
        Platform.runLater(() -> {
            ManHinhChinh1();
            pass_check.setText("");
            password_1.setText("");
            password_2.setText("");
            password_3.setText("");
            Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
            alert.setContentText("Bạn đã thay đổi mật khẩu thành công");
            alert.setHeaderText(null);
            alert.showAndWait();
        });
    }

    @Override
    public void Xac_Thuc_True() {
        Platform.runLater(() -> {
            new Thread(() -> {
                String sdt = user_SDT_2.getText();
                BigDecimal tienChuyen = new BigDecimal(soTienChuyen.getText().replaceAll(",", ""));
                String sdtNguoiGui = xntt_SDT_TaiKhoanToi.getText();
                String loiNhan = loi_nhan.getText();
                LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                String currentDate = now.format(dateFormatter);
                String currentTime = now.format(timeFormatter);

                String request = Request.CTien;
                new ClientCore(currentDate, currentTime, sdt, sdtNguoiGui, tienChuyen, loiNhan, request, this);
            }).start();
        });
    }

    @Override
    public void Xac_Thuc_False() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION) ;
        alert.setHeaderText(null);
        alert.setContentText("Bạn nhập mã pin sai");
        alert.showAndWait();
    }

    @Override
    public void Chuyen_Tien_Thanh_Cong() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION) ;
            alert.setContentText("Bạn đã chuyển tiền thành công");
            alert.setHeaderText(null);
            Optional<ButtonType> choose = alert.showAndWait() ;
            if(choose.get() == ButtonType.OK){
                So_Tai_Khoan.setText("");
                Ten_Tai_Khoan.setText("");
                So_Tien.setText("");
                loi_nhan.setText("");
                MaPin.setText("");
                ManHinhChinh3() ;
            }
        });
    }

    @Override
    public void Chuyen_Tien_Khong_Thanh_Cong() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION) ;
            alert.setContentText("Bạn đã ko chuyển tiền thành công");
            alert.setHeaderText(null);
            alert.showAndWait();
        });
    }

    @Override
    public void UpdateBalance(BigDecimal balance) {Platform.runLater(() -> {setBalance(balance);});}
    public void setBalance(BigDecimal bigDecimal) {
        Money1.setText(ConvertBalance(bigDecimal));
        Money2.setText(ConvertBalance(bigDecimal));
    }
    public void AutoUpdateTheBalance() {
        String sdt = user_SDT_1.getText();
        String request = Request.Auto_Update_Balance;
        new ClientCore(this, sdt, request);}
}