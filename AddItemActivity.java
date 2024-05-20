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

public class AddItemActivity extends AppCompatActivity {

    private Button additemButton;
    private EditText nameFeild;
    private EditText priceFeild;
    private EditText amountFeild;
    private String itemName;
    private int itemPrice;
    private int itemAmount;
    private UserDAO userDAO;
    private List<Supplies> suppliesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        this.userDAO = ((AppDatabase) Room.databaseBuilder(this, AppDatabase.class, "USER_DATABASE").allowMainThreadQueries().build()).getUserDAO();

        additemButton = findViewById(R.id.Modify_item_button_DeletItemActivity);
        nameFeild = findViewById(R.id.ItemName_editText_DeleteItemActivity);
        priceFeild = findViewById(R.id.itemPrice_editText);
        amountFeild = findViewById(R.id.iteprice_editText_Modifyactivity);




        additemButton.setOnClickListener(new View.OnClickListener() {
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

        for (Supplies supplies : suppliesList) {

            if (itemName.equals(supplies.getItemName())) {

                Toast.makeText(AddItemActivity.this, " Item Already in Store", Toast.LENGTH_LONG).show();
                return;
            }
        }

                Supplies item = new Supplies(itemName,itemPrice,itemAmount);
                userDAO.insert(item);
                Toast.makeText(AddItemActivity.this, " Item added Successfully", Toast.LENGTH_LONG).show();

               return;


    }



    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, AddItemActivity.class);
        return intent;
    }
}