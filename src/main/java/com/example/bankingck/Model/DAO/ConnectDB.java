package com.example.bankingck.Model.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectDB {
    public static Connection OpenConnection() {
        Connection connection = null ;

        final String DATABASE_NAME = "jdbc:mysql://127.0.0.1:3306/Banking";
        final String USER = "root" ;
        final String password = "123456";
        try{
            connection = DriverManager.getConnection(DATABASE_NAME,USER,password) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection ;
    }
    public static void CloseConnection(Connection conn) {
        if(conn != null){
            try{
                if(!conn.isClosed()) {
                    conn.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
