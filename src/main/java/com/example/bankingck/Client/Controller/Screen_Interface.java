package com.example.bankingck.Client.Controller;

import java.math.BigDecimal;

public interface Screen_Interface {
    void Check_Last_Password(String message);
    void Change_Password_Success();
    void Xac_Thuc_True() ;
    void Xac_Thuc_False() ;
    void Chuyen_Tien_Thanh_Cong() ;
    void Chuyen_Tien_Khong_Thanh_Cong() ;
    void UpdateBalance(BigDecimal balance) ;
}

