package com.example.my_home_expense_manager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_home_expense_manager.Models.Account;
import com.example.my_home_expense_manager.R;
import com.example.my_home_expense_manager.databinding.RowAccountsBinding;

import java.util.ArrayList;



public class accountAdapter   extends  RecyclerView.Adapter<accountAdapter.ViewHolder> {


    public interface accounonClickedListner{
        void onAccountClicked(Account account);
    }

    accounonClickedListner accounonClickedListner;
    Context context;
    ArrayList<Account> accountArrayList;

    public accountAdapter(Context context, ArrayList<Account> accountArrayList, accounonClickedListner accounonClickedListner) {
        this.context = context;
        this.accountArrayList = accountArrayList;
        this.accounonClickedListner = accounonClickedListner;
    }



    @NonNull
    @Override
    public accountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_accounts,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull accountAdapter.ViewHolder holder, int position) {
        Account account = accountArrayList.get(position);
        holder.binding.accounts.setText(account.getAccountName());
        holder.itemView.setOnClickListener(v -> {
            accounonClickedListner.onAccountClicked(account);
        });

    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RowAccountsBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowAccountsBinding.bind(itemView);

        }
    }
}