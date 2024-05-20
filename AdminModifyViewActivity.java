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

public class AdminModifyViewActivity extends AppCompatActivity {
    ListView suppliesListView;
    List<Supplies> suppliesList = new ArrayList<Supplies>();

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modify_view);

        this.userDAO = ((AppDatabase) Room.databaseBuilder(this, AppDatabase.class, "USER_DATABASE").allowMainThreadQueries().build()).getUserDAO();


        suppliesList = userDAO.getAllSupplies();
        suppliesListView = findViewById(R.id.suppliesListView_ModifyAdminViewActivity);

        ArrayAdapter<Supplies> adapter = new ArrayAdapter<Supplies>(AdminModifyViewActivity.this, android.R.layout.simple_list_item_1,suppliesList);
        suppliesListView.setAdapter(adapter);

        suppliesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ask if you want to modify item
                // take to modify item activity

                modifyItem(position);

            }
        });
     }



    public void modifyItem(int position){

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Would You like to modify this Item?");
        alertBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                    Intent intent = ModifyItemActivity.intentFactory(getApplicationContext());
                    intent.putExtra("ITEM-NAME",suppliesList.get(position).getItemName());
                    startActivity(intent);

                }


        });
        alertBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBuilder.create().show();
    }



    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, AdminModifyViewActivity.class);
        return intent;
    }

}
