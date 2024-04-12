package com.example.ltdd_9.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.ltdd_9.subject.AddSubjectActivity;

import java.util.ArrayList;

public class AddExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);
        EditText ed_exam_mark = findViewById(R.id.ed_exam_mark);
        Spinner spinner_classid_addexam = findViewById(R.id.spinner_classid_addexam);
        Spinner spinner_subjectid_addexam = findViewById(R.id.spinner_subjectid_addexam);
        Spinner spinner_studentid_addexam = findViewById(R.id.spinner_studentid_addexam);
        TextView textView_visiblenone_exam_subjectid = findViewById(R.id.textView_visiblenone_exam_subjectid);
        TextView textView_visiblenone_exam_classid = findViewById(R.id.textView_visiblenone_exam_classid);
        TextView textView_visiblenone_exam_studentid = findViewById(R.id.textView_visiblenone_exam_studentid);
        Button btn_add_exam = findViewById(R.id.btn_add_exam);
        DataBase db = new DataBase(AddExamActivity.this);

        ArrayList<String> class_id = db.getAllNamesClass_id();
        ArrayAdapter<String> arrayAdapter = createCustomeAdapter(class_id);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_classid_addexam.setAdapter(arrayAdapter);

        spinner_classid_addexam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView_visiblenone_exam_classid.setText(class_id.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddExamActivity.this, "Select class_id", Toast.LENGTH_SHORT).show();
            }
        });
        ArrayList<String> subject_id = db.getAllNamesSubject_id();
        ArrayAdapter<String> arrayAdapter1 = createCustomeAdapter(subject_id);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_subjectid_addexam.setAdapter(arrayAdapter1);
        spinner_subjectid_addexam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView_visiblenone_exam_subjectid.setText(subject_id.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddExamActivity.this, "Select subject_id", Toast.LENGTH_SHORT).show();
            }
        });
        ArrayList<String> student_id = db.getAllNamesStudent_id();
        ArrayAdapter<String> arrayAdapter2 = createCustomeAdapter(student_id);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_studentid_addexam.setAdapter(arrayAdapter2);

        spinner_studentid_addexam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView_visiblenone_exam_studentid.setText(student_id.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddExamActivity.this, "Select student_id", Toast.LENGTH_SHORT).show();
            }
        });
        btn_add_exam.setOnClickListener(v -> {
            try {
                String exam_mark = ed_exam_mark.getText().toString().trim();
                String exam_class_id  = spinner_classid_addexam.getSelectedItem().toString().trim();
                String exam_subject_id = spinner_subjectid_addexam.getSelectedItem().toString().trim();
                String exam_student_id = spinner_studentid_addexam.getSelectedItem().toString().trim();
                if (exam_mark.isEmpty()||  exam_subject_id.equals("Subject_id")||exam_class_id.equals("Class_id")||exam_student_id.equals("Student_id")||!isValidateMark(exam_mark))
                {
                    Toast.makeText(AddExamActivity.this, "Enter a value correctly", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.addExam(exam_mark,exam_class_id,exam_subject_id,exam_student_id);
                }

            }
            catch (Exception e) {}
        });

    }

    private ArrayAdapter<String> createCustomeAdapter(ArrayList<String> item) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddExamActivity.this, android.R.layout.simple_spinner_item, item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextSize(19);
                textView.setTextColor(Color.GRAY);
                return textView;
            }
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }
    private boolean isValidateMark (String mark) {
        try {
            // Convert the input to a double
            double numericMark = Double.parseDouble(mark);
            // Check if the mark is within the valid range
            return numericMark >= 0 && numericMark <= 10;
        } catch (NumberFormatException e) {
            // If the input cannot be converted to a double, it's not a valid mark
            return false;
        }
    }
}