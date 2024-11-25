package com.example.yummyfood.Adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yummyfood.R;

import java.util.ArrayList;

public class TableListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> tableList;
    private LayoutInflater inflater;

    public TableListAdapter(Context context, ArrayList<String> tableList) {
        this.context = context;
        this.tableList = tableList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tableList.size();
    }

    @Override
    public Object getItem(int position) {
        return tableList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_list_table_item, parent, false);
        }

        TextView tableName = convertView.findViewById(R.id.table1_name);
        tableName.setText(tableList.get(position));

        return convertView;
    }
}
