package com.example.bankingck.Client.Controller;

import java.net.InetAddress;
import java.net.Socket;
/*____________________DONE_____________________*/
public class Client {
    private static final int PORT = 18100 ;
    public static Socket getConnect() {
        Socket socket = null ;
        try{
            InetAddress Url = InetAddress.getByName("localhost")  ;
            socket = new Socket(Url,PORT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return socket ;
    }
    public static void getClose(Socket socket) {
        if(socket != null && !socket.isClosed()){
            try{
                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
