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

import java.util.List;

public class CreateUserActivity extends AppCompatActivity {

    private EditText usernameFeild;
    private EditText passwordFeild;
    private String username;
    private String password;
    private Button createUserButton;
    private UserDAO userDAO;
    private List<User> userList;
 //   private TextView allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        usernameFeild = findViewById(R.id.ItemName_editText_DeleteItemActivity);
        passwordFeild = findViewById(R.id.editTextPassword_DA);
      //  allUsers = findViewById(R.id.textView_all_users);
      //  allUsers.setMovementMethod(new ScrollingMovementMethod());


        createUserButton = findViewById(R.id.Create_Account_Button_CreateActivity);


        this.userDAO = ((AppDatabase) Room.databaseBuilder(this, AppDatabase.class, "USER_DATABASE").allowMainThreadQueries().build()).getUserDAO();

       // refreshDisplay();


        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                addUserToDatabase();
            }
        });

    }

//    private void refreshDisplay(){
//        //  mGymLogs = mGymLogDAO.getGymLogsByUserId(mUserId);
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
//       // allUsers.setText(sb.toString());
//
//    }

    private void getValuesFromDisplay() {
        username = usernameFeild.getText().toString();
        password = passwordFeild.getText().toString();
    }

    public void addUserToDatabase(){

        userList = userDAO.getUsers();

        for( User user : userList) {

            if (user.getUsername().equals(username)) {
                Toast.makeText(CreateUserActivity.this, " Sorry that Username is already taken.", Toast.LENGTH_LONG).show();

                Intent intent = CreateUserActivity.intentFactory(getApplicationContext());

                startActivity(intent);

                return;
            }


        }

        User newUser = new User(username,password,false);

        userDAO.insert(newUser);

        Toast.makeText(CreateUserActivity.this, " User Created Successfully !!!", Toast.LENGTH_LONG).show();

        Intent intent = LoginActivity.intentFactory(getApplicationContext());

        startActivity(intent);


    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, CreateUserActivity.class);
        return intent;
    }
}