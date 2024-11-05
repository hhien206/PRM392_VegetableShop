package com.example.crud.model;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private String date;
    private int quantity;
    private double totalMoney;
    private String status;

    public Order() {
    }

    public Order(int id, int userId, String date, int quantity, double totalMoney, String status) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.quantity = quantity;
        this.totalMoney = totalMoney;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Order ConvertCursorIntoOrder(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("order_id");
            int userIdColumnIndex = cursor.getColumnIndex("order_userid");
            int dateColumnIndex = cursor.getColumnIndex("order_date");
            int quantityColumnIndex = cursor.getColumnIndex("order_quantity");
            int totalMoneyColumnIndex = cursor.getColumnIndex("order_totalmoney");
            int statusColumnIndex = cursor.getColumnIndex("order_status");

            if (idColumnIndex != -1 && userIdColumnIndex != -1 && dateColumnIndex != -1 &&
                    quantityColumnIndex != -1 && totalMoneyColumnIndex != -1 && statusColumnIndex != -1) {
                int id = cursor.getInt(idColumnIndex);
                int userId = cursor.getInt(userIdColumnIndex);
                String date = cursor.getString(dateColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);
                double totalMoney = cursor.getDouble(totalMoneyColumnIndex);
                String status = cursor.getString(statusColumnIndex);
                return new Order(id, userId, date, quantity, totalMoney, status);
            }
        }
        return null;
    }

    public static List<Order> ConvertCursorIntoListOrder(Cursor cursor) {
        List<Order> orders = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Order order = ConvertCursor(cursor);
                if (order != null) {
                    orders.add(order);
                }
            }
            cursor.close();
        }
        return orders;
    }

    private static Order ConvertCursor(Cursor cursor) {
        if (cursor != null) {
            int idColumnIndex = cursor.getColumnIndex("order_id");
            int userIdColumnIndex = cursor.getColumnIndex("order_userid");
            int dateColumnIndex = cursor.getColumnIndex("order_date");
            int quantityColumnIndex = cursor.getColumnIndex("order_quantity");
            int totalMoneyColumnIndex = cursor.getColumnIndex("order_totalmoney");
            int statusColumnIndex = cursor.getColumnIndex("order_status");

            if (idColumnIndex != -1 && userIdColumnIndex != -1 && dateColumnIndex != -1 &&
                    quantityColumnIndex != -1 && totalMoneyColumnIndex != -1 && statusColumnIndex != -1) {
                int id = cursor.getInt(idColumnIndex);
                int userId = cursor.getInt(userIdColumnIndex);
                String date = cursor.getString(dateColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);
                double totalMoney = cursor.getDouble(totalMoneyColumnIndex);
                String status = cursor.getString(statusColumnIndex);
                return new Order(id, userId, date, quantity, totalMoney, status);
            }
        }
        return null;
    }
}
