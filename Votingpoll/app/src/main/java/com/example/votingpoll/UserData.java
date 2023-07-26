package com.example.votingpoll;

public class UserData {
    private String uFullname;
    private String uEmail;
    private  long uMobile;
    private String uPassword;
    private  String uConPassword;
    private String uAddress;
    private long uAadhaar;
    UserData(){

    }
    UserData(String fname, String email, String address, long mobile,long aadhar, String password) {
        this.uFullname = fname;
        this.uEmail = email;
        this.uAddress = address;
        this.uMobile = mobile;
        this.uAadhaar = aadhar;
        this.uPassword = password;

    }

        public long getuAadhaar() {
        return uAadhaar;
    }

    public String getuAddress() {
        return uAddress;
    }

    public String getuConPassword() {
        return uConPassword;
    }

    public String getuEmail() {
        return uEmail;
    }

    public String getuFullname() {
        return uFullname;
    }

    public long getuMobile() {
        return uMobile;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuAadhaar(long uAadhaar) {
        this.uAadhaar = uAadhaar;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    public void setuConPassword(String uConPassword) {
        this.uConPassword = uConPassword;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public void setuFullname(String uFullname) {
        this.uFullname = uFullname;
    }

    public void setuMobile(long uMobile) {
        this.uMobile = uMobile;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }
}
