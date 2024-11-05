package com.example.crud.model;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class Vegetable {
    private int id;
    private String name;
    private String category;
    private String originCountry;
    private Double price;

    public Vegetable() {
    }

    public Vegetable(int id, String name, String category, String originCountry, Double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.originCountry = originCountry;
        this.price = price;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public static Vegetable ConvertCursorIntoVegetable(Cursor cursor) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idColumnIndex = cursor.getColumnIndex("id");
                int nameColumnIndex = cursor.getColumnIndex("vegetable_name");
                int categoryColumnIndex = cursor.getColumnIndex("vegetable_category");
                int originCountryColumnIndex = cursor.getColumnIndex("vegetable_origincountry");
                int priceColumnIndex = cursor.getColumnIndex("vegetable_price");
                if (idColumnIndex != -1 && nameColumnIndex != -1 && categoryColumnIndex != -1 && originCountryColumnIndex != -1 && priceColumnIndex != -1) {
                    int id = cursor.getInt(idColumnIndex);
                    String name = cursor.getString(nameColumnIndex);
                    String category = cursor.getString(categoryColumnIndex);
                    String originCountry = cursor.getString(originCountryColumnIndex);
                    double price = cursor.getDouble(priceColumnIndex);
                    return new Vegetable(id, name, category, originCountry, price);
                }
            }
        }
        return null;
    }

    public static List<Vegetable> ConvertCursorIntoListVegetable(Cursor cursor) {
        if (cursor != null) {
            List<Vegetable> vegetables = new ArrayList<>();

            while (cursor.moveToNext()) {
                Vegetable vegetable = new Vegetable();
                vegetable = ConvertCursor(cursor);
                if (vegetable != null) {
                    vegetables.add(vegetable);
                }
            }
            cursor.close();
            return vegetables;
        }
        return null;
    }

    private static Vegetable ConvertCursor(Cursor cursor) {
        if (cursor != null) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int nameColumnIndex = cursor.getColumnIndex("vegetable_name");
            int categoryColumnIndex = cursor.getColumnIndex("vegetable_category");
            int originCountryColumnIndex = cursor.getColumnIndex("vegetable_origincountry");
            int priceColumnIndex = cursor.getColumnIndex("vegetable_price");
            if (idColumnIndex != -1 && nameColumnIndex != -1 && categoryColumnIndex != -1 && originCountryColumnIndex != -1 && priceColumnIndex != -1) {
                int id = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                String category = cursor.getString(categoryColumnIndex);
                String originCountry = cursor.getString(originCountryColumnIndex);
                double price = cursor.getDouble(priceColumnIndex);
                return new Vegetable(id, name, category, originCountry, price);
            }
        }
        return null;
    }
}
