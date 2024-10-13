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

        Food f1 = new Food(1, "Name", "ngon", R.drawable.food1, 1000);
        Food f2 = new Food(2, "Name", "ngon", R.drawable.food1, 1000);
        Food f3 = new Food(3, "Name", "ngon", R.drawable.food1, 1000);
        Food f4 = new Food(4, "Name", "ngon", R.drawable.food1, 1000);
        Food f5 = new Food(5, "Name", "ngon", R.drawable.food1, 1000);
        Food f6 = new Food(6, "Name", "ngon", R.drawable.food1, 1000);
        Food f7 = new Food(7, "Name", "ngon", R.drawable.food1, 1000);

        arrFood.add(f1);
        arrFood.add(f2);
        arrFood.add(f3);
        arrFood.add(f4);
        arrFood.add(f5);
        arrFood.add(f6);
        arrFood.add(f7);

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