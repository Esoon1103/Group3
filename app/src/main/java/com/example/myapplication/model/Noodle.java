package com.example.myapplication.model;

public class Noodle {
    private String key, name, price, image, id;
    private boolean noodle;

    public Noodle(){
    }

    public Noodle(boolean noodle) {
        this.noodle = noodle;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNoodle() {
        return noodle;
    }

    public void setNoodle(boolean noodle) {
        this.noodle = noodle;
    }
}
