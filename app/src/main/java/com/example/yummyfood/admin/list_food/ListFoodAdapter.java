package com.example.yummyfood.admin.list_food;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.yummyfood.R;
import com.example.yummyfood.admin.Edit_Food;
import com.example.yummyfood.admin.model.Food;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class ListFoodAdapter extends ArrayAdapter<Food> {

    private Context context;
    private int resource;
    private List<Food> arrFood;

    public ListFoodAdapter(@NonNull Context context, int resource, ArrayList<Food> arrFood) {
        super(context, resource, arrFood);
        this.context = context;
        this.resource = resource;
        this.arrFood = arrFood;
    }

    public class ViewHolder {
        TextView tvName, tvId, tvImage, tvPrice;
        ShapeableImageView listImage;
        ImageView btnEdit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvId = (TextView) convertView.findViewById(R.id.tvId);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            viewHolder.listImage = convertView.findViewById(R.id.listImage);
            viewHolder.btnEdit = convertView.findViewById(R.id.btnEdit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Food f = arrFood.get(position);
        viewHolder.tvId.setText("ID: " + String.valueOf(f.getId()));
        viewHolder.tvName.setText(f.getName());
        viewHolder.tvPrice.setText(f.getPrice() + " đ");
        viewHolder.listImage.setImageResource(f.getImage());

        //edit food
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Edit_Food.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }


}
