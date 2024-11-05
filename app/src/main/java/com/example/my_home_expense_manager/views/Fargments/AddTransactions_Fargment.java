package com.example.my_home_expense_manager.views.Fargments;

import static com.example.my_home_expense_manager.Utils.Constains.categories;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.my_home_expense_manager.Adapters.accountAdapter;
import com.example.my_home_expense_manager.Adapters.categoryAdapter;
import com.example.my_home_expense_manager.Models.Account;
import com.example.my_home_expense_manager.Models.Category;
import com.example.my_home_expense_manager.Models.Transaction;
import com.example.my_home_expense_manager.R;
import com.example.my_home_expense_manager.Utils.Constains;
import com.example.my_home_expense_manager.databinding.FragmentAddTransactionsFargmentBinding;
import com.example.my_home_expense_manager.databinding.ListDialogBinding;
import com.example.my_home_expense_manager.views.Activities.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AddTransactions_Fargment extends BottomSheetDialogFragment {



    public AddTransactions_Fargment() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAddTransactionsFargmentBinding binding;
    Transaction transaction;

    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTransactionsFargmentBinding.inflate(inflater);
        transaction = new Transaction();

        binding.IcomeButton.setOnClickListener(v -> {
            binding.IcomeButton.setBackgroundResource(R.drawable.icome_selector);
            binding.expenseButton.setBackgroundResource(R.drawable.defult_selector);
            binding.expenseButton.setTextColor(getContext().getColor(R.color.defultcolorbutton));
            binding.IcomeButton.setTextColor(getContext().getColor(R.color.Green));
            transaction.setType(Constains.INCOME);
        });

        binding.expenseButton.setOnClickListener(v -> {
            binding.expenseButton.setBackgroundResource(R.drawable.expense_selector);

            binding.IcomeButton.setBackgroundResource(R.drawable.defult_selector);
            binding.IcomeButton.setTextColor(getContext().getColor(R.color.defultcolorbutton));
            binding.expenseButton.setTextColor(getContext().getColor(R.color.Red));
            transaction.setType(Constains.EXPENSE);

        });

        binding.date.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,view.getDayOfMonth());
                calendar.set(Calendar.MONTH,view.getMonth());
                calendar.set(Calendar.YEAR,view.getYear());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/YYYY");
                String datetoshow = simpleDateFormat.format(calendar.getTime());
                binding.date.setText(datetoshow);

                transaction.setDate(calendar.getTime());
                transaction.setId(calendar.getTime().getTime());
            });
            datePickerDialog.show();
        });

        binding.category.setOnClickListener(v -> {

            ListDialogBinding listDialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog categorydialog = new AlertDialog.Builder(getContext()).create();
            categorydialog.setView(listDialogBinding.getRoot());




            categoryAdapter cAdapter = new categoryAdapter(getContext(), categories, category -> {

                binding.category.setText(category.getCategory_name());
                transaction.setCategory(category.getCategory_name());
                categorydialog.dismiss();
            });
            listDialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
            listDialogBinding.recyclerView.setAdapter(cAdapter);
            categorydialog.show();

        });

        binding.acount.setOnClickListener(v -> {
            ListDialogBinding listDialogBinding = ListDialogBinding.inflate(inflater);

            AlertDialog accountdialog = new AlertDialog.Builder(getContext()).create();
            accountdialog.setView(listDialogBinding.getRoot());
            ArrayList<Account> accounts = new ArrayList<>();
            accounts.add(new Account(0,"Cash"));
            accounts.add(new Account(0,"Bank"));
            accounts.add(new Account(0,"Credit Card"));
            accounts.add(new Account(0,"Paypal"));
            accounts.add(new Account(0,"Bitcoin"));


            accountAdapter aAdapter = new accountAdapter(getContext(), accounts, account -> {
                binding.acount.setText(account.getAccountName());
                transaction.setAccount(account.getAccountName());
                accountdialog.dismiss();
            });
            listDialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            listDialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            listDialogBinding.recyclerView.setAdapter(aAdapter);
            accountdialog.show();

        });

        binding.saveButton.setOnClickListener(v -> {
            double amount = Double.parseDouble(binding.amount.getText().toString());
            String note = binding.note.getText().toString();
            if(transaction.getType().equals(Constains.EXPENSE)){
                transaction.setAmount(amount*-1);
            }
            else {
            transaction.setAmount(amount);
            }
            transaction.setNote(note);

          ((MainActivity)getActivity()).ViewModel.addTransaction(transaction);
          ((MainActivity)getActivity()).getTransactions();


          dismiss();
        });

        return binding.getRoot();
    }
}