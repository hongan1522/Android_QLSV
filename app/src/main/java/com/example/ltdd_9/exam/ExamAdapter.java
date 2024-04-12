package com.example.ltdd_9.exam;

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

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.MyViewHolder> {

    Activity activity;
    Context context;
    ArrayList exam_id,exam_class_id,exam_subject_id,exam_student_id,exam_mark;
    Animation translate_anim;

    public ExamAdapter(Activity activity, Context context, ArrayList exam_id,
                       ArrayList exam_class_id, ArrayList exam_subject_id,
                       ArrayList exam_student_id, ArrayList exam_mark) {
        this.activity = activity;
        this.context = context;
        this.exam_id = exam_id;
        this.exam_class_id = exam_class_id;
        this.exam_subject_id = exam_subject_id;
        this.exam_student_id = exam_student_id;
        this.exam_mark = exam_mark;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_exam, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.exam_id_txt.setText(String.valueOf(exam_id.get(position)));
        holder.exam_class_id_txt.setText(String.valueOf(exam_class_id.get(position)));
        holder.exam_subject_id_txt.setText(String.valueOf(exam_subject_id.get(position)));
        holder.exam_student_id_txt.setText(String.valueOf(exam_student_id.get(position)));
        holder.exam_mark_txt.setText(String.valueOf(exam_mark.get(position)));
        String result = String.valueOf((exam_mark.get(position)));
        String passMark = "5";
        if (result.equals(passMark) || Integer.parseInt(result) > Integer.parseInt(passMark)){
            holder.exam_result_txt.setText("Pass");
        }
        else if (Integer.parseInt(result) < Integer.parseInt(passMark)){
            holder.exam_result_txt.setText("Fail");
        }
        holder.exam_layout.setOnClickListener(v -> {
            Intent intent = new Intent(context,UpdateExamActivity.class);
            intent.putExtra("exam_id",String.valueOf(exam_id.get(position)));
            intent.putExtra("exam_class_id",String.valueOf(exam_class_id.get(position)));
            intent.putExtra("exam_subject_id",String.valueOf(exam_subject_id.get(position)));
            intent.putExtra("exam_student_id",String.valueOf(exam_student_id.get(position)));
            intent.putExtra("exam_mark",String.valueOf(exam_mark.get(position)));
            activity.startActivityForResult(intent,1);

        });
    }

    @Override
    public int getItemCount() {
        return exam_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView exam_id_txt, exam_class_id_txt, exam_subject_id_txt,
                exam_student_id_txt, exam_mark_txt, exam_result_txt;
        LinearLayout exam_layout;
        public MyViewHolder(View itemView) {
            super(itemView);
            exam_id_txt = itemView.findViewById(R.id.tv_exam_id);
            exam_class_id_txt = itemView.findViewById(R.id.tv_exam_class_id);
            exam_subject_id_txt = itemView.findViewById(R.id.tv_exam_subject_id);
            exam_student_id_txt = itemView.findViewById(R.id.tv_exam_student_id);
            exam_mark_txt = itemView.findViewById(R.id.tv_exam_mark);
            exam_result_txt = itemView.findViewById(R.id.tv_exam_result);
            exam_layout = itemView.findViewById(R.id.exam_layout);
            // anime recycleview
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            exam_layout.setAnimation(translate_anim);
        }

    }

}
