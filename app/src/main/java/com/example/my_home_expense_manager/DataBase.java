package com.example.my_home_expense_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.my_home_expense_manager.Models.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ExpenseManager.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String COL_ID = "id";
    private static final String COL_TYPE = "type";
    private static final String COL_CATEGORY = "category";
    private static final String COL_ACCOUNT = "account";
    private static final String COL_NOTE = "note";
    private static final String COL_DATE = "date";
    private static final String COL_AMOUNT = "amount";
    private static final String COL_TIMESTAMP = "timestamp";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_TRANSACTIONS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TYPE + " TEXT, " +
                COL_CATEGORY + " TEXT, " +
                COL_ACCOUNT + " TEXT, " +
                COL_NOTE + " TEXT, " +
                COL_DATE + " INTEGER, " +
                COL_AMOUNT + " REAL, " +
                COL_TIMESTAMP + " INTEGER)"; // Add timestamp column
        db.execSQL(createTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    // Method to add a transaction to the database
    // Method to add a transaction to the database
    public void addTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_TYPE, transaction.getType());
            values.put(COL_CATEGORY, transaction.getCategory());
            values.put(COL_ACCOUNT, transaction.getAccount());
            values.put(COL_NOTE, transaction.getNote());
            values.put(COL_DATE, transaction.getDate().getTime()); // Store date as timestamp
            values.put(COL_AMOUNT, transaction.getAmount());
            values.put(COL_TIMESTAMP, new Date().getTime()); // Add a timestamp field for the current time
            db.insert(TABLE_TRANSACTIONS, null, values);
        } finally {
            db.close();
        }
    }

    // Method to fetch all transactions from the database
    public ArrayList<Transaction> getTransactionsByDate(Date date) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        // Start and end of the selected date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startOfDay = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long endOfDay = calendar.getTimeInMillis();

        try {
            cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE " + COL_DATE + " >= ? AND " + COL_DATE + " < ?",
                    new String[]{String.valueOf(startOfDay), String.valueOf(endOfDay)}
            );

            if (cursor.moveToFirst()) {
                do {
                    Transaction transaction = new Transaction(
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_ACCOUNT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTE)),
                            new Date(cursor.getLong(cursor.getColumnIndexOrThrow(COL_DATE))),
                            cursor.getDouble(cursor.getColumnIndexOrThrow(COL_AMOUNT)),
                            cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID))
                    );
                    transactions.add(transaction);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return transactions;
    }

    public ArrayList<Transaction> getTransactionsByMonth(Date date) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long startOfMonth = calendar.getTimeInMillis();

        calendar.add(Calendar.MONTH, 1);
        long endOfMonth = calendar.getTimeInMillis();

        try {
            cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE " + COL_DATE + " >= ? AND " + COL_DATE + " < ?",
                    new String[]{String.valueOf(startOfMonth), String.valueOf(endOfMonth)}
            );

            if (cursor.moveToFirst()) {
                do {
                    Transaction transaction = new Transaction(
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_ACCOUNT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTE)),
                            new Date(cursor.getLong(cursor.getColumnIndexOrThrow(COL_DATE))),
                            cursor.getDouble(cursor.getColumnIndexOrThrow(COL_AMOUNT)),
                            cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID))
                    );
                    transactions.add(transaction);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return transactions;
    }


    public void deleteTransaction(long transactionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            db.delete(TABLE_TRANSACTIONS, COL_ID + " = ?", new String[]{String.valueOf(transactionId)});
        } finally {
            db.close();
        }
    }


}
