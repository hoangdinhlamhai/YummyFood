package com.example.yummyfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.R;

import java.util.List;
import java.util.Map;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private final Context context;
    private final List<Map<String, Object>> reviews;

    public ReviewAdapter(Context context, List<Map<String, Object>> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Map<String, Object> review = reviews.get(position);

        // Kiểm tra và gán mô tả đánh giá
        String description = review.get("danhGia") != null ? review.get("danhGia").toString() : "Không có đánh giá";
        holder.tvDescription.setText(description);

        // Kiểm tra và gán số sao đánh giá
        if (review.get("soSao") != null) {
            try {
                holder.ratingBar.setRating(Float.parseFloat(review.get("soSao").toString()));
            } catch (NumberFormatException e) {
                holder.ratingBar.setRating(0); // Gán mặc định là 0 nếu có lỗi
            }
        } else {
            holder.ratingBar.setRating(0); // Gán mặc định là 0 nếu giá trị null
        }

        // Kiểm tra và gán ngày đánh giá
        String date = review.get("ngayDanhGia") != null ? review.get("ngayDanhGia").toString() : "Không có ngày";
        holder.tvDate.setText("Ngày đánh giá: " + date);
    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvDate;
        RatingBar ratingBar;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvShortDescription);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
