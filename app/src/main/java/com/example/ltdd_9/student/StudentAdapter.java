package com.example.ltdd_9.student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd_9.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    private Context context;
    private ArrayList student_id,student_name,
            student_gender,student_email,student_classid, student_bir;
    Activity activity;
    Animation translate_anim;
    public StudentAdapter(Activity activity,
                          Context context,
                          ArrayList student_id,
                          ArrayList student_name,
                          ArrayList student_gender,
                          ArrayList student_email,
                          ArrayList student_classid,
                          ArrayList student_bir) {
        this.activity = activity;
        this.context = context;
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_gender = student_gender;
        this.student_email = student_email;
        this.student_classid = student_classid;
        this.student_bir = student_bir;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_student, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.student_id_txt.setText(String.valueOf(student_id.get(position)));
        holder.student_name_txt.setText(String.valueOf(student_name.get(position)));
        holder.student_email_txt.setText(String.valueOf(student_email.get(position)));
        holder.student_gender_txt.setText(String.valueOf(student_gender.get(position)));
        holder.student_classid_txt.setText(String.valueOf(student_classid.get(position)));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String bir = sdf.format(student_bir.get(position));
        holder.student_bir_txt.setText(bir);
        String gender = String.valueOf(student_gender.get(position));
        if (gender.equals("Male")) {
            holder.img_student_gender.setImageResource(R.drawable.male);
        } else {
            holder.img_student_gender.setImageResource(R.drawable.female);
        }
        holder.mainLayoutStudent.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateStudentActivity.class);
            intent.putExtra("student_id",String.valueOf(student_id.get(position)));
            intent.putExtra("student_name",String.valueOf(student_name.get(position)));
            intent.putExtra("student_gender",String.valueOf(student_gender.get(position)));
            intent.putExtra("student_classid",String.valueOf(student_classid.get(position)));
            intent.putExtra("student_email",String.valueOf(student_email.get(position)));
            intent.putExtra("student_birthday", bir);
            activity.startActivityForResult(intent,1);
        });
    }

    @Override
    public int getItemCount() {
        return student_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView student_id_txt,student_name_txt,student_bir_txt,student_email_txt,student_gender_txt,student_classid_txt;
        LinearLayout mainLayoutStudent;
        ImageView img_student_gender;
        public MyViewHolder(View itemView) {
            super(itemView);
            student_id_txt = itemView.findViewById(R.id.student_id_txt);
            student_name_txt = itemView.findViewById(R.id.student_name_txt);
            student_email_txt = itemView.findViewById(R.id.student_email_txt);
            student_gender_txt = itemView.findViewById(R.id.student_gender_txt);
            student_classid_txt = itemView.findViewById(R.id.classid_student_txt);
            student_bir_txt = itemView.findViewById(R.id.student_date_txt);
            img_student_gender = itemView.findViewById(R.id.img_student_gender);
            mainLayoutStudent = itemView.findViewById(R.id.mainLayoutStudent);
            // anime recycleview
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayoutStudent.setAnimation(translate_anim);
        }
    }
}
