package com.example.myapplication.model;

public class Rice {
    private String key, name, price, image, id;
    private boolean rice;

    public Rice() {
    }

    public Rice(boolean rice) {
        this.rice = rice;
    }

    public void setRice(boolean rice) {
        this.rice = rice;
    }

    public boolean isRice() {
        return rice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
