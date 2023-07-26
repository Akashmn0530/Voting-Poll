package com.example.votingadmin;

public class AddPollClass {
    private String aFullname;
    private String aEmail;
    private  long aMobile;
    private String aAddress;
    private long aAadhaar;


    public void setaAadhaar(long uAadhaar) {
        this.aAadhaar = uAadhaar;
    }

    public void setaAddress(String uAddress) {
        this.aAddress = uAddress;
    }


    public void setaEmail(String uEmail) {
        this.aEmail = uEmail;
    }

    public void setaFullname(String uFullname) {
        this.aFullname = uFullname;
    }

    public void setaMobile(long uMobile) {
        this.aMobile = uMobile;
    }

    public long getaAadhaar() {
        return aAadhaar;
    }

    public long getaMobile() {
        return aMobile;
    }

    public String getaAddress() {
        return aAddress;
    }

    public String getaEmail() {
        return aEmail;
    }

    public String getaFullname() {
        return aFullname;
    }

}


