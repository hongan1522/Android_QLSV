package com.example.ltdd_9.lecturer;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LecturerActivity extends AppCompatActivity {

    RecyclerView rv_lecture;
    FloatingActionButton addButton_Lecturer;
    ImageView iv_lecturer;
    TextView tv_lecturer;
    DataBase db;
    ArrayList<String> lecturer_id;
    ArrayList<String> lecturer_classid;
    ArrayList<String> lecturer_subjectid;
    ArrayList<String> lecturer_name;
    ArrayList<String> lecturer_user_id;
    LecturerAdapter lecturerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer);

        rv_lecture = findViewById(R.id.rv_lecturer);
        addButton_Lecturer = findViewById(R.id.addButton_Lecturer);
        tv_lecturer = findViewById(R.id.tv_lecturer);
        iv_lecturer = findViewById(R.id.iv_lecturer);

        addButton_Lecturer.setOnClickListener(view -> {
            Intent intent = new Intent(LecturerActivity.this, AddLecturerActivity.class);
            startActivity(intent);
        });

        db = new DataBase(LecturerActivity.this);
        lecturer_id = new ArrayList<>();
        lecturer_classid = new ArrayList<>();
        lecturer_subjectid = new ArrayList<>();
        lecturer_name = new ArrayList<>();
        lecturer_user_id = new ArrayList<>();
        storeDataInArrays();

        lecturerAdapter = new LecturerAdapter(LecturerActivity.this, this,
                lecturer_id, lecturer_classid, lecturer_subjectid, lecturer_name, lecturer_user_id);
        rv_lecture.setAdapter(lecturerAdapter);
        rv_lecture.setLayoutManager(new LinearLayoutManager(LecturerActivity.this));
    }

    void storeDataInArrays(){
        Cursor cursor = db.readAllDataLecturer();
        if (cursor.getCount() == 0){
            iv_lecturer.setVisibility(View.VISIBLE);
            tv_lecturer.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                lecturer_id.add(cursor.getString(0));
                lecturer_name.add(cursor.getString(3));
                lecturer_classid.add(cursor.getString(1));
                lecturer_subjectid.add(cursor.getString(2));
                lecturer_user_id.add(cursor.getString(4));
            }
            iv_lecturer.setVisibility(View.GONE);
            tv_lecturer.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all ?");
        builder.setMessage("Are you sure you want to delete all data lecturer?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            DataBase myDB = new DataBase(LecturerActivity.this);
            myDB.deleteAllDataLecturer();
            Intent intent = new Intent(LecturerActivity.this, LecturerActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
        });
        builder.create().show();
    }
}