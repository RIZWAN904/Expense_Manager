package com.example.my_home_expense_manager.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    public static String formateDate(Date date){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/YYYY");
        String dateFormate = simpleDateFormat.format(date);
        return  dateFormate;
    }

    public static String formateDateByMonth(Date date){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM/YYYY");
        String dateFormate = simpleDateFormat.format(date);
        return  dateFormate;
    }
}
