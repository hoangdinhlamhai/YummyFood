package com.example.yummyfood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.ListReviewAdapter;
import com.example.yummyfood.Domain.Review;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailActivity extends AppCompatActivity {

    TextView tvFoodName, tvFoodPrice, tvFoodDescription;
    ImageView ivFoodImage;
    RecyclerView rvReviews;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail_user);

        // Ánh xạ view
        tvFoodName = findViewById(R.id.foodDetailName);
        tvFoodPrice = findViewById(R.id.foodDetailPrice);
        ivFoodImage = findViewById(R.id.foodDetailImg);
        tvFoodDescription = findViewById(R.id.foodDetailDescription);
        rvReviews = findViewById(R.id.rvReviews);

        // Mở cơ sở dữ liệu
        database = openOrCreateDatabase("dbYummyFood.db", MODE_PRIVATE, null);

        // Lấy ID món ăn từ Intent
        Intent intent = getIntent();
        int foodId = intent.getIntExtra("food_id", -1); // Truyền food_id khi gọi Intent

        if (foodId != -1) {
            // Lấy thông tin món ăn
            loadFoodDetail(foodId);

            // Lấy danh sách đánh giá theo id món ăn
            List<Review> reviews = loadReviews(foodId);
            setupReviewsRecyclerView(reviews);
        }
    }

    private void loadFoodDetail(int foodId) {
        Cursor cursor = database.query("MonAn",
                new String[]{"tenMonAn", "donGia", "moTa", "hinhAnh"},
                "idMonAn = ?",
                new String[]{String.valueOf(foodId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String foodName = cursor.getString(cursor.getColumnIndex("tenMonAn"));
            @SuppressLint("Range") int foodPrice = cursor.getInt(cursor.getColumnIndex("donGia"));
            @SuppressLint("Range") String foodDesc = cursor.getString(cursor.getColumnIndex("moTa"));
            @SuppressLint("Range") byte[] foodImage = cursor.getBlob(cursor.getColumnIndex("hinhAnh"));

            tvFoodName.setText(foodName);
            tvFoodPrice.setText(foodPrice + " đ");
            tvFoodDescription.setText(foodDesc);

            if (foodImage != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
                ivFoodImage.setImageBitmap(bitmap);
            }

            cursor.close();
        }
    }

    private List<Review> loadReviews(int foodId) {
        List<Review> reviews = new ArrayList<>();
        Cursor cursor = database.query("DanhGia",
                new String[]{"danhGia"},
                "idMonAn = ?",
                new String[]{String.valueOf(foodId)},
                null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex("danhGia"));

                reviews.add(new Review(comment));
            }
            cursor.close();
        }

        return reviews;
    }

    private void setupReviewsRecyclerView(List<Review> reviews) {
        ListReviewAdapter adapter = new ListReviewAdapter(this, reviews);
        rvReviews.setLayoutManager(new LinearLayoutManager(this));
        rvReviews.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}
