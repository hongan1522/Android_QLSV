package com.example.ltdd_9.lecturer;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;

import java.util.ArrayList;

public class AddLecturerActivity extends AppCompatActivity {

    Spinner sp_add_classid_lecturer, sp_add_subjectid_lecturer, spinner_userid_add_lecturer;
    Button btn_add_lecturer;
    TextView tv_visible_classid_lecturer, tv_visible_subjectid_lecturer, textView_visiblenone_lecturer_userid;
    EditText ed_add_lecture_name;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecturer);

        ed_add_lecture_name = findViewById(R.id.ed_lecturer_name);
        sp_add_classid_lecturer = findViewById(R.id.spinner_classid_add_lecturer);
        sp_add_subjectid_lecturer = findViewById(R.id.spinner_subjectid_add_lecturer);
        spinner_userid_add_lecturer = findViewById(R.id.spinner_userid_add_lecturer);
        btn_add_lecturer = findViewById(R.id.btn_add_lucturer);
        tv_visible_classid_lecturer = findViewById(R.id.textView_visiblenone_lecturer_classid);
        tv_visible_subjectid_lecturer = findViewById(R.id.textView_visiblenone_lecturer_subjectid);
        textView_visiblenone_lecturer_userid = findViewById(R.id.textView_visiblenone_lecturer_userid);
        db = new DataBase(AddLecturerActivity.this);
        ArrayList<String> class_id = db.getAllNamesClass_id();
        ArrayAdapter<String> ad_classid = createCustomAdapter(class_id);
        ad_classid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_add_classid_lecturer.setAdapter(ad_classid);
        sp_add_classid_lecturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                tv_visible_classid_lecturer.setText(class_id.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddLecturerActivity.this, "Select class_id", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<String> subject_id = db.getAllNamesSubject_id();
        ArrayAdapter<String> ad_subjectid = createCustomAdapter(subject_id);
        ad_subjectid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_add_subjectid_lecturer.setAdapter(ad_subjectid);
        sp_add_subjectid_lecturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv_visible_subjectid_lecturer.setText(subject_id.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddLecturerActivity.this, "Select subject_id", Toast.LENGTH_SHORT).show();
            }
        });
        ArrayList<String> user_id = db.getAllNamesUser_id();
        ArrayAdapter<String> ad_userid = createCustomAdapter(user_id);
        ad_userid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_userid_add_lecturer.setAdapter(ad_userid);
        spinner_userid_add_lecturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textView_visiblenone_lecturer_userid.setText(user_id.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddLecturerActivity.this, "Select user_id", Toast.LENGTH_SHORT).show();
            }
        });
        btn_add_lecturer.setOnClickListener(view -> {
            try {
                String lecturer_subjectid = sp_add_subjectid_lecturer.getSelectedItem().toString().trim();
                String lecturer_classid = sp_add_classid_lecturer.getSelectedItem().toString().trim();
                String lecturer_userid = spinner_userid_add_lecturer.getSelectedItem().toString().trim();
                String lecture_name = ed_add_lecture_name.getText().toString().trim();

                if (lecturer_subjectid.equals(null) || lecturer_classid.equals(null) ||lecturer_userid.equals(null)) {
                    Toast.makeText(AddLecturerActivity.this, "Enter a value correctly", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db.addLecturer(lecture_name, lecturer_classid, lecturer_subjectid,lecturer_userid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private ArrayAdapter<String> createCustomAdapter(ArrayList<String> items) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddLecturerActivity.this, android.R.layout.simple_spinner_item, items) {
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
}