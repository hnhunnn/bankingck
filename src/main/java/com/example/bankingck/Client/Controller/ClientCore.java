package com.example.bankingck.Client.Controller;

import com.example.bankingck.Client.Controller.Client;
import com.example.bankingck.Client.Controller.Screen_Interface;
import javafx.application.Platform;
import javafx.scene.control.Label;
import com.example.bankingck.Client.Controller.LoginCallBack;
import com.example.bankingck.Model.Request;

import java.io.*;
import java.net.Socket;



// Class này dùng để gửi đi dữ liệu tới server
public class ClientCore {
    public ClientCore(String SDT, String password, String request, LoginCallBack loginCallback) {
        this.socket = Client.getConnect();
        this.loginCallBack = loginCallback;
        System.out.println(SDT + " : " +password);
        Runnable clientCore = new Runnable() {
            @Override
            public void run() {
                try {
                    SendRequest(request, socket);
                    Send_Login_Value(SDT, password, socket);
                    int loginResult = getSuccess(socket);
                    Platform.runLater(() -> {
                        if (loginResult == 1) {
                            loginCallback.onLoginSuccess();
                        } else {
                            loginCallback.onLoginFailure("SDT hoặc mật khẩu không đúng vui lòng thử lại");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Client.getClose(socket);
                }
            }
        };
        new Thread(clientCore).start();
    }
    /*Dăng ký*/ public ClientCore(String Ho, String Ten, String SDT, String gmail, String password, String request, LoginCallBack loginCallBack) {
        this.socket = Client.getConnect();
        this.loginCallBack = loginCallBack;
        System.out.println(Ho + " : " + Ten + " : " + SDT + " : " + gmail + " : " + password);
        Runnable clientCore = new Runnable() {
            @Override
            public void run() {
                try {
                    SendRequest(request, socket);
                    Send_SignUp_Value(Ho, Ten, SDT, gmail, password, socket);
                    int SignUpCheck = getSuccess(socket);
                    System.out.println(SignUpCheck);
                    Platform.runLater(() -> {
                        if(SignUpCheck == 1) loginCallBack.OnSignUpSuccess();
                        else if(SignUpCheck == 2) loginCallBack.OnSignUpFailure("SDT đã có tài khoản!");
                        else loginCallBack.OnSignUpFailure("không thể đăng ký");
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Client.getClose(socket);
                }
            }
        };
        new Thread(clientCore).start();
    }
    public ClientCore(String SDT, String request,LoginCallBack loginCallBack){
        this.socket = Client.getConnect();
        this.loginCallBack = loginCallBack;
        Runnable signOff = new Runnable() {
            @Override
            public void run() {
                try {
                    SendRequest(request, socket);
                    System.out.println(request);
                    Send_SDT_ToSearch(SDT, socket);
                    int getSuccess = getSuccess(socket);
                    Platform.runLater(() -> {
                        if (getSuccess == 3) {
                            loginCallBack.logOutSuccess();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    Client.getClose(socket);
                }
            }
        };
        new Thread(signOff).start();
    }
    public ClientCore(String SDT , String oldPassword , String newPassword , String request , Screen_Interface screenInterface){
        this.socket = Client.getConnect() ;
        this.screenInterface = screenInterface ;
        Runnable change_password = new Runnable() {
            @Override
            public void run() {
                try{
                    SendRequest(request,socket);
                    Send_SDT_Password_ToCheck(SDT,oldPassword,newPassword,socket);
                    int n = getSuccess(socket);
                    if( n == 5 ){
                        screenInterface.Check_Last_Password("Mật khẩu hiện tại không đúng vui lòng thử lại");
                    } else if (n == 6) {
                        screenInterface.Change_Password_Success();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    Client.getClose(socket);
                }
            }
        };
        new Thread(change_password).start();
    }

    public void SendRequest(String request, Socket socket) {
        try {
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            toServer.write(request + "\n");
            toServer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Gửi yêu cầu tới server*/
    public void Send_Login_Value(String SDT, String password, Socket socket) {
        try {
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String access = fromServer.readLine();
            System.out.println(access);
            if (access.equals("<Oke>")) {
                toServer.write(SDT + "\n");
                toServer.write(password + "\n");
            }
            toServer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Send_SignUp_Value(String Ho, String Ten, String SDT, String gmail, String password, Socket socket) {
        try {
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String access = fromServer.readLine();
            System.out.println(access);
            if (access.equals("<Oke>")) {
                toServer.write(Ho + "\n");
                toServer.write(Ten + "\n");
                toServer.write(SDT + "\n");
                toServer.write(gmail + "\n");
                toServer.write(password + "\n");
            }
            toServer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Send_SDT_ToSearch(String sdt, Socket socket) {
        try {
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            String access = fromServer.readLine();
            System.out.println(access);
            if(access.equals("<Oke>")) {
                toServer.write(sdt+ "\n");
            }
            toServer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Send_SDT_Password_ToCheck(String sdt , String password ,String newPassword, Socket socket){
        try{
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            String access = fromServer.readLine();
            System.out.println(access);
            if(access.equals(Request.OKE)) {
                toServer.write(sdt + "\n");
                toServer.write(password+"\n");
                toServer.write(newPassword+"\n");
                toServer.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private int getSuccess(Socket socket) {
        try {
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String access = fromServer.readLine();
            System.out.println(access);
            switch (access) {
                case Request.ExistNumberPhone : return 2 ;
                case Request.SignUpSuccess, Request.LoginSuccess : return 1;
                case Request.SignOffSuccess: return 3 ;
                case Request.GetAccountNameSuccess:
                    Platform.runLater(() -> {
                        try{
                            String ten = fromServer.readLine();
                            if(ten != null){
                                loginCallBack.GetUserNameSuccess(ten);
                            }else{
                                loginCallBack.GetUseNameFail();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                    return 4;
                case Request.LastPasswordFail: return 5 ;
                case Request.ChangePassSuccess: return 6 ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    private Socket socket;
    private LoginCallBack loginCallBack ;
    private Screen_Interface screenInterface;
}
