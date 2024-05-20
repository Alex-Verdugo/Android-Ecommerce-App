package com.example.project2alexanderverdugo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project2alexanderverdugo.db.AppDatabase;
import com.example.project2alexanderverdugo.db.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class SuppliesListActivity extends AppCompatActivity {
    ListView suppliesListView;
    List<Supplies> suppliesList = new ArrayList<Supplies>();

    User user;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies_list);

        this.userDAO = ((AppDatabase) Room.databaseBuilder(this, AppDatabase.class, "USER_DATABASE").allowMainThreadQueries().build()).getUserDAO();

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");

        user = userDAO.getUsersByUsername(username);

        suppliesListView = findViewById(R.id.suppliesListView_ModifyAdminViewActivity);

        createDefaultItems();

      ArrayAdapter<Supplies> adapter = new ArrayAdapter<Supplies>(SuppliesListActivity.this, android.R.layout.simple_list_item_1,suppliesList);
      suppliesListView.setAdapter(adapter);
      suppliesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              buyItem(position);

          }
      });
    }

    public void buyItem(int position){

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Would You like to purchase this Item?");
        alertBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if(suppliesList.get(position).getAmount()<=0){
                    Toast.makeText(SuppliesListActivity.this, " This Item is out of Stock", Toast.LENGTH_LONG).show();
                }else {

                    user.setOrders(user.getOrders()+ " \n " + " =============== " + " \n "+ suppliesList.get(position).toString1()+ " \n " + " =============== " + " \n ");
                    userDAO.update(user);

                    suppliesList.get(position).setAmount(suppliesList.get(position).getAmount()-1);
                    userDAO.update(suppliesList.get(position));

                    Toast.makeText(SuppliesListActivity.this, " Item Purchased Successfully", Toast.LENGTH_LONG).show();

//                    Intent intent = MainActivity.intentFactory(getApplicationContext(),user.getUserID());
//
//                    startActivity(intent);

                }

            }
        });
        alertBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBuilder.create().show();
    }


    public void createDefaultItems(){

        suppliesList = userDAO.getAllSupplies();

        if(suppliesList.size()<5){

            Supplies supplies1 = new Supplies("Explosive Breath Mints",200,8);
            Supplies supplies2 = new Supplies("Submarine Car",800000,3);
            Supplies supplies3 = new Supplies("X-Ray Glasses",5500,12);
            Supplies supplies4 = new Supplies("Voice Changer",700,9);

            userDAO.insert(supplies1);
            userDAO.insert(supplies2);
            userDAO.insert(supplies3);
            userDAO.insert(supplies4);



        }


        suppliesList = userDAO.getAllSupplies();


    }


    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, SuppliesListActivity.class);
        return intent;
    }

}