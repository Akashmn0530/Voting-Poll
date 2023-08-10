package com.example.votingpoll.candidate;

import android.graphics.Bitmap;

import java.io.Serializable;

public class CandiData implements Serializable {
    private String aucFullname;
    private String aucEmail;
    private  long aucMobile;
    private String aucAddress;
    private String aucAadhaar;
    private float aucRating = 0;
    private String aucId;
    private String aucFeedback;
    private String aucPlans;
    private String aucPass;
    private String partyName;
    private int voteCountCandi;
    Bitmap partySymbol, partyCandidateImage;
    int image1;
    int image;
    CandiData(){ }
    public CandiData(Bitmap partySymbol, String name, Bitmap partyCandidateImage, String pname,String aucAadhaar) {
        this.partySymbol = partySymbol;
        this.partyCandidateImage = partyCandidateImage;
        this.aucFullname = name;
        this.partyName = pname;
        this.aucAadhaar = aucAadhaar;
    }
    public CandiData(String name, int votes, String party){
        this.aucFullname = name;
        this.voteCountCandi = votes;
        this.partyName = party;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public void setPartySymbol(Bitmap partySymbol) {
        this.partySymbol = partySymbol;
    }

    public void setPartyCandidateImage(Bitmap partyCandidateImage) {
        this.partyCandidateImage = partyCandidateImage;
    }

    public Bitmap getPartySymbol() {
        return partySymbol;
    }

    public Bitmap getPartyCandidateImage() {
        return partyCandidateImage;
    }

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

    public String getAucPass() {
        return aucPass;
    }

    public void setAucPass(String aucPass) {
        this.aucPass = aucPass;
    }

    public String getAucFeedback() {
        return aucFeedback;
    }

    public void setAucFeedback(String aucFeedback) {
        this.aucFeedback = aucFeedback;
    }

    public String getAucPlans() {
        return aucPlans;
    }

    public void setAucPlans(String aucPlans) {
        this.aucPlans = aucPlans;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public int getVoteCountCandi() {
        return voteCountCandi;
    }

    public void setVoteCountCandi(int voteCountCandi) {
        this.voteCountCandi = voteCountCandi;
    }
}


