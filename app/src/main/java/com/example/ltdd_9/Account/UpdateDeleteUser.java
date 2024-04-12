package com.example.ltdd_9.Account;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd_9.Account.adapter.apdapterUser;
import com.example.ltdd_9.Account.dao.UserDAO;
import com.example.ltdd_9.Account.model.Account;
import com.example.ltdd_9.Account.rule.PasswordHasher;
import com.example.ltdd_9.R;

import java.util.List;

public class UpdateDeleteUser extends AppCompatActivity {
    private EditText eName, eMail, eUsername, ePassword, eConfirmPassword;
    private RadioGroup radioGroup;
    private RadioButton radioButtonAdmin, radioButtonUser;
    private Button btnSave, btnDelete, btnCancel;
    private Account account;
    private boolean isEditing;
    private UserDAO userDAO;
    private apdapterUser adapter;
    public static AddShowUser addShowUserInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_user);

        userDAO = new UserDAO(this);
        adapter = new apdapterUser();
        eName = findViewById(R.id.eName);
        eMail = findViewById(R.id.eMail);
        eUsername = findViewById(R.id.eUsername);
        radioGroup = findViewById(R.id.radiogroup);
        ePassword = findViewById(R.id.ePassword);
        eConfirmPassword = findViewById(R.id.eConfirmPassword);
        radioButtonAdmin = findViewById(R.id.radiobtnAdmin);
        radioButtonUser = findViewById(R.id.radiobtnUser);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("account")) {
            account = (Account) intent.getSerializableExtra("account");
            isEditing = true;
            displayAccountInfo(account);

            // Đặt radio button tương ứng với vai trò của tài khoản
            if (account.getRole() == 1) {
                radioButtonAdmin.setChecked(true);
            } else {
                radioButtonUser.setChecked(true);
            }
        } else {
            isEditing = false;
        }
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radiobtnAdmin:
                    Log.i("UpdateDeleteUser", "Radio button Admin selected, role = " + account.getRole());
                    account.setRole(1);
                    break;
                case R.id.radiobtnUser:
                    Log.i("UpdateDeleteUser", "Radio button User selected, role = " + account.getRole());
                    account.setRole(0);
                    break;
            }
        });


        // Xử lý sự kiện khi click vào nút Lưu
        btnSave.setOnClickListener(view -> {
            if (account != null && validateFields()) {
                Log.i("UpdateDeleteUser", "Saving account information...");
                Account updatedAccount = new Account();
                updatedAccount.setId(account.getId()); // Đảm bảo giữ nguyên ID của tài khoản
                updatedAccount.setName(eName.getText().toString());
                updatedAccount.setEmail(eMail.getText().toString());
                updatedAccount.setUsername(eUsername.getText().toString());
                String hashedPassword = PasswordHasher.hashPassword(ePassword.getText().toString());
                updatedAccount.setPassword(hashedPassword);
                Log.d("Update", "Mật khẩu đã được mã hóa thành: " + hashedPassword);
                if (radioButtonAdmin.isChecked()) {
                    updatedAccount.setRole(1);
                } else {
                    updatedAccount.setRole(0);
                }
                Log.i("UpdateDeleteUser", "Updated account information: " + updatedAccount.toString());
                UserDAO userDAO = new UserDAO(this);
                userDAO.updateAccount(updatedAccount);
                Log.i("UpdateDeleteUser", "Account updated successfully");
                Intent intent1 = new Intent(UpdateDeleteUser.this, AddShowUser.class);
                startActivity(intent1);
            }
        });

        // Xử lý sự kiện khi click vào nút Xóa
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xác nhận trước khi xóa
                new AlertDialog.Builder(UpdateDeleteUser.this)
                        .setTitle("Delete ")
                        .setMessage("Are you sure to delete this record?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Gọi phương thức xóa tài khoản từ UserDAO
                                if (userDAO.deleteAccount(account.getId())) {
                                    Toast.makeText(UpdateDeleteUser.this, "Xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UpdateDeleteUser.this,AddShowUser.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(UpdateDeleteUser.this, "Xóa tài khoản thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        // Xử lý sự kiện khi click vào nút Hủy
        btnCancel.setOnClickListener(view -> {
            Intent intent2 = new Intent(UpdateDeleteUser.this,AddShowUser.class);
            startActivity(intent2);
        });
    }
//    public void updateAccountRole(int accountId, int newRole) {
//        userDAO.updateRole(accountId, newRole); // Gọi phương thức updateRole của UserDAO
//        adapter.notifyDataSetChanged(); // Cập nhật lại RecyclerView
//    }
@Override
protected void onResume() {
    super.onResume();
    Intent intent = getIntent();
    if (intent != null && intent.hasExtra("account")) {
        Account updatedAccount = (Account) intent.getSerializableExtra("account");
        // Kiểm tra xem vai trò đã thay đổi hay chưa
        if (updatedAccount != null && updatedAccount.getRole() != account.getRole()) {
            //account.setRole(updatedAccount.getRole());
            userDAO.updateAccount(account);
            Log.i("UpdateDeleteUser", "Updated role for account with ID " + account.getId() + " to " + account.getRole());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("updatedAccount", account);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}



    private void displayAccountInfo(Account account) {
        if (account != null) {
            eName.setText(account.getName());
            eMail.setText(account.getEmail());
            eUsername.setText(account.getUsername());
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin tài khoản", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean validateFields() {
        if (eName.getText().toString().isEmpty()) {
            eName.setError("Nhập tên");
            eName.requestFocus();
            return false;
        }
        if (eMail.getText().toString().isEmpty()) {
            eMail.setError("Nhập mail");
            eMail.requestFocus();
            return false;
        }
        if (!isValidEmail(eMail.getText().toString())) {
            eMail.setError("Email không hợp lệ");
            eMail.requestFocus();
            return false;
        }
        if (eUsername.getText().toString().isEmpty()) {
            eUsername.setError("Nhập username");
            eUsername.requestFocus();
            return false;
        }
        if (ePassword.getText().toString().isEmpty()) {
            ePassword.setError("Nhập mật khẩu");
            ePassword.requestFocus();
            return false;
        }
        if (eConfirmPassword.getText().toString().isEmpty()) {
            eConfirmPassword.setError("Xác nhận mật khẩu");
            eConfirmPassword.requestFocus();
            return false;
        }
        // Kiểm tra xác nhận mật khẩu có khớp với mật khẩu đã nhập không
        if (!ePassword.getText().toString().equals(eConfirmPassword.getText().toString())) {
            eConfirmPassword.setError("Mật khẩu không khớp");
            eConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }

    // Phương thức kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}
