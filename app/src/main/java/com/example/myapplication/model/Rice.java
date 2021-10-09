package com.example.myapplication.model;

public class Rice {
    String name;
    String price;
    Integer image;

    public Rice(String name, String price, Integer image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
