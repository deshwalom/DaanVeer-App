package com.hackuniv.daanveer.Model;

public class Organisation {
    String orgName,orgRegNo,orgMobile,orgEmail,orgAddress,orgCity,orgPic,orgId,orgDsc;
    Boolean verifed;
    OrgFounder orgFounder;
    //done
    public Organisation(String orgName,Boolean verifed, String orgRegNo, String orgMobile, String orgEmail, String orgAddress, String orgCity,String orgId,String orgPic,String orgDsc) {
        this.orgName = orgName;
        this.verifed = verifed;
        this.orgRegNo = orgRegNo;
        this.orgMobile = orgMobile;
        this.orgEmail = orgEmail;
        this.orgAddress = orgAddress;
        this.orgCity = orgCity;
        this.orgId =orgId;
        this.orgPic =orgPic;
        this.orgDsc =orgDsc;

    }
 public Organisation(String orgName,Boolean verifed, String orgRegNo, String orgMobile, String orgEmail, String orgAddress, String orgCity,String orgId,String orgPic,OrgFounder orgFounder,String orgDsc) {
        this.orgName = orgName;
        this.verifed = verifed;
        this.orgRegNo = orgRegNo;
        this.orgMobile = orgMobile;
        this.orgEmail = orgEmail;
        this.orgAddress = orgAddress;
        this.orgCity = orgCity;
        this.orgId =orgId;
        this.orgPic =orgPic;
        this.orgFounder = orgFounder;
     this.orgDsc =orgDsc;
    }

    public OrgFounder getOrgFounder() {
        return orgFounder;
    }

    public void setOrgFounder(OrgFounder orgFounder) {
        this.orgFounder = orgFounder;
    }
//done
    public Organisation(String orgName, Boolean verifed, String orgMobile, String orgEmail, String orgAddress, String orgCity, String orgId,String orgPic,String orgDsc) {
        this.orgName = orgName;
        this.verifed = verifed;
        this.orgMobile = orgMobile;
        this.orgEmail = orgEmail;
        this.orgAddress = orgAddress;
        this.orgCity = orgCity;
        this.orgId =orgId;
        this.orgPic =orgPic;
        this.orgDsc =orgDsc;
    }
 public Organisation(String orgName, Boolean verifed, String orgMobile, String orgEmail, String orgAddress, String orgCity, String orgId,String orgPic,OrgFounder orgFounder,String orgDsc) {
        this.orgName = orgName;
        this.verifed = verifed;
        this.orgMobile = orgMobile;
        this.orgEmail = orgEmail;
        this.orgAddress = orgAddress;
        this.orgCity = orgCity;
        this.orgId =orgId;
        this.orgPic =orgPic;
     this.orgFounder = orgFounder;
     this.orgDsc =orgDsc;
    }


    public Organisation() {
    }

    public String getOrgDsc() {
        return orgDsc;
    }

    public void setOrgDsc(String orgDsc) {
        this.orgDsc = orgDsc;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    public String getOrgRegNo() {
        return orgRegNo;
    }

    public void setOrgRegNo(String orgRegNo) {
        this.orgRegNo = orgRegNo;
    }

    public String getOrgMobile() {
        return orgMobile;
    }

    public void setOrgMobile(String orgMobile) {
        this.orgMobile = orgMobile;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgCity() {
        return orgCity;
    }

    public void setOrgCity(String orgCity) {
        this.orgCity = orgCity;
    }

    public String getOrgPic() {
        return orgPic;
    }

    public void setOrgPic(String orgPic) {
        this.orgPic = orgPic;
    }

    public Boolean getVerifed() {
        return verifed;
    }

    public void setVerifed(Boolean verifed) {
        this.verifed = verifed;
    }
}
