package com.example.yummyfood.Fragment;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Fragment.Voucher.Voucher;
import com.example.yummyfood.Fragment.Voucher.VoucherAdapter;
import com.example.yummyfood.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivityVoucher extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VoucherAdapter adapter;
    private List<Voucher> voucherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_user);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        voucherList = new ArrayList<>();
        voucherList.add(new Voucher("Giảm tối đa 10k", "Đơn tối thiểu 0đ"));
        voucherList.add(new Voucher("Giảm tối đa 10k", "Đơn tối thiểu 0đ"));
        voucherList.add(new Voucher("Giảm tối đa 10k", "Đơn tối thiểu 0đ"));
        voucherList.add(new Voucher("Giảm tối đa 10k", "Đơn tối thiểu 0đ"));
        voucherList.add(new Voucher("Giảm tối đa 10k", "Đơn tối thiểu 0đ"));
        voucherList.add(new Voucher("Giảm tối đa 10k", "Đơn tối thiểu 0đ"));



        adapter = new VoucherAdapter(this, voucherList);
        recyclerView.setAdapter(adapter);
    }
}