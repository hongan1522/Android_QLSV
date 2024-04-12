package com.example.ltdd_9;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd_9.Account.AddShowUser;
import com.example.ltdd_9.Account.DetailUserActivity;
import com.example.ltdd_9.Account.LoginActivity;
import com.example.ltdd_9.Account.model.Account;
import com.example.ltdd_9.classes.ClassActivity;
import com.example.ltdd_9.exam.ExamActivity;
import com.example.ltdd_9.lecturer.LecturerActivity;
import com.example.ltdd_9.statistic.StatisticActivity;
import com.example.ltdd_9.student.StudentActivity;
import com.example.ltdd_9.subject.SubjectActivity;

public class UserActivity extends AppCompatActivity {
    Button btn_user_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Ánh xạ Button từ layout


        // Lấy thông tin đăng nhập từ Intent
        Account loggedInAccount = (Account) getIntent().getSerializableExtra("account");
        final String loggedInUsername = loggedInAccount.getUsername();

        btn_user_menu = findViewById(R.id.btn_admin_menu);
        btn_user_menu.setOnClickListener(view -> {
            PopupMenu menu = new PopupMenu(UserActivity.this, btn_user_menu);
            menu.getMenuInflater().inflate(R.menu.menu_user, menu.getMenu());
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.item_user_info: {
                            openDetailUserActivity(loggedInUsername);
                            return true;
                        }
                        case R.id.item_exam_manager_user: {
                            Intent intent = new Intent(UserActivity.this, ExamActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        case R.id.item_logout_user:
                        {
                            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        default:
                            return false;
                    }
                }
            });
            menu.show();
        });
    }

    // Phương thức mở DetailUserActivity và truyền loggedInUsername qua Intent
    private void openDetailUserActivity(String loggedInUsername) {
        Intent intent = new Intent(this, DetailUserActivity.class);
        intent.putExtra("loggedInUsername", loggedInUsername);
        startActivity(intent);
    }
}

