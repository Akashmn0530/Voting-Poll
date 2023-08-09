package com.example.votingpoll.user;

import java.io.Serializable;

public class ServerData implements Serializable {
    private String auFullname;
    private String auEmail;
    private  long auMobile;
    private String auAddress;
    private String auAadhaar;
    private float auRating = 0;
    private String auId;
    private String vote = "Not voted";
    private  String password;
    private String auFeedbackDescription;
    private int voteCount = 0;

    public void setauAadhaar(String uAadhaar) {
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

    public String getAuAadhaar() {
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

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public float getAuRating() {
        return auRating;
    }

    public void setAuRating(float auRating) {
        this.auRating = auRating;
    }

    public String getAuId() {
        return auId;
    }

    public void setAuId(String auId) {
        this.auId = auId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuFeedbackDescription() {
        return auFeedbackDescription;
    }

    public void setAuFeedbackDescription(String auFeedbackDescription) {
        this.auFeedbackDescription = auFeedbackDescription;
    }
}


