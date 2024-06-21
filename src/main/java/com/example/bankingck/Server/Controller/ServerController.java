package com.example.bankingck.Server.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerController implements Initializable {
    private server serverCore;
    private String port;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Start_server_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (port_server.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Hệ thống", null, "Bạn chưa nhập PORT\nVui lòng nhập đầy đủ!");
                } else {
                    try {
                        port = port_server.getText() ;
                        if(isNumber(port)) {
                            if(Integer.parseInt(port) <= 65535){
                                status_Running.setVisible(true);
                                status_OFF.setVisible(false);
                                Start_server_button.setDisable(true);
                                End_server_button.setDisable(false);
                                serverCore = new server(Integer.parseInt(port),ServerController.this);
                                updateMessage("START SERVER ON PORT :"+port);
                                port_server.setDisable(true);
                            }else{
                                showAlert(Alert.AlertType.CONFIRMATION, "Lỗi", null, "Port không được quá 65.535!\nVui lòng thử lại");
                            }
                        }else{
                            showAlert(Alert.AlertType.WARNING, "Thông báo", null, "Port vui lòng là số!");
                        }
                    } catch (Exception e) {
                        updateMessage("START ERROR");
                        e.printStackTrace();
                    }
                }
            }
        });

        End_server_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Thread close_server = new Thread(() -> {
                    try {
                        serverCore.stopServer();
                        updateMessage("STOP SERVER");
                        status_Running.setVisible(false);
                        status_OFF.setVisible(true);
                        Start_server_button.setDisable(false);
                        End_server_button.setDisable(true);
                        port_server.setDisable(false);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
                close_server.start();
            }
        });
    }


    @FXML private Button End_server_button;
    @FXML private Button InforButton;
    @FXML private TextArea Message;
    @FXML private Label Number_Of_User;
    @FXML private Button Start_server_button;
    @FXML private TextField port_server;
    @FXML private Label status_OFF;
    @FXML private Label status_Running;
    @FXML private Button userButton;

  

    public void updateMessage(String msg) {
        Platform.runLater(() -> Message.appendText(msg + "\n"));
    }
    public boolean isNumber(String n) {
        String regex = "^\\d{3,5}$"; /* một chuỗi có phải là số nguyên dương
                                     có từ 4 đến 8 chữ số hay không trong Java */

        // Tạo đối tượng Pattern
        Pattern pattern = Pattern.compile(regex) ;

        // Tạo đối tượng Mathcher
        Matcher matcher  = pattern.matcher(n) ;

        // Kiểm tra chuỗi có khớp với biểu thức chính quy
        return matcher.matches() ;
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
