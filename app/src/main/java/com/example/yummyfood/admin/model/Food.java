package com.example.yummyfood.admin.model;

public class Food {
    private int id, image;
    private String name, shortDesc;
    private int price;

    public Food(int id, String name, String shortDesc, int image, int price) {
        this.id = id;
        this.name = name;
        this.shortDesc = shortDesc;
        this.image = image;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
