package com.example.project2alexanderverdugo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2alexanderverdugo.db.AppDatabase;
import com.example.project2alexanderverdugo.db.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class DeletItemActivity extends AppCompatActivity {

    EditText itemNameFeild;
    String itemname;
    Button deletItemButton;
    UserDAO userDAO;
    Supplies item;
    List<Supplies> suppliesList = new ArrayList<Supplies>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delet_item);

        this.userDAO = ((AppDatabase) Room.databaseBuilder(this, AppDatabase.class, "USER_DATABASE")
                .allowMainThreadQueries()
                .build())
                .getUserDAO();
        itemNameFeild = findViewById(R.id.ItemName_editText_DeleteItemActivity);
        itemname = itemNameFeild.getText().toString();

        deletItemButton = findViewById(R.id.Modify_item_button_DeletItemActivity);



        deletItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForItem();

            }
        });




    }




    public void checkForItem(){
        suppliesList = userDAO.getAllSupplies();
        itemname = itemNameFeild.getText().toString();


            if (suppliesList.contains(userDAO.getSuppliesByName(itemname))) {

                Toast.makeText(DeletItemActivity.this, " Item Does not Exist", Toast.LENGTH_LONG).show();
                return;
            }


        item = userDAO.getSuppliesByName(itemname);
        userDAO.delete(item);
        Toast.makeText(DeletItemActivity.this, " Item Deleted Successfully", Toast.LENGTH_LONG).show();

        Intent intent = AdminActivity.intentFactory(getApplicationContext());
        startActivity(intent);

        return;


    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, DeletItemActivity.class);
        return intent;
    }
}