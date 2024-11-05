package com.example.crud.model;

import android.database.Cursor;

import com.example.crud.CardActivity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    public User() {
    }

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    // Getters và setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public static User ConvertCursorIntoUser(Cursor cursor) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int userIdColumnIndex = cursor.getColumnIndex("user_id");
                int userNameColumnIndex = cursor.getColumnIndex("user_name");
                int userPasswordColumnIndex = cursor.getColumnIndex("user_password");
                int userEmailColumnIndex = cursor.getColumnIndex("user_email");
                if (userIdColumnIndex != -1 && userNameColumnIndex != -1 && userPasswordColumnIndex != -1) {
                    int id = cursor.getInt(userIdColumnIndex);
                    String name = cursor.getString(userNameColumnIndex);
                    String password = cursor.getString(userPasswordColumnIndex);
                    String email = cursor.getString(userEmailColumnIndex);
                    return new User(id, name, password);
                }
            }
        }
        return null;
    }
    public static List<User> ConvertCursorIntoListUser(Cursor cursor){
        if (cursor != null) {
            List<User> users = new ArrayList<>();

            while (cursor.moveToNext()) {
                User user = new User();
                user = ConvertCursor(cursor);
                if(user!= null){
                    users.add(user);
                }
            }
            cursor.close();
            return users;
        }
        return null;
    }
    private static User ConvertCursor(Cursor cursor){
        if (cursor != null) {
            int userIdColumnIndex = cursor.getColumnIndex("user_id");
            int userNameColumnIndex = cursor.getColumnIndex("user_name");
            int userPasswordColumnIndex = cursor.getColumnIndex("user_password");
            int userEmailColumnIndex = cursor.getColumnIndex("user_email");
            if (userIdColumnIndex != -1 && userNameColumnIndex != -1 && userPasswordColumnIndex != -1) {
                int id = cursor.getInt(userIdColumnIndex);
                String name = cursor.getString(userNameColumnIndex);
                String password = cursor.getString(userPasswordColumnIndex);
                String email = cursor.getString(userEmailColumnIndex);
                return new User(id, name, password);
            }
        }
        return null;
    }
}
