package com.example.ltdd_9.Account;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.ltdd_9.Account.dao.UserDAO;
import com.example.ltdd_9.Account.model.Account;
import com.example.ltdd_9.Account.rule.PasswordHasher;
import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText eUser, ePass, eRepass, eName, eMail;
    private DataBase db;
    private UserDAO userDAO;
    ImageButton ib_Pass, ib_RePass;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DataBase(this);
        userDAO = new UserDAO(this);
        eUser = findViewById(R.id.txtUsername);
        ePass = findViewById(R.id.txtPassword);
        eName = findViewById(R.id.txtName);
        eMail = findViewById(R.id.txtGmail);
        eRepass = findViewById(R.id.txtRePassword);
        btnRegister = findViewById(R.id.btnSave1);
        ib_Pass= findViewById(R.id.ibPasswordVisibility);
        ib_RePass = findViewById(R.id.ibRePasswordVisibility);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = eName.getText().toString();
                String email = eMail.getText().toString();
                String username = eUser.getText().toString();
                String password = ePass.getText().toString();
                String rePassword = eRepass.getText().toString();
                int role = 0;

                if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty() || name.isEmpty() || email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all value.", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Email format wrong.", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(rePassword)) {
                        boolean checkUser = userDAO.checkUsername(username);
                        if (!checkUser) {

                            String hashedPassword = PasswordHasher.hashPassword(password);
                            Log.d("Register", "Mật khẩu đã được mã hóa thành: " + hashedPassword);

                            Account account = new Account(name, email, username, password, role);
                            userDAO.addUser(account);
                            Toast.makeText(RegisterActivity.this, "Register succesfully.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "UserName already exist.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Password incorrectly", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ib_Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordVisible) {
                    ePass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ib_Pass.setImageResource(R.drawable.close_eyes);
                    isPasswordVisible = false;
                } else {
                    ePass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ib_Pass.setImageResource(R.drawable.open_eyes);
                    isPasswordVisible = true;
                }
            }
        });

        ib_RePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordVisible) {
                    eRepass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ib_RePass.setImageResource(R.drawable.close_eyes);
                    isPasswordVisible = false;
                } else {
                    eRepass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ib_RePass.setImageResource(R.drawable.open_eyes);
                    isPasswordVisible = true;
                }
            }
        });
    }
    // Phương thức kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}