package com.example.ltdd_9.subject;

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

public class SubjectActivity extends AppCompatActivity {

    RecyclerView rv_subject;
    FloatingActionButton addButton_subject;
    ImageView iv_subject;
    TextView tv_subject;
    DataBase db;
    ArrayList<String> subject_id, subject_name, subject_classid;
    SubjectAdapter subjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        rv_subject = findViewById(R.id.rv_subject);
        addButton_subject = findViewById(R.id.addButton_Subject);
        iv_subject = findViewById(R.id.iv_subject);
        tv_subject = findViewById(R.id.tv_subject);

        addButton_subject.setOnClickListener(view -> {
            Intent intent = new Intent(SubjectActivity.this, AddSubjectActivity.class);
            startActivity(intent);
        });

        db = new DataBase(SubjectActivity.this);
        subject_id = new ArrayList<>();
        subject_name = new ArrayList<>();
        subject_classid = new ArrayList<>();

        storeDataInArrays();
        subjectAdapter = new SubjectAdapter(SubjectActivity.this,this,
                subject_id,subject_name,subject_classid);
        rv_subject.setAdapter(subjectAdapter);
        rv_subject.setLayoutManager(new LinearLayoutManager(SubjectActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();}
    }

    void storeDataInArrays(){
        Cursor cursor = db.readAllDataSubject();
        if (cursor.getCount() == 0){
            iv_subject.setVisibility(View.VISIBLE);
            tv_subject.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                subject_id.add(cursor.getString(0));
                subject_name.add(cursor.getString(1));
                subject_classid.add(cursor.getString(2));
            }
            iv_subject.setVisibility(View.GONE);
            tv_subject.setVisibility(View.GONE);
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
        builder.setMessage("Are you sure you want to delete all data subject?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            DataBase myDB = new DataBase(SubjectActivity.this);
            myDB.deleteAllDataSubject();
            Intent intent = new Intent(SubjectActivity.this, SubjectActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
        });
        builder.create().show();
    }
}