package com.example.commonproject1.Tab_1;

import android.graphics.Bitmap;

public class Item {
    private String name, number, email;
    private Bitmap photo;

    public Item(String name, String number, String email, Bitmap photo) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}