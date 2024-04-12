package com.example.ltdd_9.exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExamActivity extends AppCompatActivity {
    DataBase db;
    ImageView iv_exam;
    TextView tv_exam;
    ArrayList<String> exam_id,exam_class_id,exam_subject_id,exam_student_id,exam_mark;
    ExamAdapter examAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        FloatingActionButton addButton_Exam= findViewById(R.id.addButton_Exam);
        RecyclerView rv_exam= findViewById(R.id.rv_exam);
        iv_exam= findViewById(R.id.iv_exam);
        tv_exam= findViewById(R.id.tv_exam);


        addButton_Exam.setOnClickListener(v -> {
            Intent intent = new Intent(ExamActivity.this, AddExamActivity.class);
            startActivity(intent);
        });
        db = new DataBase(ExamActivity.this);
        exam_id = new ArrayList<>();
        exam_class_id = new ArrayList<>();
        exam_subject_id = new ArrayList<>();
        exam_student_id = new ArrayList<>();
        exam_mark = new ArrayList<>();
        storeDataInArrays();
        examAdapter = new ExamAdapter(ExamActivity.this,this, exam_id,
                                exam_class_id, exam_subject_id, exam_student_id, exam_mark);
        rv_exam.setAdapter(examAdapter);
        rv_exam.setLayoutManager(new LinearLayoutManager(ExamActivity.this));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();}
    }
    void storeDataInArrays(){
        Cursor cursor = db.readAllDataExam();
        if (cursor.getCount() == 0){
            iv_exam.setVisibility(View.VISIBLE);
            tv_exam.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                exam_id.add(cursor.getString(0));
                exam_class_id.add(cursor.getString(1));
                exam_subject_id.add(cursor.getString(2));
                exam_student_id.add(cursor.getString(3));
                exam_mark.add(cursor.getString(4));
            }
            iv_exam.setVisibility(View.GONE);
            tv_exam.setVisibility(View.GONE);
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
        builder.setMessage("Are you sure you want to delete all data exam?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            DataBase myDB = new DataBase(ExamActivity.this);
            myDB.deleteAllDataExam();
            Intent intent = new Intent(ExamActivity.this,ExamActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.create().show();
    }
}