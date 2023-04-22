package com.hackuniv.daanveer.Model;

import java.util.ArrayList;

public class Donation {
    ArrayList<String> items;
    Long timestamp;
    String donatorId,donatorName,donatorAddress,donatorMobile,donatorImage,mode,organinsation,orgName;
    String status="Accept";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Donation(ArrayList<String> items, String donatorId, String donatorName, String donatorAddress, String donatorMobile, String mode, String organisation) {
        this.items = items;
        this.donatorId = donatorId;
        this.donatorName = donatorName;
        this.donatorAddress = donatorAddress;
        this.donatorMobile = donatorMobile;
        this.mode = mode;
        this.organinsation = organisation;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMode() {
        return mode;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrganinsation() {
        return organinsation;
    }

    public void setOrganinsation(String organinsation) {
        this.organinsation = organinsation;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Donation() {
    }

    public String getDonatorImage() {
        return donatorImage;
    }

    public void setDonatorImage(String donatorImage) {
        this.donatorImage = donatorImage;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public String getDonatorId() {
        return donatorId;
    }

    public void setDonatorId(String donatorId) {
        this.donatorId = donatorId;
    }

    public String getDonatorName() {
        return donatorName;
    }

    public void setDonatorName(String donatorName) {
        this.donatorName = donatorName;
    }

    public String getDonatorAddress() {
        return donatorAddress;
    }

    public void setDonatorAddress(String donatorAddress) {
        this.donatorAddress = donatorAddress;
    }

    public String getDonatorMobile() {
        return donatorMobile;
    }

    public void setDonatorMobile(String donatorMobile) {
        this.donatorMobile = donatorMobile;
    }
}

