package com.example.votingpoll.candidate;

public class CandiData {
    private String aucFullname;
    private String aucEmail;
    private  long aucMobile;
    private String aucAddress;
    private String aucAadhaar;
    private float aucRating = 0;
    private String aucId;

    public void setaucAadhaar(String uAadhaar) {
        this.aucAadhaar = uAadhaar;
    }

    public void setaucAddress(String uAddress) {
        this.aucAddress = uAddress;
    }

    public void setaucEmail(String uEmail) {
        this.aucEmail = uEmail;
    }

    public void setaucFullname(String uFullname) {
        this.aucFullname = uFullname;
    }

    public void setaucMobile(long uMobile) {
        this.aucMobile = uMobile;
    }

    public String getAucAadhaar() {
        return aucAadhaar;
    }

    public long getAucMobile() {
        return aucMobile;
    }

    public String getAucAddress() {
        return aucAddress;
    }

    public String getAucEmail() {
        return aucEmail;
    }

    public String getAucFullname() {
        return aucFullname;
    }

    public float getAucRating() {
        return aucRating;
    }

    public void setAucRating(float auRating) {
        this.aucRating = auRating;
    }

    public String getAucId() {
        return aucId;
    }

    public void setAucId(String auId) {
        this.aucId = auId;
    }
}


