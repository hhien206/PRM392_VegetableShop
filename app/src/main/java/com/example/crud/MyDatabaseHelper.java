package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "VegetableShop.db";
    private static final int DATABASE_VERSION = 1;

    //CREATE VEGETABLE DB
    private static final String TABLE_NAME = "my_vegetableShop";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "vegetable_name";
    private static final String COLUMN_CATEGORY = "vegetable_category";
    private static final String COLUMN_ORIGINCOUNTRY = "vegetable_origincountry";
    private static final String COLUMN_PRICE = "vegetable_price";

    //CREATE USERS DB
    private static final String USER_TABLE_NAME = "my_users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USERNAME = "user_name";
    private static final String COLUMN_PASSWORD = "user_password";
    private static final String COLUMN_EMAIL = "user_email";

    //CREATE ORDERDETAIL DB
    private static final String ORDERDETAIL_TABLE_NAME = "my_orderDetails";
    private static final String COLUMN_ORDERDETAIL_ID = "orderDetail_id";
    private static final String COLUMN_ORDERDETAIL_ORDER_ID = "orderDetail_order_id";
    private static final String COLUMN_ORDERDETAIL_PRODUCT_ID = "orderDetail_product_id";
    private static final String COLUMN_ORDERDETAIL_QUANTITY = "orderDetail_order_quantity";
    private static final String COLUMN_ORDERDETAIL_TOTALMONEY = "orderDetail_order_totalmoney";

    //CREATE ORDER DB
    private static final String ORDER_TABLE_NAME = "my_orders";
    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_ORDER_USER_ID = "order_userid";
    private static final String COLUMN_ORDER_DATE = "order_date";
    private static final String COLUMN_ORDER_QUANTITY = "order_quantity";
    private static final String COLUMN_ORDER_TOTALMONEY = "order_totalmoney";
    private static final String COLUMN_ORDER_STATUS = "order_status";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " ("  + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_CATEGORY + " TEXT, " +
                        COLUMN_ORIGINCOUNTRY + " TEXT, " +
                        COLUMN_PRICE + " REAL);";
        db.execSQL(query);

        String queryUser =
                "CREATE TABLE " + USER_TABLE_NAME +
                        " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_USERNAME + " TEXT, " +
                        COLUMN_PASSWORD + " TEXT, " +
                        COLUMN_EMAIL + " TEXT);";
        db.execSQL(queryUser);

        String queryOrderDetails =
                "CREATE TABLE " + ORDERDETAIL_TABLE_NAME +
                        " (" + COLUMN_ORDERDETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ORDERDETAIL_ORDER_ID + " INTEGER, " +
                        COLUMN_ORDERDETAIL_PRODUCT_ID + " INTEGER, " +
                        COLUMN_ORDERDETAIL_QUANTITY + " INTEGER, " +
                        COLUMN_ORDERDETAIL_TOTALMONEY + " REAL);";
        db.execSQL(queryOrderDetails);

        String queryOrder =
                "CREATE TABLE " + ORDER_TABLE_NAME +
                        " (" + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ORDER_USER_ID + " INTEGER, " +
                        COLUMN_ORDER_DATE + " TEXT, " +
                        COLUMN_ORDER_QUANTITY + " INTEGER, " +
                        COLUMN_ORDER_TOTALMONEY + " REAL, " +
                        COLUMN_ORDER_STATUS + " TEXT);";
        db.execSQL(queryOrder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }
    //MANAGE VEGETABLE
    Cursor getVegetableById(int vegetableId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(vegetableId)});

        // Return the cursor if it contains results; otherwise, close it and return null
        if (cursor != null && cursor.getCount() > 0) {
            return cursor;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }
    void addVegetable(String name, String category, String origincountry, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_ORIGINCOUNTRY, origincountry);
        cv.put(COLUMN_PRICE, price);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
        }
    }
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    void updateVegetable(String id, String name, String category, String origincountry , String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_ORIGINCOUNTRY, origincountry);
        cv.put(COLUMN_PRICE, price);
        long result = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{id});
        if (result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
        }
    }
    void deleteVegetable(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_LONG).show();
        }
    }

    //MANAGE USERS

    Cursor checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " +
                        COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{username, password});

        return cursor;
    }
    void addUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_EMAIL, email);

        long result = db.insert(USER_TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to add user!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "User added successfully!", Toast.LENGTH_LONG).show();
        }
    }

    Cursor readAllUsers() {
        String query = "SELECT * FROM " + USER_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateUser(String id, String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_EMAIL, email);

        long result = db.update(USER_TABLE_NAME, cv, COLUMN_USER_ID + "=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update user!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "User updated successfully!", Toast.LENGTH_LONG).show();
        }
    }

    void deleteUser(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(USER_TABLE_NAME, COLUMN_USER_ID + "=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete user!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "User deleted successfully!", Toast.LENGTH_LONG).show();
        }
    }

    long addOrder(String userId, String date, String quantity, String totalmoney, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ORDER_USER_ID, userId);
        cv.put(COLUMN_ORDER_DATE, date);
        cv.put(COLUMN_ORDER_QUANTITY, quantity);
        cv.put(COLUMN_ORDER_TOTALMONEY, totalmoney);
        cv.put(COLUMN_ORDER_STATUS, status);
        long result = db.insert(ORDER_TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
        }
        return result;
    }

    public void updateOrderStatusAndDate(int orderId, String status, String orderDate, int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ORDER_STATUS, status);
        cv.put(COLUMN_ORDER_DATE, orderDate);
        cv.put(COLUMN_ORDER_QUANTITY, quantity);
        cv.put(COLUMN_ORDER_TOTALMONEY, price);

        long result = db.update(ORDER_TABLE_NAME, cv, COLUMN_ORDER_ID + "=?", new String[]{String.valueOf(orderId)});

        if (result == -1) {
            Toast.makeText(context, "Failed to update order!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Order updated successfully!", Toast.LENGTH_LONG).show();
        }
    }

    public Cursor getOrderById(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ORDER_TABLE_NAME + " WHERE " + COLUMN_ORDER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderId)});

        // Trả về null nếu không có dữ liệu
        if (cursor != null && cursor.getCount() > 0) {
            return cursor;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }
    public Cursor getCompletedOrdersByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ORDER_TABLE_NAME + " WHERE "
                + COLUMN_ORDER_USER_ID + " = ? AND "
                + COLUMN_ORDER_STATUS + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), "COMPLETED"});

        if (cursor != null && cursor.getCount() > 0) {
            return cursor;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }
    public Cursor getCartByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ORDER_TABLE_NAME + " WHERE "
                + COLUMN_ORDER_USER_ID + " = ? AND "
                + COLUMN_ORDER_STATUS + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), "CART"});
        if (cursor != null && cursor.getCount() > 0) {
            return cursor;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }
    void addOrderDetail(String orderId, String productId, String quantity, String totalmoney){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ORDERDETAIL_ORDER_ID, orderId);
        cv.put(COLUMN_ORDERDETAIL_PRODUCT_ID, productId);
        cv.put(COLUMN_ORDERDETAIL_QUANTITY, quantity);
        cv.put(COLUMN_ORDERDETAIL_TOTALMONEY, totalmoney);
        long result = db.insert(ORDERDETAIL_TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
        }
    }
    void updateOrderDetail(String orderDetailId,String quantity, String totalmoney) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ORDERDETAIL_QUANTITY, quantity);
        cv.put(COLUMN_ORDERDETAIL_TOTALMONEY, totalmoney);
        long result = db.update(ORDERDETAIL_TABLE_NAME, cv, COLUMN_ORDERDETAIL_ID + "=?", new String[]{orderDetailId});
        if (result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
        }
    }
    public Cursor getOrderDetailsByOrderId(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ORDERDETAIL_TABLE_NAME + " WHERE " + COLUMN_ORDERDETAIL_ORDER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderId)});

        if (cursor != null && cursor.getCount() > 0) {
            return cursor;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }
    void deleteOrderDetail(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(ORDERDETAIL_TABLE_NAME, COLUMN_ORDERDETAIL_ID + "=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_LONG).show();
        }
    }
}
