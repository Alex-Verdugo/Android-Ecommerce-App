package com.example.project2alexanderverdugo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2alexanderverdugo.db.AppDatabase;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userID;

    private String username;
    private String password;
    private Boolean isAdmin;
    private String orders = " ";

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Boolean getAdmin() {
        return this.isAdmin;
    }

    public void setAdmin(Boolean admin) {
        this.isAdmin = admin;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin ;
    }

    public User(String username, String password, Boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}
