package com.example.bankingck.Model;

public class User {
    private int ID ;
    private String Ho;
    private String Ten ;
    private String SDT ;
    private String gmail ;
    private String password ;
    private boolean IsOnline;
    public User(String SDT, String password) {
        this.SDT = SDT;
        this.password = password;
    }
    public User(int ID, String ho, String ten, String SDT, String gmail, String password , boolean IsOnline) {
        this.ID = ID;
        Ho = ho;
        Ten = ten;
        this.SDT = SDT;
        this.gmail = gmail;
        this.password = password;
        this.IsOnline = IsOnline ;
    }
    public User(int ID, String ho, String ten, String SDT, String gmail, String password) {
        this.ID = ID;
        Ho = ho;
        Ten = ten;
        this.SDT = SDT;
        this.gmail = gmail;
        this.password = password;
    }
    public User(String ho, String ten, String SDT, String gmail, String password) {
        Ho = ho;
        Ten = ten;
        this.SDT = SDT;
        this.gmail = gmail;
        this.password = password;
    }
    public User(String SDT){
        this.SDT = SDT;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHo() {
        return Ho;
    }
    public void setHo(String ho) {
        Ho = ho;
    }

    public String getTen() {
        return Ten;
    }
    public void setTen(String ten) {
        Ten = ten;
    }

    public String getSDT() {
        return SDT;
    }
    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getGmail() {
        return gmail;
    }
    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnline() {
        return IsOnline;
    }
    public void setOnline(boolean online) {
        IsOnline = online;
    }

}