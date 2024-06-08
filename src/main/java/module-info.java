module com.example.bankingck {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.bankingck to javafx.fxml;
    exports com.example.bankingck;
    opens com.example.bankingck.Server.Controller to javafx.fxml;
    exports com.example.bankingck.Model;
    exports com.example.bankingck.Server.Controller;
    exports com.example.bankingck.Client.Controller;



}