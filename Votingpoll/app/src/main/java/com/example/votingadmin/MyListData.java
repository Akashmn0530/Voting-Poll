package com.example.votingadmin;

public class MyListData{
    private String fullname, email, address;
    private long mobile, aadhar;
    public MyListData(String fullname, long mobile, long aadhar, String email, String address) {
        this.fullname = fullname;
        this.email = email;
        this.address = address;
        this.mobile = mobile;
        this.aadhar = aadhar;
    }

    public long getAadhar() {
        return aadhar;
    }

    public long getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }
}
