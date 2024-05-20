package com.example.project2alexanderverdugo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.room.Room;
import com.example.project2alexanderverdugo.db.UserDAO;
import com.example.project2alexanderverdugo.db.AppDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button adminButton;
    private Button orderHistorybutton;
    private Button deleteUserButton;
    private Button viewItemsButton;
    private UserDAO userDAO;
    private SharedPreferences preferences = null;
    private List<User> userList;
    private static final String USER_ID_KEY = "com.example.project2alexanderverdugo.userIdKey";
    private static final String PREFENCES_KEY = "com.example.project2alexanderverdugo.PREFENCES_KEY";
    private int userId = -1;
    private User user;

    //private List<Supplies> suppliesList;

    public MainActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        deleteUserButton = findViewById(R.id.button_Delete_User);
        viewItemsButton = findViewById(R.id.View_items_button);
        orderHistorybutton = findViewById(R.id.button_order_history);
        this.adminButton = (Button)this.findViewById(R.id.AdminButton);
        this.userDAO = ((AppDatabase)Room.databaseBuilder(this, AppDatabase.class, "USER_DATABASE")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build())
                .getUserDAO();
        this.checkForUser();
        this.checkIfAdmin(this.userId);
        this.addUserToPrefrence(this.userId);
        this.loginUser(this.userId);


        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });


        viewItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SuppliesListActivity.intentFactory(getApplicationContext());
                String username = user.getUsername();
                intent.putExtra("USERNAME",username);
                startActivity(intent);
            }
        });

        orderHistorybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OrderHistoryActivity.intentFactory(getApplicationContext());
                String username = user.getUsername();
                intent.putExtra("USERNAME",username);
                startActivity(intent);
            }
        });

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DeleteUserActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void checkIfAdmin(int userId) {
        this.user = this.userDAO.getUsersById(userId);
        if (this.user == null) {
            this.adminButton.setVisibility(View.INVISIBLE);
        } else if (this.user.getAdmin()) {
            this.adminButton.setVisibility(View.VISIBLE);
        }

    }

    private void loginUser(int userId) {
        this.user = this.userDAO.getUsersById(userId);
        this.invalidateOptionsMenu();
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if (this.user != null) {
            MenuItem item = menu.findItem(R.id.item1);
            item.setTitle(this.user.getUsername());
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private void addUserToPrefrence(int userId) {
        if (this.preferences == null) {
            this.getPrefs();
        }

        Editor editor = this.preferences.edit();
        editor.putInt(USER_ID_KEY, userId);
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

    private void checkForUser() {
        this.userId = this.getIntent().getIntExtra(USER_ID_KEY, -1);
        if (this.userId == -1) {
            if (this.preferences == null) {
                this.getPrefs();
            }

            this.userId = this.preferences.getInt(USER_ID_KEY, -1);
            if (this.userId == -1) {
                List<User> users = this.userDAO.getUsers();

                if (users.size() <= 0) {
                    User defaultUser = new User("testuser1", "testuser1", false);
                    User defaultAdmin = new User("admin2", "admin2", true);
                    this.userDAO.insert(new User[]{defaultUser});
                    this.userDAO.insert(new User[]{defaultAdmin});
                }


                Intent intent = LoginActivity.intentFactory(this);
                this.startActivity(intent);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.users_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item1:
                this.logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearUserFromIntent() {
        this.getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref() {
        this.addUserToPrefrence(-1);
    }

    private void getPrefs() {
        this.preferences = this.getSharedPreferences(PREFENCES_KEY, 0);
    }

    private void logoutUser() {
        Builder alertBuilder = new Builder(this);
        alertBuilder.setMessage("logout?");
        alertBuilder.setPositiveButton("yes", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.clearUserFromIntent();
                MainActivity.this.clearUserFromPref();
                MainActivity.this.userId = -1;
                MainActivity.this.checkForUser();
            }
        });
        alertBuilder.setNegativeButton("no", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBuilder.create().show();
    }
}
