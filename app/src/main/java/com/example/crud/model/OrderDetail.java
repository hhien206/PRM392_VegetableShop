package com.example.crud.model;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class OrderDetail {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double totalMoney;

    public OrderDetail() {
    }

    public OrderDetail(int id, int orderId, int productId, int quantity, double totalMoney) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalMoney = totalMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public static OrderDetail ConvertCursorIntoOrderDetail(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("orderDetail_id");
            int orderIdColumnIndex = cursor.getColumnIndex("orderDetail_order_id");
            int productIdColumnIndex = cursor.getColumnIndex("orderDetail_product_id");
            int quantityColumnIndex = cursor.getColumnIndex("orderDetail_order_quantity");
            int totalMoneyColumnIndex = cursor.getColumnIndex("orderDetail_order_totalmoney");

            if (idColumnIndex != -1 && orderIdColumnIndex != -1 && productIdColumnIndex != -1 &&
                    quantityColumnIndex != -1 && totalMoneyColumnIndex != -1) {
                int id = cursor.getInt(idColumnIndex);
                int orderId = cursor.getInt(orderIdColumnIndex);
                int productId = cursor.getInt(productIdColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);
                double totalMoney = cursor.getDouble(totalMoneyColumnIndex);
                return new OrderDetail(id, orderId, productId, quantity, totalMoney);
            }
        }
        return null;
    }

    public static List<OrderDetail> ConvertCursorIntoListOrderDetail(Cursor cursor) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                OrderDetail orderDetail = ConvertCursor(cursor);
                if (orderDetail != null) {
                    orderDetails.add(orderDetail);
                }
            }
            cursor.close();
        }
        return orderDetails;
    }

    private static OrderDetail ConvertCursor(Cursor cursor) {
        if (cursor != null) {
            int idColumnIndex = cursor.getColumnIndex("orderDetail_id");
            int orderIdColumnIndex = cursor.getColumnIndex("orderDetail_order_id");
            int productIdColumnIndex = cursor.getColumnIndex("orderDetail_product_id");
            int quantityColumnIndex = cursor.getColumnIndex("orderDetail_order_quantity");
            int totalMoneyColumnIndex = cursor.getColumnIndex("orderDetail_order_totalmoney");

            if (idColumnIndex != -1 && orderIdColumnIndex != -1 && productIdColumnIndex != -1 &&
                    quantityColumnIndex != -1 && totalMoneyColumnIndex != -1) {
                int id = cursor.getInt(idColumnIndex);
                int orderId = cursor.getInt(orderIdColumnIndex);
                int productId = cursor.getInt(productIdColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);
                double totalMoney = cursor.getDouble(totalMoneyColumnIndex);
                return new OrderDetail(id, orderId, productId, quantity, totalMoney);
            }
        }
        return null;
    }
}
