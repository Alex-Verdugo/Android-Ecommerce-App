package com.example.project2alexanderverdugo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2alexanderverdugo.db.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.SUPPLIES_TABLE)
public class Supplies {

    @PrimaryKey(autoGenerate = true)
    private int itemId;

    private String itemName;
    private int price;
    private int amount;

    public Supplies(String itemName, int price, int amount) {
        this.itemName = itemName;
        this.price = price;
        this.amount = amount;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return

               " ITEM : " + itemName + "\n" + "PRICE : " + price + "\n" + "AMOUNT IN STOCK : " + amount;
    }

    public String toString1() {
        return

                " ITEM : " + itemName + "\n" + "PRICE : " + price  + "\n" +"DATE ORDERED : " + new Date();
    }
}
