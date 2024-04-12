package com.example.ltdd_9.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd_9.Account.dao.UserDAO;
import com.example.ltdd_9.Account.model.Account;
import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;

public class AddUserActivity extends AppCompatActivity {
    private Button btnAdd;
    private EditText eUser, ePass, eRepass, eName, eGmail;
    private CheckBox chkAdmin, chkUser;
    private DataBase db;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        db = new DataBase(this);
        userDAO = new UserDAO(this);
        eUser = findViewById(R.id.txtUsername);
        ePass = findViewById(R.id.txtPassword);
        eName = findViewById(R.id.txtName);
        eGmail = findViewById(R.id.txtGmail);
        eRepass = findViewById(R.id.txtRePassword);
        btnAdd = findViewById(R.id.btnAdd);
        chkAdmin = findViewById(R.id.chkAdmin);
        chkUser = findViewById(R.id.chkUser);

        chkAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkAdmin.isChecked()) {
                    chkUser.setChecked(false);
                }
            }
        });

        chkUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkUser.isChecked()) {
                    chkAdmin.setChecked(false);
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = eName.getText().toString();
                String gmail = eGmail.getText().toString();
                String username = eUser.getText().toString();
                String password = ePass.getText().toString();
                String rePassword = eRepass.getText().toString();

                int role = 0;
                if (chkAdmin.isChecked()) {
                    role = 1;
                } else if (chkUser.isChecked()) {
                    role = 0;
                }

                if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty() || name.isEmpty() || gmail.isEmpty()) {
                    Toast.makeText(AddUserActivity.this, "Please enter all value field.", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(rePassword)) {
                        boolean checkUser = userDAO.checkUsername(username);
                        if (!checkUser) {
                            Account account = new Account(name, gmail, username, password, role);
                            userDAO.addUser(account);
                            Toast.makeText(AddUserActivity.this, "Add user successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddUserActivity.this, AddShowUser.class);
                            intent.putExtra("newAccount", account);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AddUserActivity.this, "Username already exist.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddUserActivity.this, "Password incorrectly.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
