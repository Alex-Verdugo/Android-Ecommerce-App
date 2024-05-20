package com.example.project2alexanderverdugo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    private Button addItemButton;
    private Button modifyItemButton;
    private Button deletItemButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addItemButton = findViewById(R.id.Add_ItemButton);
        modifyItemButton = findViewById(R.id.modify_ItemButton);
        deletItemButton = findViewById(R.id.delete_ItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddItemActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        modifyItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminModifyViewActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });


        deletItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DeletItemActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });


    }





    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, AdminActivity.class);
        return intent;
    }
}