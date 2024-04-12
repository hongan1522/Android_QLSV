package com.example.ltdd_9.classes;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;


public class AddClassActivity extends AppCompatActivity {
    EditText ed_name;
    Button bt_add_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

    ed_name = findViewById(R.id.ed_class_name);
    bt_add_class = findViewById(R.id.add_button_class);

    DataBase db = new DataBase(AddClassActivity.this);
    bt_add_class.setOnClickListener(view -> {
       try {
           String class_name = ed_name.getText().toString().trim();
           if(class_name.isEmpty())
           {
               Toast.makeText(this, "Enter a value correctly", Toast.LENGTH_SHORT).show();
           }
           else
           {
               db.addClass(class_name);
           }

       }catch (Exception e)
       {

       }
    });
    }
}