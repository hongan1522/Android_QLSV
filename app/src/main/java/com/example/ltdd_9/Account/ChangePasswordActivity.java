package com.example.ltdd_9.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ltdd_9.Account.dao.UserDAO;
import com.example.ltdd_9.Account.model.Account;
import com.example.ltdd_9.Account.rule.PasswordHasher;
import com.example.ltdd_9.R;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnUpdate, btnCancel;
    private EditText eNewPass, eConfirmPass;
    private UserDAO userDAO;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        userDAO = new UserDAO(this);
        initView();
        btnUpdate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        Intent t = getIntent();
        String loggedInUsername = t.getStringExtra("loggedInUsername");
        account = userDAO.getUserByUsername(loggedInUsername);
//        account = (Account) t.getSerializableExtra("account");
    }

    private void initView() {
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnSave);
        eNewPass = findViewById(R.id.txtNewPassword);
        eConfirmPass = findViewById(R.id.txtNewPasswordConfirm);
    }

    @Override
    public void onClick(View view) {
        if (view == btnCancel) {
            finish();
        }
        if (view == btnUpdate) {
            String newPass = eNewPass.getText().toString();
            String newPassConfirm = eConfirmPass.getText().toString();
            if (newPassConfirm.equals(newPass)) {
                String hashedPassword = PasswordHasher.hashPassword(newPass);
                account.setPassword(newPass);
                Log.d("ChangePassword", "Mật khẩu đã được mã hóa thành: " + hashedPassword);
                userDAO.changePassword(account);
                finish();
            } else {
                Toast.makeText(this, "Password must be same in both field.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
