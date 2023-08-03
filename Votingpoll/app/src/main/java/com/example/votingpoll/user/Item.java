package com.example.votingpoll.user;

public class Item {
    int image;
    String name;
    int image1;
    String pname;

    public Item(int image, String name, int image1, String pname) {
        this.image = image;
        this.name = name;
        this.image1 = image1;
        this.pname = pname;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
