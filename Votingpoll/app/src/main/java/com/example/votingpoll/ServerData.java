package com.example.votingpoll;

import android.content.Context;

import java.util.List;

public class ServerData {
    private String auFullname;
    private String auEmail;
    private  long auMobile;
    private String auAddress;
    private long auAadhaar;
    private float auRating = 0;
    private String vote = "Not voted";

    private int voteCount = 0;

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
}


