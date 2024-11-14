package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class edit_address extends AppCompatActivity {

    private EditText editTextName, editTextPhone, editTextAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_address);

        //back to homepage
        Button btnBack = findViewById(R.id.appCompatButton4);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edit_address.this, ActivityPaymentUser.class);
                startActivity(intent);
//                finish();
            }
        });

        Button btnSave = findViewById(R.id.btn_save_review2);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edit_address.this, ActivityPaymentUser.class);
                startActivity(intent);
//                finish();
            }
        });

        editTextName = findViewById(R.id.name_edit_text);
        editTextPhone = findViewById(R.id.phone_number_edit_text);
        editTextAddress = findViewById(R.id.address_edit_text);

        Button btnHuy = findViewById(R.id.btn_delete_address);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextName.setText("");
                editTextPhone.setText("");
                editTextAddress.setText("");
            }
        });
    }
}