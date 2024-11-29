package com.example.yummyfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummyfood.Domain.Food;
import com.example.yummyfood.FoodDetailActivity;
import com.example.yummyfood.R;

import java.util.List;

public class  ListFoodAdapter extends RecyclerView.Adapter<ListFoodAdapter.FoodViewHolder> {

    private Context context;
    private List<Food> foodList;

    public ListFoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.tvFoodName.setText(food.getName());
        holder.tvFoodPrice.setText(food.getPrice() + "đ");

        // Load hình ảnh
        Glide.with(context).load(food.getImage()).into(holder.ivFoodImage);

        // Thêm sự kiện click
        holder.itemView.setOnClickListener(v -> {
            // Tạo Intent để chuyển sang Activity chi tiết món ăn
            Intent intent = new Intent(context, FoodDetailActivity.class);

            // Truyền idMonAn qua Intent
            intent.putExtra("idMonAn", food.getId());

            // Khởi động Activity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvFoodPrice;
        ImageView ivFoodImage;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.foodName);
            tvFoodPrice = itemView.findViewById(R.id.foodPrice);
            ivFoodImage = itemView.findViewById(R.id.foodImg);
        }
    }
}
