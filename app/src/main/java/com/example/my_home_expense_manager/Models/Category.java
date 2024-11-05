package com.example.my_home_expense_manager.Models;

public class Category {

    private String Category_name;
    private  int Category_icon;
    private int category_color;

    public Category(String category_name, int category_icon, int category_color) {
        Category_name = category_name;
        Category_icon = category_icon;
        this.category_color = category_color;
    }

    public String getCategory_name() {
        return Category_name;
    }

    public void setCategory_name(String category_name) {
        Category_name = category_name;
    }

    public int getCategory_icon() {
        return Category_icon;
    }

    public void setCategory_icon(int category_icon) {
        Category_icon = category_icon;
    }

    public int getCategory_color() {
        return category_color;
    }

    public void setCategory_color(int category_color) {
        this.category_color = category_color;
    }
}
