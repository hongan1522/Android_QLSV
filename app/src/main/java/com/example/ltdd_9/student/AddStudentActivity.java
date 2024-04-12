package com.example.ltdd_9.student;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddStudentActivity extends AppCompatActivity {

    EditText et_student_name, et_student_bir, et_student_email;
    Button add_button;
    Spinner spinner_classid_addstudent;
    RadioGroup rg_student_gender;
    RadioButton rb_student_male, rb_student_female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        et_student_name = findViewById(R.id.ed_student_name);
        et_student_bir = findViewById(R.id.ed_student_bir);
        et_student_email = findViewById(R.id.ed_student_email);
        add_button = findViewById(R.id.add_button_student);
        spinner_classid_addstudent = findViewById(R.id.spinner_classid_addstudent);
        rg_student_gender = findViewById(R.id.rg_student_gender);
        rb_student_female = findViewById(R.id.rb_student_female);
        rb_student_male = findViewById(R.id.rb_student_male);


        DataBase myDB = new DataBase(AddStudentActivity.this);
        ArrayList<String> class_id = myDB.getAllNamesClass_id();

        // Create an ArrayAdapter and set it to the spinner
        ArrayAdapter<String> arrayAdapter = createCustomAdapter(class_id);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_classid_addstudent.setAdapter(arrayAdapter);

        //Radio Group
        rg_student_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rb_student_female) {
                    rb_student_male.setChecked(false);
                } else {
                    rb_student_male.setChecked(true);
                }
            }
        });

        add_button.setOnClickListener(v -> {
            try {
                String student_name_input = et_student_name.getText().toString().trim();
                String student_mail_input = et_student_email.getText().toString().trim();
                String student_gender_input = rb_student_male.isChecked() ? "Male" : "Female";
                String student_classid = spinner_classid_addstudent.getSelectedItem().toString().trim();
                String student_bir_input = et_student_bir.getText().toString().trim();

                if (student_name_input.isEmpty() || student_bir_input.isEmpty() || student_mail_input.isEmpty() || !isValidEmail(student_mail_input) || student_classid.equals("Class_id") || !isValidDate(student_bir_input)) {
                    Toast.makeText(AddStudentActivity.this, "Enter a value correctly", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date birthdayDate = sdf.parse(student_bir_input);

                        java.sql.Date birthdaySqlDate = new java.sql.Date(birthdayDate.getTime());

                        if (birthdaySqlDate != null) {
                            myDB.addStudent(student_name_input, birthdaySqlDate, student_gender_input, student_mail_input, student_classid);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidDate(String bir) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            Date birthday = sdf.parse(bir);
            Calendar calBir = Calendar.getInstance();
            calBir.setTime(birthday);

            int year = calBir.get(Calendar.YEAR);
            int month = calBir.get(Calendar.MONTH) + 1;
            int day = calBir.get(Calendar.DAY_OF_MONTH);

            if (year >= 1990 && year <= Calendar.getInstance().get(Calendar.YEAR) && month >= 1 && month <= 12 && day >= 1 && day <= 31) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }

    private ArrayAdapter<String> createCustomAdapter(ArrayList<String> items) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddStudentActivity.this, android.R.layout.simple_spinner_item, items) {
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