package com.example.myapplication.model;

public class Dessert {
    private String key, name, price, image, id;
    private boolean dessert;

    public Dessert(){

    }

    public Dessert(boolean dessert) {
        this.dessert = dessert;
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

    public boolean isDessert() {
        return dessert;
    }

    public void setDessert(boolean dessert) {
        this.dessert = dessert;
    }
}
