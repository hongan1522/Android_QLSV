package com.example.ltdd_9.student;

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

import java.sql.Date;
import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton;
    DataBase myDB;
    ArrayList<String> student_id,student_name,student_gender,student_email,student_classid;
    ArrayList<Date> student_bir;
    StudentAdapter customeAdapter;
    ImageView empty_image;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.addButton);
        empty_image = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(StudentActivity.this, AddStudentActivity.class);
            startActivity(intent);
        });
        myDB = new DataBase(StudentActivity.this);
        student_id = new ArrayList<>();
        student_name = new ArrayList<>();
        student_gender= new ArrayList<>();
        student_email = new ArrayList<>();
        student_classid = new ArrayList<>();
        student_bir = new ArrayList<>();

        storeDataInArrays();
        customeAdapter = new StudentAdapter(StudentActivity.this,this,
                student_id,student_name,student_gender,student_email,student_classid, student_bir);
        recyclerView.setAdapter(customeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(StudentActivity.this));
    }

    // Refrest database if requestCode = 1 (after update database)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();}
        }
    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            empty_image.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                student_id.add(cursor.getString(0));
                student_name.add(cursor.getString(1));
                student_gender.add(cursor.getString(2));
                student_email.add(cursor.getString(3));

                long birMilliseconds = cursor.getLong(4);
                Date bir = new Date(birMilliseconds);
                student_bir.add(bir);

                student_classid.add(cursor.getString(5));
            }
            empty_image.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
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
        builder.setMessage("Are you sure you want to delete all data student?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            DataBase myDB = new DataBase(StudentActivity.this);
            myDB.deleteAllData();
            Intent intent = new Intent(StudentActivity.this,StudentActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.create().show();
    }
}