package com.example.bankingck.Client.Controller;

public interface LoginCallBack {
    void onLoginSuccess() ;
    void onLoginFailure(String message) ;
    void OnSignUpSuccess() ;
    void OnSignUpFailure(String message) ;
    void logOutSuccess();
    void GetUserNameSuccess(String accountName);
    void GetUseNameFail() ;
}

