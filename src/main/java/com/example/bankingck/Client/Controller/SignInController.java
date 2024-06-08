package com.example.bankingck.Client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class SignInController {
    public void ShowPasswordOnAction(KeyEvent keyEvent) {
    }

    public void open_eye_OnAction(MouseEvent mouseEvent) {
    }

    public void HidePasswordOnAction(KeyEvent keyEvent) {
    }

    public void close_eye_OnAction(MouseEvent mouseEvent) {
    }
    public  void SignUp(ActionEvent a){
        try {
            // Đảm bảo rằng đường dẫn đến tệp FXML là chính xác
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bankingck/SignUP.fxml"));
            Parent root = loader.load();

            // Lấy controller từ loader
            SignUpController controller = loader.getController();

            // Tạo và hiển thị Stage mới
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();


        } catch (Exception e) {
            // In ra chi tiết lỗi khác
            e.printStackTrace();
        }


    }
}
