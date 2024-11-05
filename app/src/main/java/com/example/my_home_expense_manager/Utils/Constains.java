package com.example.my_home_expense_manager.Utils;

import com.example.my_home_expense_manager.Models.Category;
import com.example.my_home_expense_manager.R;

import java.util.ArrayList;

public class Constains {

    public static  String INCOME ="INCOME";
    public static  String EXPENSE ="EXPENSE";

    public static int SELECTED_TAB = 0;


    public static int DAILY = 0;
    public static int MONTHLY = 1;
    public static int CALENDAR = 2;
    public static int SUMMARY = 3;
    public static int NOTES = 4;





    public static ArrayList<Category> categories;

    public static void setCategory(){
         categories = new ArrayList<>();
        categories.add(new Category("Salary", R.drawable.ic_salary,R.color.category1));
        categories.add(new Category("Business",R.drawable.ic_business,R.color.category2));
        categories.add(new Category("Investments",R.drawable.ic_investment,R.color.category3));
        categories.add(new Category("Loan",R.drawable.ic_loan,R.color.category4));
        categories.add(new Category("Rent",R.drawable.ic_rent,R.color.category5));
        categories.add(new Category("Others",R.drawable.ic_other,R.color.category6));
    }

    public static Category getCategoryDeatils(String categoryName){
        for (Category cat: categories
             ) {
            if(cat.getCategory_name().equals(categoryName)){
                return cat;
            }
        }
        return null;
    }

    public static int getAccountColor(String accountName){
        int color = 0;
        switch (accountName){
            case "Bank":
                return R.color.bank_color;
            case "Cash":
                return R.color.cash_color;
            case "Card":
                return R.color.card_color;
            default:
                return R.color.defult_color;
        }
    }
}
