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

public class DeleteUserActivity extends AppCompatActivity {

    private Button deleteUserButton;
    private EditText usernameFeild;
    private EditText passwordFeild;
    private String username;
    private String password;
    private UserDAO userDAO;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        this.userDAO = ((AppDatabase) Room.databaseBuilder(this, AppDatabase.class, "USER_DATABASE")
                .allowMainThreadQueries()
                .build())
                .getUserDAO();

        deleteUserButton = findViewById(R.id.Modify_item_button_DeletItemActivity);
        usernameFeild = findViewById(R.id.ItemName_editText_DeleteItemActivity);
        passwordFeild = findViewById(R.id.editTextPassword_DA);
        password = passwordFeild.getText().toString();
        username = usernameFeild.getText().toString();
        user = userDAO.getUsersByUsername(username);



        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameFeild = findViewById(R.id.ItemName_editText_DeleteItemActivity);
                passwordFeild = findViewById(R.id.editTextPassword_DA);
                password = passwordFeild.getText().toString();
                username = usernameFeild.getText().toString();
                user = userDAO.getUsersByUsername(username);
                if(checkForUserInDatabase()){

                   if(validatePassword() == false){

                       Toast.makeText(DeleteUserActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();

                       Intent intent = DeleteUserActivity.intentFactory(getApplicationContext());
                       startActivity(intent);

                   }else {
//                    Toast.makeText(DeleteUserActivity.this, username, Toast.LENGTH_LONG).show();

                       userDAO.delete(user);

                       Toast.makeText(DeleteUserActivity.this, " User Deleted Successfully ", Toast.LENGTH_LONG).show();

                       Intent intent = LoginActivity.intentFactory(getApplicationContext());
                       startActivity(intent);
                   }
                }

            }
        });
    }



    private boolean validatePassword() {
        return this.user.getPassword().equals(password);
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


    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, DeleteUserActivity.class);
        return intent;
    }
}