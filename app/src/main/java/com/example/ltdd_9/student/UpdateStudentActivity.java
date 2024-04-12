package com.example.ltdd_9.student;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class UpdateStudentActivity extends AppCompatActivity {

    EditText et_student_name, et_student_bir, et_student_email,et_student_gender;
    Spinner spinner_student_classid;
    RadioGroup rg_student_gender;
    RadioButton rb_male, rb_female;
    Button update_button,delete_button;
    String id,name,bir,email,gender,student_classid;
    DataBase myDB = new DataBase(UpdateStudentActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        et_student_name = findViewById(R.id.ed_student_name2);
        et_student_bir = findViewById(R.id.ed_student_bir2);
        et_student_email = findViewById(R.id.ed_student_email2);
        spinner_student_classid = findViewById(R.id.spinner_classid_updatestudent);
        rg_student_gender = findViewById(R.id.rg_student_gender2);
        rb_female = findViewById(R.id.rb_student_female2);
        rb_male = findViewById(R.id.rb_student_male2);
        //first we call
        getIntentData();
        //set actionbar name after getIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name); }

        //Radio Button
        rg_student_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rb_student_female2) {
                    rb_male.setChecked(false);
                } else {
                    rb_male.setChecked(true);
                }
            }
        });

        update_button = findViewById(R.id.update_button_student);
        update_button.setOnClickListener(v -> {
            DataBase myDB1 = new DataBase(UpdateStudentActivity.this);

            String name = et_student_name.getText().toString().trim();
            String bir = et_student_bir.getText().toString().trim();
            String email = et_student_email.getText().toString().trim();
            String gender = rb_male.isChecked() ? "Male" : "Female";
            String student_classid = spinner_student_classid.getSelectedItem().toString().trim();

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date birDate = sdf.parse(bir);

                java.sql.Date birthday = new java.sql.Date(birDate.getTime());

                if (!name.isEmpty() || !bir.isEmpty() || !email.isEmpty() || !isValidEmail(email) || student_classid.equals("Class_id") || !isValidDate(bir)) {
                    myDB1.updateData(id, name, birthday, gender, email, student_classid);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        delete_button = findViewById(R.id.delete_button_student);
        delete_button.setOnClickListener(v -> {
            confirmDialog();
        });

    }

    void getIntentData(){
        if(getIntent().hasExtra("student_id")
                && getIntent().hasExtra("student_name")
                && getIntent().hasExtra("student_birthday")
                && getIntent().hasExtra("student_gender")
                && getIntent().hasExtra("student_email")
                && getIntent().hasExtra("student_classid")
        )
        {
            id = getIntent().getStringExtra("student_id");
            name = getIntent().getStringExtra("student_name");
            bir = getIntent().getStringExtra("student_birthday");
            gender = getIntent().getStringExtra("student_gender");
            email = getIntent().getStringExtra("student_email");
            student_classid = getIntent().getStringExtra("student_classid");

            et_student_name.setText(name);
            et_student_bir.setText(bir);
            et_student_email.setText(email);

            if (gender.equals("Male")) {
                rb_male.setChecked(true);
            } else {
                rb_female.setChecked(true);
            }

            ArrayList<String> class_id = myDB.getAllNamesClass_id();
            ArrayAdapter<String> ad_classid = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, class_id);
            ad_classid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_student_classid.setAdapter(ad_classid);

            int positionClassID = class_id.indexOf(student_classid);
            if (positionClassID != -1) {
                spinner_student_classid.setSelection(positionClassID);
            } else {
                Toast.makeText(this, "No data from class_id", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
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

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            DataBase myDB = new DataBase(UpdateStudentActivity.this);
            myDB.deleteOneRow(id);
            // press yes close activity and comeback to main activity
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.create().show();
    }
}