package com.hackuniv.daanveer.Model;

public class Users {
    String userId,userType,userPhone;
    public Users() {
    }
    public Users(String userId, String userType, String userPhone) {
        this.userId = userId;
        this.userType = userType;
        this.userPhone = userPhone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
