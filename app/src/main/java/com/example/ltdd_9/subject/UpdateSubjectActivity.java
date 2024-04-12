package com.example.ltdd_9.subject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;
import com.example.ltdd_9.classes.UpdateClassActivity;
import com.example.ltdd_9.student.UpdateStudentActivity;

import java.util.ArrayList;

public class UpdateSubjectActivity extends AppCompatActivity {

    EditText ed_subject_name;
    Spinner spinner_subject_classid;
    Button btn_update_subject, btn_delete_subject;
    String id, name, subject_classid;
    DataBase myDB = new DataBase(UpdateSubjectActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_subject);

        ed_subject_name = findViewById(R.id.ed_up_subject_name);
        spinner_subject_classid = findViewById(R.id.spinner_classid_updatesubject);
        btn_delete_subject = findViewById(R.id.btn_delete_subject);
        btn_update_subject = findViewById(R.id.btn_update_subject);

        getIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name); }

        btn_update_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase db = new DataBase(UpdateSubjectActivity.this);
                name = ed_subject_name.getText().toString().trim();
                subject_classid = spinner_subject_classid.getSelectedItem().toString().trim();
                db.updateDataSubject(id, name, subject_classid);
            }
        });

        btn_delete_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    private void getIntentData(){
        if(getIntent().hasExtra("subject_id")
                && getIntent().hasExtra("subject_name")
                && getIntent().hasExtra("subject_classid")
        )
        {
            id = getIntent().getStringExtra("subject_id");
            name = getIntent().getStringExtra("subject_name");
            subject_classid = getIntent().getStringExtra("subject_classid");

            ArrayList<String> class_id = myDB.getAllNamesClass_id();
            ArrayAdapter<String> ad_classid = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, class_id);
            ad_classid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_subject_classid.setAdapter(ad_classid);

            int positionClassID = class_id.indexOf(subject_classid);
            if (positionClassID != -1) {
                spinner_subject_classid.setSelection(positionClassID);
            } else {
                Toast.makeText(this, "No data from class_id", Toast.LENGTH_SHORT).show();
            }

            ed_subject_name.setText(name);
        }
        else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            myDB.deleteOneRowSubject(id);
            // press yes close activity and comeback to main activity
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
        });
        builder.create().show();
    }
}