package com.hackuniv.daanveer.Model;

public class OrgFounder {
    String username,profileImage,mobile,email,address,city,gender,age;

//    public OrgFounder(String username, String mobile, String email, String address, String city, String gender, String age) {
//        this.username = username;
//        this.mobile = mobile;
//        this.email = email;
//        this.address = address;
//        this.city = city;
//        this.gender = gender;
//        this.age = age;
//    }
    public OrgFounder(String username,String profileImage, String mobile, String email, String address, String city, String gender,  String age) {
        this.username = username;
        this.profileImage = profileImage;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.city = city;
        this.gender = gender;
        this.age = age;
    }

    public OrgFounder() {
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
