package com.example.ltdd_9.lecturer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;
import com.example.ltdd_9.subject.UpdateSubjectActivity;

import java.util.ArrayList;

public class UpdateLecturerActivity extends AppCompatActivity {

    Spinner sp_up_classid_lecturer, sp_up_subjectid_lecturer,sp_up_userid_lecturer;
    Button btn_up_lecturer, btn_delete_lecturer;
    DataBase db;
    EditText ed_up_lecturer_name;
    String id, lecturer_class_id, lecturer_subject_id, lecturer_name, lecturer_userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_lecturer);

        sp_up_classid_lecturer = findViewById(R.id.spinner_classid_up_lecturer);
        sp_up_subjectid_lecturer = findViewById(R.id.spinner_subjectid_up_lecturer);
        sp_up_userid_lecturer = findViewById(R.id.spinner_userid_up_lecturer);
        btn_delete_lecturer = findViewById(R.id.btn_delete_lecturer);
        btn_up_lecturer = findViewById(R.id.btn_update_lecturer);
        ed_up_lecturer_name = findViewById(R.id.ed_up_lecturer_name);


        db = new DataBase(UpdateLecturerActivity.this);

        getIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(lecturer_name);
        }

        btn_up_lecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase db = new DataBase(UpdateLecturerActivity.this);
                lecturer_class_id = sp_up_classid_lecturer.getSelectedItem().toString().trim();
                lecturer_subject_id = sp_up_subjectid_lecturer.getSelectedItem().toString().trim();
                lecturer_name = ed_up_lecturer_name.getText().toString().trim();
                lecturer_userid = sp_up_userid_lecturer.getSelectedItem().toString().trim();
                db.updateDataLecturer(id, lecturer_name, lecturer_class_id, lecturer_subject_id,lecturer_userid);
            }
        });

        btn_delete_lecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    private void getIntentData() {
        if (getIntent().hasExtra("lecturer_id")
                && getIntent().hasExtra("lecturer_classid")
                && getIntent().hasExtra("lecturer_subjectid")
                && getIntent().hasExtra("lecturer_name")
                && getIntent().hasExtra("lecturer_userid")
        ) {
            id = getIntent().getStringExtra("lecturer_id");
            lecturer_class_id = getIntent().getStringExtra("lecturer_classid");
            lecturer_subject_id = getIntent().getStringExtra("lecturer_subjectid");
            lecturer_name = getIntent().getStringExtra("lecturer_name");
            lecturer_userid = getIntent().getStringExtra("lecturer_userid");
            ed_up_lecturer_name.setText(lecturer_name);

            ArrayList<String> class_id = db.getAllNamesClass_id();
            ArrayAdapter<String> ad_classid = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, class_id);
            ad_classid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_up_classid_lecturer.setAdapter(ad_classid);

            int positionClassID = class_id.indexOf(lecturer_class_id);
            if (positionClassID != -1) {
                sp_up_classid_lecturer.setSelection(positionClassID);
            } else {
                Toast.makeText(this, "No data from class_id", Toast.LENGTH_SHORT).show();
            }

            ArrayList<String> subject_id = db.getAllNamesSubject_id();
            ArrayAdapter<String> ad_subjectid = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subject_id);
            ad_subjectid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_up_subjectid_lecturer.setAdapter(ad_subjectid);

            int positionSubjectId = subject_id.indexOf(lecturer_subject_id);
            if (positionSubjectId != -1) {
                sp_up_subjectid_lecturer.setSelection(positionSubjectId);
            } else {
                Toast.makeText(this, "No data from subject_id", Toast.LENGTH_SHORT).show();
            }
            ArrayList<String> user_id = db.getAllNamesUser_id();
            ArrayAdapter<String> ad_userid = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, user_id);
            ad_userid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_up_userid_lecturer.setAdapter(ad_userid);

            int positionUserID = user_id.indexOf(lecturer_userid);
            if (positionUserID != -1) {
                sp_up_userid_lecturer.setSelection(positionUserID);
            } else {
                Toast.makeText(this, "No data from user_id", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + lecturer_name + " ?");
        builder.setMessage("Are you sure you want to delete " + lecturer_name + " ?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            db.deleteOneRowLecturer(id);
            // press yes close activity and comeback to main activity
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
        });
        builder.create().show();
    }
}