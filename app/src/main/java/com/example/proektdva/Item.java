package com.example.proektdva;

public class Item {
    private String itemName;
    private String itemAddress;
    private String itemPhone;
    private String itemWebsite;

    public Item(String name, String address, String phone, String website) {
        this.itemName = name;
        this.itemAddress = address;
        this.itemPhone = phone;
        this.itemWebsite = website;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getItemAddress() {
        return itemAddress;
    }

    public String getItemPhone() {
        return itemPhone;
    }

    public String getItemWebsite() {
        return itemWebsite;
    }

}
