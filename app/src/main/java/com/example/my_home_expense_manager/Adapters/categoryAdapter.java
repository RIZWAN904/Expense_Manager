package com.example.my_home_expense_manager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_home_expense_manager.Models.Category;
import com.example.my_home_expense_manager.R;
import com.example.my_home_expense_manager.databinding.CategorySimpleLayoutBinding;

import java.util.ArrayList;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.CategoryViewHolder> {


    Context context;
    ArrayList<Category> categoryArrayList;

    public interface categoryOnClickListner{
        void categoryOnClick(Category category);
    }

    categoryOnClickListner categoryOnClickListner;

    public categoryAdapter(Context context, ArrayList<Category> categoryArrayList,categoryOnClickListner categoryOnClickListner) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.categoryOnClickListner = categoryOnClickListner;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_simple_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category category = categoryArrayList.get(position);
        holder.binding.categoryText.setText(category.getCategory_name());
      holder.binding.categoryIcon.setImageResource(category.getCategory_icon());
      holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(category.getCategory_color()));
      holder.itemView.setOnClickListener(v -> {
          categoryOnClickListner.categoryOnClick(category);
      });

    }

    @Override
    public int getItemCount() {

        return categoryArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        CategorySimpleLayoutBinding binding;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CategorySimpleLayoutBinding.bind(itemView);

        }
    }
}