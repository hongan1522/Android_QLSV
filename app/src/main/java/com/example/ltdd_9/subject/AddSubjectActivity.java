package com.example.ltdd_9.subject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

public class AddSubjectActivity extends AppCompatActivity {

    EditText ed_subject_name;
    Spinner spinner_classid_addsubject;
    Button btn_add_subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        ed_subject_name = findViewById(R.id.ed_subject_name);
        spinner_classid_addsubject = findViewById(R.id.spinner_classid_addsubject);
        btn_add_subject = findViewById(R.id.btn_add_subject);

        DataBase db = new DataBase(AddSubjectActivity.this);
        ArrayList<String> class_id = db.getAllNamesClass_id();

        ArrayAdapter<String> arrayAdapter = createCustomAdapter(class_id);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_classid_addsubject.setAdapter(arrayAdapter);

        btn_add_subject.setOnClickListener(view -> {
            try {
                String subject_name = ed_subject_name.getText().toString().trim();
                String subject_classid = spinner_classid_addsubject.getSelectedItem().toString().trim();

                if (subject_name.isEmpty() || subject_classid.equals(null)) {
                    Toast.makeText(AddSubjectActivity.this, "Enter a value correctly", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db.addSubject(subject_name, subject_classid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private ArrayAdapter<String> createCustomAdapter(ArrayList<String> items) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddSubjectActivity.this, android.R.layout.simple_spinner_item, items) {
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