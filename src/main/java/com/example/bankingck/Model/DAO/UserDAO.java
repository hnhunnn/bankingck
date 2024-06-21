package com.example.bankingck.Model.DAO;

import com.example.bankingck.Model.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    private static Connection conn = com.example.bankingck.Model.DAO.ConnectDB.OpenConnection();
    public static User verifyUser(User user){
        try{
            conn = com.example.bankingck.Model.DAO.ConnectDB.OpenConnection() ;
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM `bank` WHERE `SDT` = ? AND `Password` = ? ");
            preparedStatement.setString(1,user.getSDT());
            preparedStatement.setString(2,user.getPassword());
            ResultSet rs = preparedStatement.executeQuery() ;
            while(rs.next()) {
                return new User(rs.getInt("Id"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("SDT"),
                        rs.getString("Gmail"),
                        rs.getString("Password"),
                        rs.getInt("IsOnline") != 0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null ;
    }
    public static User verifyUserBy_SDT(User user){
        try{
            conn = ConnectDB.OpenConnection() ;
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `bank` WHERE `SDT` = ?");
            preparedStatement.setString(1, user.getSDT());

            ResultSet rs = preparedStatement.executeQuery() ;
            while(rs.next()){
                return new User(rs.getInt("Id"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("SDT"),
                        rs.getString("Gmail"),
                        rs.getString("Password"),
                        rs.getInt("IsOnline") != 0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void AddUser(User user){
        try{
            conn = ConnectDB.OpenConnection() ;
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO `bank` (`Ho`, `Ten`, `SDT`, `Gmail`, `Password`)"
                            + "VALUES (?,?,?,?,?);");
            preparedStatement.setString(1,user.getHo());
            preparedStatement.setString(2,user.getTen());
            preparedStatement.setString(3,user.getSDT());
            preparedStatement.setString(4,user.getGmail());
            preparedStatement.setString(5,user.getPassword());

            int rs =  preparedStatement.executeUpdate();
            if(rs>0){
                PreparedStatement insertInformation = conn.prepareStatement("INSERT INTO `user` (`SDT`)"
                        + "VALUES (?);");
                insertInformation.setString(1, user.getSDT());
                int infoRs = insertInformation.executeUpdate();
                if (infoRs > 0) {
                    System.out.println("User information inserted successfully into 'information'.");
                } else {
                    System.out.println("User information insertion failed in 'information'.");
                }
            }else{
                System.out.println("User information insertion failed.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static boolean CheckDuplicated(User user){
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM `bank` WHERE `SDT` = ? ");
            preparedStatement.setString(1,user.getSDT());

            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return true ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false ;
    }

    public static void updateToOnline(int id) {
        try{
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE `bank`" +
                    "SET `IsOnline` = 1 Where `ID` = ? ");
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate() ;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void updateToOffline(int id) {
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE `bank` SET `IsOnline` = 0 Where `ID` = ? ");
            preparedStatement.setInt(1,id); ;
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate() ;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String ChangeName(String SDT) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT `Ho` , `Ten` FROM `bank` WHERE `SDT` =  ?");
            preparedStatement.setString(1,SDT);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                String firstName = rs.getString("Ho") ;
                String lastName = rs.getString("Ten") ;
                return firstName +" "+lastName ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Unknown User" ;
    }
    public static BigDecimal UpdateBalance (String SDT){
        try{
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT `Balance` FROM `user` WHERE `SDT` = ?") ;
            preparedStatement.setString(1,SDT);

            ResultSet rs = preparedStatement.executeQuery() ;
            if(rs.next()){
                return rs.getBigDecimal("Balance");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null ;
    }
    public static void updatePassword(String sdt , String newPassword){
        try{
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE `bank` SET `Password` = ? WHERE `SDT` = ?") ;
            preparedStatement.setString(1,newPassword);
            preparedStatement.setString(2,sdt);

            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkIsBanned(User user){
        try{
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM banned_user WHERE `SDT_User` =?");
            preparedStatement.setString(1,user.getSDT());

            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false ;
    }
}
