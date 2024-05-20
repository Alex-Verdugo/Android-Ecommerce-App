package com.example.project2alexanderverdugo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2alexanderverdugo.db.AppDatabase;
import com.example.project2alexanderverdugo.db.UserDAO;

import java.util.List;

public class ModifyItemActivity extends AppCompatActivity {


    private Button modifyItemButton;
    private EditText nameFeild;
    private EditText priceFeild;
    private EditText amountFeild;
    private String itemName ;
    private int itemPrice ;
    private int itemAmount ;
    private UserDAO userDAO;
    private List<Supplies> suppliesList;
    private Supplies item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item);

        this.userDAO = ((AppDatabase) Room.databaseBuilder(this, AppDatabase.class, "USER_DATABASE").allowMainThreadQueries().build()).getUserDAO();
        nameFeild = findViewById(R.id.ItemName_editText_DeleteItemActivity);
        priceFeild = findViewById(R.id.iteprice_editText_Modifyactivity);
        amountFeild = findViewById(R.id.itemAmount_editText_Modifyactivity2);

        modifyItemButton = findViewById(R.id.Modify_item_button_DeletItemActivity);

        modifyItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                checkForItem();
            }
        });


    }


    private void getValuesFromDisplay(){


        itemName = nameFeild.getText().toString();

        try{
            itemPrice = Integer.parseInt(priceFeild.getText().toString());
        }catch (NumberFormatException e){
            Log.d("ADD ITEM", "Couldn't covert Price");
        }

        try{
            itemAmount = Integer.parseInt(amountFeild.getText().toString());
        }catch (NumberFormatException e){
            Log.d("ADD ITEM", "Couldn't covert Amount");
        }


    }


    public void checkForItem(){
        suppliesList = userDAO.getAllSupplies();

        Intent intent = getIntent();
        String nameOfItem = intent.getStringExtra("ITEM-NAME");

        item = userDAO.getSuppliesByName(nameOfItem);

        item.setAmount(itemAmount);
        item.setItemName(itemName);
        item.setPrice(itemPrice);
        userDAO.update(item);

        Toast.makeText(ModifyItemActivity.this, " Item Modified Successfully", Toast.LENGTH_LONG).show();

        Intent intent1 = AdminActivity.intentFactory(getApplicationContext());
        startActivity(intent);

        return;


    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, ModifyItemActivity.class);
        return intent;
    }
}