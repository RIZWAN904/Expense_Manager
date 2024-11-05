package com.example.my_home_expense_manager.views.Activities;

import static java.security.AccessController.getContext;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.my_home_expense_manager.Adapters.transactionAdapter;
import com.example.my_home_expense_manager.DataBase;
import com.example.my_home_expense_manager.Models.Transaction;
import com.example.my_home_expense_manager.R;
import com.example.my_home_expense_manager.Utils.Constains;
import com.example.my_home_expense_manager.Utils.Helper;
import com.example.my_home_expense_manager.ViewModel.MainViewModel;
import com.example.my_home_expense_manager.databinding.ActivityMainBinding;
import com.example.my_home_expense_manager.views.Fargments.AddTransactions_Fargment;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
  ActivityMainBinding binding;
  Calendar calendar;
    int selectedtab = 0;
 public MainViewModel ViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("Transactions");


        Constains.setCategory();
        calendar = Calendar.getInstance();
        updateDate();

        binding.nextDate.setOnClickListener(v -> {
            if(Constains.SELECTED_TAB == Constains.DAILY){
                calendar.add(Calendar.DATE, 1);
            } else if (Constains.SELECTED_TAB == Constains.MONTHLY) {
                calendar.add(Calendar.MONTH, 1);
            }
            updateDate();
        });

        binding.currentDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this);
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,view.getDayOfMonth());
                calendar.set(Calendar.MONTH,view.getMonth());
                calendar.set(Calendar.YEAR,view.getYear());


                String datetoshow = Helper.formateDate(calendar.getTime());
                binding.currentDate.setText(datetoshow);
            });
            datePickerDialog.show();
        });

        binding.previseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constains.SELECTED_TAB == Constains.DAILY){
                    calendar.add(Calendar.DATE, -1);
                } else if (Constains.SELECTED_TAB == Constains.MONTHLY) {
                    calendar.add(Calendar.MONTH, -1);
                }
                updateDate();

            }
        });



        binding.floatingActionButton.setOnClickListener(v ->
                new AddTransactions_Fargment().show(getSupportFragmentManager(), null));


//        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
//        transactionArrayList.add(new Transaction(Constains.INCOME,"Business","Cash","some note here",new Date(),1000,new Date().getTime()));
//        transactionArrayList.add(new Transaction(Constains.EXPENSE,"Investments","Bank","some note here",new Date(),1000,4));
//        transactionArrayList.add(new Transaction(Constains.INCOME,"Others","Cash","some note here",new Date(),8000,5));
//        transactionArrayList.add(new Transaction(Constains.EXPENSE,"Business","Cash","some note here",new Date(),-2000,6));
//        transactionArrayList.add(new Transaction(Constains.INCOME,"Business","Bank","some note here",new Date(),9000,8));
//        transactionArrayList.add(new Transaction(Constains.EXPENSE,"Rent","Cash","some note here",new Date(),6000,8));
//        transactionArrayList.add(new Transaction(Constains.INCOME,"Loan","Cash","some note here",new Date(),4000,9));
//        transactionArrayList.add(new Transaction(Constains.INCOME,"Loan","Bank","some note here",new Date(),4000,9));
//        transactionArrayList.add(new Transaction(Constains.INCOME,"Loan","Cash","some note here",new Date(),4000,9));
//        transactionArrayList.add(new Transaction(Constains.INCOME,"Loan","Cash","some note here",new Date(),4000,9));
//        transactionArrayList.add(new Transaction(Constains.INCOME,"Loan","Card","some note here",new Date(),4000,9));
//        transactionArrayList.add(new Transaction(Constains.INCOME,"Loan","Paypal","some note here",new Date(),4000,9));
//        transactionArrayList.add(new Transaction(Constains.INCOME,"Loan","Card","some note here",new Date(),4000,9));
//        transactionArrayList.add(new Transaction(Constains.INCOME,"Loan","Bank","some note here",new Date(),4000,9));

//
//
     //  DataBase database = new DataBase(this);

   //     for (Transaction transaction : transactionArrayList) {
       //     database.addTransaction(transaction);
     //   }

        // Fetch data from the database and display it
      //  ArrayList<Transaction> transactionsFromDb = database.getAllTransactions();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ViewModel.transactions.observe(this, new Observer<ArrayList<Transaction>>() {
            @Override
            public void onChanged(ArrayList<Transaction> transactions) {
                transactionAdapter transactionAdapter = new transactionAdapter(MainActivity.this,transactions);
                binding.recyclerView.setAdapter(transactionAdapter);
                if(transactions.size() > 0){
                    binding.emptyIamge.setVisibility(View.GONE);

                }
                else {
                    binding.emptyIamge.setVisibility(View.VISIBLE);
                }
            }
        });
        ViewModel.getTransactions(new Date());

        ViewModel.totalIncome.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomeAmount.setText(String.valueOf(aDouble));
            }
        });

        ViewModel.totalExpense.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenseamount.setText(String.valueOf(aDouble));
            }
        });
ViewModel.totalAmount.observe(this, new Observer<Double>() {
    @Override
    public void onChanged(Double aDouble) {
        binding.totalamount.setText(String.valueOf(aDouble));
    }
});

ViewModel.calculateTotals();



// Daily = 0
// Monthly = 1
// Calendar = 2
// summary =3
// notes = 4


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Daliy")) {
                    Constains.SELECTED_TAB = Constains.DAILY;
                } else if (tab.getText().equals("Monthly")) {
                    Constains.SELECTED_TAB = Constains.MONTHLY;
                }

                else if (tab.getText().equals("Calendar")) {
                    Constains.SELECTED_TAB = Constains.CALENDAR;


                }

                updateDate();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });




    }

    public Date getSelectedDate() {
        return calendar.getTime();
    }

    void updateDate() {
        Date selectedDate = calendar.getTime();
        if (Constains.SELECTED_TAB == Constains.MONTHLY) {
            binding.currentDate.setText(Helper.formateDateByMonth(selectedDate));
        } else if(Constains.SELECTED_TAB == Constains.DAILY) {
            binding.currentDate.setText(Helper.formateDate(selectedDate));
        }
        ViewModel.getTransactions(selectedDate);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void getTransactions(){
        ViewModel.getTransactions(new Date());
    }
}