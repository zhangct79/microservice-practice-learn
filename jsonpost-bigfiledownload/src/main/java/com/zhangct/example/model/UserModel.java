package com.zhangct.example.model;

/**
 * @author zhangct
 * @date 20/8/7
 */
public class UserModel {
    private String loginName;
    private String password;
    private String email;
    private String phone;

    public UserModel(){

    }

    public UserModel(String loginName, String password, String email, String phone) {
        this.loginName = loginName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
