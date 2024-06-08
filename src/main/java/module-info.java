module com.example.bankingck {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.bankingck to javafx.fxml;
    exports com.example.bankingck;
}