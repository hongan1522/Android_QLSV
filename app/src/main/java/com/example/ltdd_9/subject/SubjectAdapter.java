package com.example.ltdd_9.subject;

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

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder>{

    private Context context;
    private ArrayList subject_id, subject_name, subject_classid;
    Activity activity;
    Animation translate_anim;

    public SubjectAdapter(Activity activity,
                          Context context,
                          ArrayList subject_id,
                          ArrayList subject_name,
                          ArrayList subject_classid) {
        this.activity = activity;
        this.context = context;
        this.subject_id = subject_id;
        this.subject_name = subject_name;
        this.subject_classid = subject_classid;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_subject, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        holder.subject_id_txt.setText(String.valueOf(subject_id.get(position)));
        holder.subject_name_txt.setText(String.valueOf(subject_name.get(position)));
        holder.subject_classid_txt.setText(String.valueOf(subject_classid.get(position)));
        holder.mainLayoutSubject.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateSubjectActivity.class);
            intent.putExtra("subject_id",String.valueOf(subject_id.get(position)));
            intent.putExtra("subject_classid",String.valueOf(subject_classid.get(position)));
            intent.putExtra("subject_name",String.valueOf(subject_name.get(position)));
            activity.startActivityForResult(intent,1);
        });
    }

    @Override
    public int getItemCount() {
        return subject_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject_id_txt,subject_name_txt,subject_classid_txt;
        LinearLayout mainLayoutSubject;
        public MyViewHolder(View itemView) {
            super(itemView);
            subject_id_txt = itemView.findViewById(R.id.tv_subject_id);
            subject_name_txt = itemView.findViewById(R.id.tv_subject_name);
            subject_classid_txt = itemView.findViewById(R.id.tv_subject_classid);
            mainLayoutSubject = itemView.findViewById(R.id.mainLayoutSubject);
            // anime recycleview
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayoutSubject.setAnimation(translate_anim);
        }
    }
}
