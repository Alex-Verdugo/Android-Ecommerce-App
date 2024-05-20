package com.example.project2alexanderverdugo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.project2alexanderverdugo.db.UserDAO;
import com.example.project2alexanderverdugo.db.AppDatabase;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private TextView appName;
    //private TextView allUsers;
    private EditText usernameFeild;
    private EditText passwordFeild;
    private String username;
    private String password;
    private Button loginButton;
    private Button createAccountButton;
    private UserDAO userDAO;
    private User user;
    private List<User> userList;

    public LoginActivity() {
    }



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        appName = findViewById(R.id.textView_appName);
        usernameFeild = findViewById(R.id.ItemName_editText_DeleteItemActivity);
        passwordFeild = findViewById(R.id.editTextPassword_DA);
        loginButton = findViewById(R.id.Create_Account_Button_CreateActivity);
        //allUsers = findViewById(R.id.textView_all_users);
        //  allUsers.setMovementMethod(new ScrollingMovementMethod());
        createAccountButton = findViewById(R.id.button_create_account);

        this.userDAO = ((AppDatabase)Room.databaseBuilder(this, AppDatabase.class, "USER_DATABASE")
                .allowMainThreadQueries()
                .build())
                .getUserDAO();

       // allUsers.setText(userDAO.getUsers().toString());

       // refreshDisplay();

        this.loginButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LoginActivity.this.getValuesFromDisplay();
                if (LoginActivity.this.checkForUserInDatabase()) {
                    if (!LoginActivity.this.validatePassword()) {
                        Toast.makeText(LoginActivity.this, " invalid password", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = MainActivity.intentFactory(LoginActivity.this.getApplicationContext(), LoginActivity.this.user.getUserID());
                        LoginActivity.this.startActivity(intent);
                    }
                }

            }
        });

        createAccountButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CreateUserActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });


    }

    private void getValuesFromDisplay() {
        this.username = this.usernameFeild.getText().toString();
        this.password = this.passwordFeild.getText().toString();
    }

    private boolean validatePassword() {
        return this.user.getPassword().equals(this.password);
    }

    private boolean checkForUserInDatabase() {
        this.user = this.userDAO.getUsersByUsername(this.username);
        if (this.user == null) {
            Toast.makeText(this, " no user " + this.username + " found ", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

//    private void refreshDisplay(){
//      //  mGymLogs = mGymLogDAO.getGymLogsByUserId(mUserId);
//        userList = userDAO.getUsers();
//
//        StringBuilder sb = new StringBuilder();
//        for( User user : userList){
//            sb.append(user);
//            sb.append("\n");
//            sb.append("======================");
//            sb.append("\n");
//        }
//
//      allUsers.setText(sb.toString());
//
//    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}
