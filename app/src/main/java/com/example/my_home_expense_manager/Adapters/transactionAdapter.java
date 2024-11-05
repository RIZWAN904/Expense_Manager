package com.example.my_home_expense_manager.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_home_expense_manager.Models.Category;
import com.example.my_home_expense_manager.Models.Transaction;
import com.example.my_home_expense_manager.R;
import com.example.my_home_expense_manager.Utils.Constains;
import com.example.my_home_expense_manager.Utils.Helper;
import com.example.my_home_expense_manager.databinding.RowTrasactionsBinding;
import com.example.my_home_expense_manager.views.Activities.MainActivity;

import java.util.ArrayList;
import java.util.Date;


public class transactionAdapter extends RecyclerView.Adapter<transactionAdapter.transactionViewHolder> {

    Context context;
    ArrayList<Transaction> transactionArrayList;

    public transactionAdapter() {
    }

    public transactionAdapter(Context context, ArrayList<Transaction> transactionArrayList) {
        this.context = context;
        this.transactionArrayList = transactionArrayList;
    }

    @NonNull
    @Override
    public transactionAdapter.transactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new transactionViewHolder(LayoutInflater.from(context).inflate(R.layout.row_trasactions,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull transactionAdapter.transactionViewHolder holder, int position) {

        Transaction transaction = transactionArrayList.get(position);

        holder.binding.amount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.acountLabel.setText(transaction.getAccount());
        holder.binding.transactionCategory.setText(transaction.getCategory());
        holder.binding.transactionDate.setText(Helper.formateDate(transaction.getDate()));

        Category transactionCategory = Constains.getCategoryDeatils(transaction.getCategory());

        holder.binding.category.setImageResource(transactionCategory.getCategory_icon());
       holder.binding.category.setBackgroundTintList(context.getColorStateList(transactionCategory.getCategory_color()));

       holder.binding.acountLabel.setBackgroundTintList(context.getColorStateList(Constains.getAccountColor(transaction.getAccount())));


        if(transaction.getType().equals(Constains.EXPENSE)){
            holder.binding.amount.setTextColor(context.getColor(R.color.Red));
        }else{
            holder.binding.amount.setTextColor(context.getColor(R.color.Green));
        }

        holder.itemView.setOnLongClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Delete Transaction");
            alertDialog.setMessage("Are you sure you want to delete this transaction?");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialog, which) -> {
                Date selectedDate = ((MainActivity) context).getSelectedDate(); // Get the selected date
                ((MainActivity) context).ViewModel.deleteTransaction(transaction.getId(), selectedDate); // Pass date to ViewModel
            });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return false;
        });


    }

    @Override
    public int getItemCount() {
        return transactionArrayList.size();
    }

    public class transactionViewHolder extends RecyclerView.ViewHolder {

        RowTrasactionsBinding binding;
        public transactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowTrasactionsBinding.bind(itemView);
        }
    }

}