module com.example.bankingck {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens com.example.bankingck to javafx.fxml;
    exports com.example.bankingck;
    opens com.example.bankingck.Server.Controller to javafx.fxml;
    exports com.example.bankingck.Server.Controller;
    exports com.example.bankingck.Client.Controller;
    opens com.example.bankingck.Client.Controller to javafx.fxml;


}