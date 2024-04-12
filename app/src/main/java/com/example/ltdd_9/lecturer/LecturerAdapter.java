package com.example.ltdd_9.lecturer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd_9.R;

import java.util.ArrayList;

public class LecturerAdapter extends RecyclerView.Adapter<LecturerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList lecturer_id, lecturer_classid, lecturer_subjectid, lecturer_name, lecturer_userid;
    Activity activity;
    Animation translate_anim;

    public LecturerAdapter(Activity activity,
                           Context context,
                           ArrayList lecturer_id,
                           ArrayList lecturer_name,
                           ArrayList lecturer_classid,
                           ArrayList lecturer_subjectid,
                           ArrayList lecturer_userid
    ) {
        this.activity = activity;
        this.context = context;
        this.lecturer_id = lecturer_id;
        this.lecturer_classid = lecturer_classid;
        this.lecturer_subjectid = lecturer_subjectid;
        this.lecturer_name = lecturer_name;
        this.lecturer_userid = lecturer_userid;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_lecturer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        holder.subject_id_txt.setText(String.valueOf(lecturer_subjectid.get(position)));
        holder.lecturer_id_txt.setText(String.valueOf(lecturer_id.get(position)));
        holder.class_id_txt.setText(String.valueOf(lecturer_classid.get(position)));
        holder.lecture_name_txt.setText(String.valueOf(lecturer_name.get(position)));
        holder.lecturer_user_id_txt.setText(String.valueOf(lecturer_userid.get(position)));
        holder.mainLayoutLecturer.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateLecturerActivity.class);
            intent.putExtra("lecturer_id",String.valueOf(lecturer_id.get(position)));
            intent.putExtra("lecturer_classid",String.valueOf(lecturer_classid.get(position)));
            intent.putExtra("lecturer_name",String.valueOf(lecturer_name.get(position)));
            intent.putExtra("lecturer_subjectid",String.valueOf(lecturer_subjectid.get(position)));
            intent.putExtra("lecturer_userid",String.valueOf(lecturer_userid.get(position)));
            activity.startActivityForResult(intent,1);
        });
    }

    @Override
    public int getItemCount() {
        return lecturer_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView lecturer_id_txt, class_id_txt, subject_id_txt, lecture_name_txt,lecturer_user_id_txt;
        LinearLayout mainLayoutLecturer;
        public MyViewHolder( View itemView) {
            super(itemView);
            lecturer_id_txt = itemView.findViewById(R.id.tv_lecturer_id);
            subject_id_txt = itemView.findViewById(R.id.tv_lecturer_subjectid);
            class_id_txt = itemView.findViewById(R.id.tv_lecturer_classid);
            lecture_name_txt = itemView.findViewById(R.id.tv_lecturer_name);
            lecturer_user_id_txt = itemView.findViewById(R.id.tv_lecturer_userid);
            mainLayoutLecturer = itemView.findViewById(R.id.mainLayoutLecturer);
            // anime recycleview
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayoutLecturer.setAnimation(translate_anim);
        }
    }
}
