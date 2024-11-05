package com.example.my_home_expense_manager.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.my_home_expense_manager.Adapters.transactionAdapter;
import com.example.my_home_expense_manager.DataBase;
import com.example.my_home_expense_manager.Models.Transaction;
import com.example.my_home_expense_manager.Utils.Constains;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainViewModel extends AndroidViewModel {



   public MutableLiveData<ArrayList<Transaction>> transactions = new MutableLiveData<>();
   public MutableLiveData<Double> totalIncome = new MutableLiveData<>();
   public MutableLiveData<Double> totalExpense = new MutableLiveData<>();
   public MutableLiveData<Double> totalAmount = new MutableLiveData<>();



    public MainViewModel(@NonNull Application application) {
        super(application);
    }


    DataBase database = new DataBase(this.getApplication());


    public void addTransaction(Transaction transaction) {
        database.addTransaction(transaction); // Save to database

    }

    public void deleteTransaction(long transactionId, Date selectedDate) {
        database.deleteTransaction(transactionId);
        getTransactions(selectedDate); // Delete the transaction
         // Refresh transactions for the selected date
    }

    public void getTransactions(Date date) {
        ArrayList<Transaction> newTransactions = null;
        if (Constains.SELECTED_TAB == Constains.MONTHLY) {
            newTransactions = database.getTransactionsByMonth(date); // Fetch by month
        } else if(Constains.SELECTED_TAB == Constains.DAILY) {
            newTransactions = database.getTransactionsByDate(date); // Fetch by date
        }
        transactions.setValue(newTransactions);
        calculateTotals(); // Update totals
    }




    public void calculateTotals() {
        double income = 0.0, expense = 0.0;
        for (Transaction transaction : transactions.getValue()) {
            if (transaction.getType().equals(Constains.INCOME)) {
                income += transaction.getAmount();
            } else if (transaction.getType().equals(Constains.EXPENSE)) {
                expense += transaction.getAmount();
            }
        }
        totalIncome.setValue(income);
        totalExpense.setValue(expense);
        totalAmount.setValue(income - expense); // Or other calculation
    }




}
