package com.example.ltdd_9.classes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity {

    EditText ed_class_name;
    FloatingActionButton add_class;
    RecyclerView rv_class;
    ArrayList<String> class_name,class_id;
    ImageView iv_class;
    TextView tv_class;
    ClassAdapter classAdapter;
    DataBase db;
    List<ClassModel> classItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        ed_class_name = findViewById(R.id.ed_class_name);
        add_class = findViewById(R.id.addButton_Class);
        rv_class = findViewById(R.id.rv_class);
        iv_class = findViewById(R.id.iv_class);

        tv_class = findViewById(R.id.tv_class);
        add_class.setOnClickListener(view -> {
            Intent intent = new Intent(ClassActivity.this, AddClassActivity.class);
            startActivity(intent);
        });

        db = new DataBase(ClassActivity.this);
        class_name = new ArrayList<>();
        class_id = new ArrayList<>();
        storeDataInArrays1();
        classAdapter = new ClassAdapter(ClassActivity.this,this,class_id,class_name);
        rv_class.setAdapter(classAdapter);
        rv_class.setLayoutManager(new LinearLayoutManager(ClassActivity.this));
    }

    private void filter(String newText) {

        DataBase db = new DataBase(this);
        Cursor cursor = db.readAllDataClass();
        if (cursor.getCount() == 0){

        }else {
            while (cursor.moveToNext()){
                @SuppressLint("Range") String classId=cursor.getString(cursor.getColumnIndex("class_id"));
                if (classId.contains(newText)) {
                    @SuppressLint("Range") String className = cursor.getString(cursor.getColumnIndex("class_name"));
                    classItems.add(new ClassModel(classId, className));
                }
            }
            cursor.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();}
    }
    void storeDataInArrays1(){
        Cursor cursor = db.readAllDataClass();
        if (cursor.getCount() == 0){
            iv_class.setVisibility(View.VISIBLE);
            tv_class.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                class_id.add(cursor.getString(0));
                class_name.add(cursor.getString(1));
            }
            iv_class.setVisibility(View.GONE);
            tv_class.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mymenu,menu);
        MenuItem item = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
        builder.setMessage("Are you sure you want to delete all data class?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            DataBase myDB = new DataBase(ClassActivity.this);
            myDB.deleteAllDataClass();
            Intent intent = new Intent(ClassActivity.this,ClassActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.create().show();
    }
}