package com.example.ltdd_9.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd_9.Account.dao.UserDAO;
import com.example.ltdd_9.Account.model.Account;
import com.example.ltdd_9.R;
import com.example.ltdd_9.UserActivity;

public class DetailUserActivity extends AppCompatActivity {

    private TextView tvName, tvUsername, tvEmail, tvIdTeacher;
    private Button btnBack,btnUpdate;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        userDAO = new UserDAO(this);
        tvName = findViewById(R.id.tvName);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvIdTeacher = findViewById(R.id.tvidteacher);
        btnBack = findViewById(R.id.btnBack);
        btnUpdate = findViewById(R.id.btnUpdate);

        Intent intent = getIntent();
        String loggedInUsername = intent.getStringExtra("loggedInUsername");

        Account user = userDAO.getUserByUsername(loggedInUsername);

        if (user != null) {
            tvName.setText(user.getName());
            tvUsername.setText(user.getUsername());
            tvEmail.setText(user.getEmail());

        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailUserActivity.this, ChangePasswordActivity.class);
                intent.putExtra("loggedInUsername", loggedInUsername);
                startActivity(intent);
                finish();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailUserActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
