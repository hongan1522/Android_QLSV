package com.example.ltdd_9.classes;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;


public class UpdateClassActivity extends AppCompatActivity {
    EditText ed_up_class_name;
    Button bt_update_class, bt_delete_class;
    String name, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_class);

        ed_up_class_name = findViewById(R.id.ed_up_class_name);

        getIntentData();

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(name); }

        bt_update_class = findViewById(R.id.bt_update_class);
        bt_update_class.setOnClickListener(view -> {
            DataBase db = new DataBase(UpdateClassActivity.this);
            name = ed_up_class_name.getText().toString().trim();
            db.updateDataClass(id, name);
        });
        bt_delete_class = findViewById(R.id.bt_delete_class);
        bt_delete_class.setOnClickListener(view -> {
            confirmDialog();
        });
    }

    private void getIntentData(){
        if(getIntent().hasExtra("class_id")
                && getIntent().hasExtra("class_name"))
        {
            //getting database from intent
            id = getIntent().getStringExtra("class_id");
            name = getIntent().getStringExtra("class_name");
            //setting intent data
            ed_up_class_name.setText(name);
        }
        else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            DataBase db = new DataBase(UpdateClassActivity.this);
            db.deleteOneRowClass(id);
            // press yes close activity and comeback to main activity
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.create().show();
    }
}