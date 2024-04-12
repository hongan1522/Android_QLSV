package com.example.ltdd_9.exam;

import android.app.AlertDialog;
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

import java.util.ArrayList;

public class UpdateExamActivity extends AppCompatActivity {

    EditText ed_exam_mark;
    Spinner spinner_exam_class_id_update,spinner_exam_subject_id_update,spinner_exam_student_id_update;
    Button btn_exam_update,btn_exam_delete;
    String exam_id,exam_class_id,exam_subject_id,exam_student_id,exam_mark;
    DataBase db = new DataBase(UpdateExamActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_exam);

        ed_exam_mark = findViewById(R.id.ed_exam_mark_update);
        spinner_exam_class_id_update = findViewById(R.id.spinner_classid_addexam_update);
        spinner_exam_subject_id_update = findViewById(R.id.spinner_subjectid_addexam_update);
        spinner_exam_student_id_update = findViewById(R.id.spinner_studentid_addexam_update);
        btn_exam_delete = findViewById(R.id.btn_delete_exam);
        btn_exam_update = findViewById(R.id.btn_update_exam);
        getIntentDataExam();
        //set actionbar name after getIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(exam_id); }

        btn_exam_update.setOnClickListener(v -> {
            exam_class_id = spinner_exam_class_id_update.getSelectedItem().toString().trim();
            exam_subject_id = spinner_exam_subject_id_update.getSelectedItem().toString().trim();
            exam_mark = ed_exam_mark.getText().toString().trim();
            exam_student_id = spinner_exam_student_id_update.getSelectedItem().toString().trim();
            db.updateExam(exam_id, exam_class_id, exam_subject_id, exam_student_id, exam_mark);
        });

        btn_exam_delete.setOnClickListener(view -> confirmDialog());
    }

    void getIntentDataExam(){
        if (getIntent().hasExtra("exam_id")&&
                getIntent().hasExtra("exam_mark") &&
                getIntent().hasExtra("exam_class_id") &&
                getIntent().hasExtra("exam_subject_id") &&
                getIntent().hasExtra("exam_student_id")){
            exam_id = getIntent().getStringExtra("exam_id");
            exam_mark = getIntent().getStringExtra("exam_mark");
            exam_class_id = getIntent().getStringExtra("exam_class_id");
            exam_subject_id = getIntent().getStringExtra("exam_subject_id");
            exam_student_id = getIntent().getStringExtra("exam_student_id");

            ed_exam_mark.setText(exam_mark);

            ArrayList<String> class_id = db.getAllNamesClass_id();
            ArrayAdapter<String> ad_classid = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, class_id);
            ad_classid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_exam_class_id_update.setAdapter(ad_classid);

            int positionClassID = class_id.indexOf(exam_class_id);
            if (positionClassID != -1) {
                spinner_exam_class_id_update.setSelection(positionClassID);
            } else {
                Toast.makeText(this, "No data from class_id", Toast.LENGTH_SHORT).show();
            }

            ArrayList<String> subject_id = db.getAllNamesSubject_id();
            ArrayAdapter<String> ad_subjectid = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subject_id);
            ad_subjectid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_exam_subject_id_update.setAdapter(ad_subjectid);

            int positionSubjectId = subject_id.indexOf(exam_subject_id);
            if (positionSubjectId != -1) {
                spinner_exam_subject_id_update.setSelection(positionSubjectId);
            } else {
                Toast.makeText(this, "No data from subject_id", Toast.LENGTH_SHORT).show();
            }

            ArrayList<String> student_id = db.getAllNamesStudent_id();
            ArrayAdapter<String> ad_studentid = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, student_id);
            ad_studentid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_exam_student_id_update.setAdapter(ad_studentid);

            int positionStudentId = student_id.indexOf(exam_student_id);
            if (positionStudentId != -1) {
                spinner_exam_student_id_update.setSelection(positionStudentId);
            } else {
                Toast.makeText(this, "No data from student_id", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + exam_id + " ?");
        builder.setMessage("Are you sure you want to delete " + exam_id + " ?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            DataBase myDB = new DataBase(UpdateExamActivity.this);
            myDB.deleteOneRowExam(exam_id);
            // press yes close activity and comeback to main activity
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.create().show();
    }
}