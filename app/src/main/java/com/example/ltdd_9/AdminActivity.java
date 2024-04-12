package com.example.ltdd_9;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd_9.Account.AddShowUser;
import com.example.ltdd_9.Account.LoginActivity;
import com.example.ltdd_9.Account.model.Account;
import com.example.ltdd_9.classes.ClassActivity;
import com.example.ltdd_9.exam.ExamActivity;
import com.example.ltdd_9.lecturer.LecturerActivity;
import com.example.ltdd_9.statistic.StatisticActivity;
import com.example.ltdd_9.student.StudentActivity;
import com.example.ltdd_9.subject.SubjectActivity;

public class AdminActivity extends AppCompatActivity {

    Button btn_admin_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        TextView tvUsername = findViewById(R.id.tvUsername);
        Account loggedInAccount = (Account) getIntent().getSerializableExtra("account");
        if (loggedInAccount != null) {
            String loggedInUsername = loggedInAccount.getUsername();
            tvUsername.setText(loggedInUsername);
        } else {
            tvUsername.setText("Unknown");
        }
        btn_admin_menu = findViewById(R.id.btn_admin_menu);
        btn_admin_menu.setOnClickListener(view -> {
            PopupMenu menu = new PopupMenu(AdminActivity.this, btn_admin_menu);
            menu.getMenuInflater().inflate(R.menu.menu_admin, menu.getMenu());
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.item_student_manager: {
                            Intent intent = new Intent(AdminActivity.this, StudentActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        case R.id.item_class_manager: {
                            Intent intent = new Intent(AdminActivity.this, ClassActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        case R.id.item_subject_manager:
                        {
                            Intent intent = new Intent(AdminActivity.this, SubjectActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        case R.id.item_exam_manager:
                        {
                            Intent intent = new Intent(AdminActivity.this, ExamActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        case R.id.item_logout:
                        {
                            Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        case R.id.item_lecturer_manager:
                        {
                            Intent intent = new Intent(AdminActivity.this, LecturerActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        case R.id.item_statistical:
                        {
                            Intent intent = new Intent(AdminActivity.this, StatisticActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        case R.id.item_user_manager: {
                            Intent intent = new Intent(AdminActivity.this, AddShowUser.class);
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
}