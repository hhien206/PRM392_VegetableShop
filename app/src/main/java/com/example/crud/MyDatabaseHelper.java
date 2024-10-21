package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

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

    //CREATE USERS DB
    private static final String USER_TABLE_NAME = "my_users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USERNAME = "user_name";
    private static final String COLUMN_PASSWORD = "user_password";
    private static final String COLUMN_EMAIL = "user_email";

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
                        COLUMN_ORIGINCOUNTRY + " TEXT);";
        db.execSQL(query);

        String queryUser =
                "CREATE TABLE " + USER_TABLE_NAME +
                        " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_USERNAME + " TEXT, " +
                        COLUMN_PASSWORD + " TEXT, " +
                        COLUMN_EMAIL + " TEXT);";
        db.execSQL(queryUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    //MANAGE VEGETABLE
    void addVegetable(String name, String category, String origincountry){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_ORIGINCOUNTRY, origincountry);
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
    void updateVegetable(String id, String name, String category, String origincountry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_ORIGINCOUNTRY, origincountry);
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

    // Kiểm tra đăng nhập
    boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " +
                        COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{username, password});

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
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
}
