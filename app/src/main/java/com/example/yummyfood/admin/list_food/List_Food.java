package com.example.yummyfood.admin.list_food;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yummyfood.R;
import com.example.yummyfood.admin.model.Food;

import java.util.ArrayList;

public class List_Food extends AppCompatActivity {
    private ListView lv;
//    ActivityListFoodBinding binding;
//    ListFoodAdapter listAdapter;
//    Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

//        binding = ActivityListFoodBinding.inflate(getLayoutInflater());

        setContentView(R.layout.activity_list_food);

        lv = (ListView)findViewById(R.id.listview);

        ArrayList<Food> arrFood = new ArrayList<>();

        Food f1 = new Food(1, "food1", "ngon", R.drawable.food1, 1000);
        arrFood.add(f1);
        ListFoodAdapter listFoodAdapter = new ListFoodAdapter(this, R.layout.item_food, arrFood);
        lv.setAdapter(listFoodAdapter);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        int[] imageList = {R.drawable.food1, R.drawable.food1, R.drawable.food1};
//        String[] nameList = {"Food1", "Food2", "Food3"};
//        int[] idList = {1, 2, 3};
//        int[] priceList = {1000, 2000, 3000};
//        String[] shortDescList = {"1111", "2222", "3333"};
//
//        for (int i = 0; i < imageList.length; i++){
//            food = new Food(idList[i], nameList[i], shortDescList[i] , imageList[i], priceList[i]);
//            dataArrayList.add(food);
//        }
//
//        listAdapter = new ListFoodAdapter(List_Food.this, dataArrayList);
//        binding.listview.setAdapter(listAdapter);
//        binding.listview.setClickable(true);

    }
}