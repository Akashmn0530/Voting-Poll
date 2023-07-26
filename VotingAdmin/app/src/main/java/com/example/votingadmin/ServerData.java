package com.example.votingadmin;

import android.content.Context;

import java.util.List;

public class ServerData {
    private String auFullname;
    private String auEmail;
    private  long auMobile;
    private String auAddress;
    private long auAadhaar;

    public void setauAadhaar(long uAadhaar) {
        this.auAadhaar = uAadhaar;
    }

    public void setauAddress(String uAddress) {
        this.auAddress = uAddress;
    }


    public void setauEmail(String uEmail) {
        this.auEmail = uEmail;
    }

    public void setauFullname(String uFullname) {
        this.auFullname = uFullname;
    }

    public void setauMobile(long uMobile) {
        this.auMobile = uMobile;
    }

    public long getAuAadhaar() {
        return auAadhaar;
    }

    public long getAuMobile() {
        return auMobile;
    }

    public String getAuAddress() {
        return auAddress;
    }

    public String getAuEmail() {
        return auEmail;
    }

    public String getAuFullname() {
        return auFullname;
    }
}


