package com.tth.flappybird2;

import android.graphics.drawable.Drawable;

public class BirdM {
    private int bird_id;
    private int price;
    private boolean state;
    private int imageID;
    private String name;
    private int bird_up_id;
    private int bird_down_id;

    public BirdM() {
    }

    public BirdM(int bird_id, int price, boolean state, int imageID, String name, int bird_up_id, int bird_down_id) {
        this.bird_id = bird_id;
        this.price = price;
        this.state = state;
        this.imageID = imageID;
        this.name = name;
        this.bird_up_id = bird_up_id;
        this.bird_down_id = bird_down_id;
    }

    public BirdM(int price, boolean state, int imageID, String name, int bird_up_id, int bird_down_id) {
        this.price = price;
        this.state = state;
        this.imageID = imageID;
        this.name = name;
        this.bird_up_id = bird_up_id;
        this.bird_down_id = bird_down_id;
    }

    public int getBird_up_id() {
        return bird_up_id;
    }

    public void setBird_up_id(int bird_up_id) {
        this.bird_up_id = bird_up_id;
    }

    public int getBird_down_id() {
        return bird_down_id;
    }

    public void setBird_down_id(int bird_down_id) {
        this.bird_down_id = bird_down_id;
    }

    public int getBird_id() {
        return bird_id;
    }

    public void setBird_id(int bird_id) {
        this.bird_id = bird_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
