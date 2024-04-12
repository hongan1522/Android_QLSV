package com.example.ltdd_9.Account;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.example.ltdd_9.AdminActivity;
import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;
import com.example.ltdd_9.UserActivity;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin, btnRegister;
    private EditText eUser, ePass;
    private DataBase db;
    private Account account;
    private UserDAO userDAO;
    ImageButton ib_eye;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eUser = findViewById(R.id.txtUsername);
        ePass = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        ib_eye = findViewById(R.id.ib_eye);
        db = new DataBase(getApplicationContext());
        userDAO = new UserDAO(this);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        ib_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordVisible) {
                    ePass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ib_eye.setImageResource(R.drawable.close_eyes);
                    isPasswordVisible = false;
                } else {
                    ePass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ib_eye.setImageResource(R.drawable.open_eyes);
                    isPasswordVisible = true;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            String username = eUser.getText().toString();
            String password = ePass.getText().toString();
            if (username.equals("") || password.equals("")) {
                Toast.makeText(LoginActivity.this, "Please enter value correctly!", Toast.LENGTH_SHORT).show();
            } else {
                String hashedPassword = PasswordHasher.hashPassword(password);
                Log.d("Login", "Mật khẩu đã được mã hóa thành: " + hashedPassword);

                Boolean checkUserPass = userDAO.checkUsernamePassword(username, password);
                if (checkUserPass) {
                    Toast.makeText(LoginActivity.this, "Log In Successfully!!", Toast.LENGTH_SHORT).show();
                    account = userDAO.login(username, password);
                    Log.i("acc", account.getUsername());
                    int role = userDAO.getRoleByUsername(account.getUsername());
                    Log.i("role", String.valueOf(role));

                    if (role == 1) {
                        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                        intent.putExtra("account", account);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                        intent.putExtra("account", account);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Log In fail. Check your information again!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (view == btnRegister) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }

}
