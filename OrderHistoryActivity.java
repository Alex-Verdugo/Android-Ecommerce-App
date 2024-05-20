package com.example.project2alexanderverdugo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.project2alexanderverdugo.db.AppDatabase;
import com.example.project2alexanderverdugo.db.UserDAO;

public class OrderHistoryActivity extends AppCompatActivity {
    TextView textView;
    UserDAO userDAO;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        textView = findViewById(R.id.orders);
        textView.setMovementMethod(new ScrollingMovementMethod());

        this.userDAO = ((AppDatabase) Room.databaseBuilder(this, AppDatabase.class, "USER_DATABASE").allowMainThreadQueries().build()).getUserDAO();





        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        user = userDAO.getUsersByUsername(username);


        textView.setText(user.getOrders());

    }




    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, OrderHistoryActivity.class);
        return intent;
    }
}