package com.example.ltdd_9.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd_9.R;

import java.util.ArrayList;
import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private ArrayList class_id, class_name;
    Activity activity;
    Animation translate_anim;
    List<ClassModel> list;
    List<String> classList;
    List<String> classListAll;
    public ClassAdapter(Activity activity,
                          Context context,
                          ArrayList class_id,
                          ArrayList class_name) {
        this.activity = activity;
        this.context = context;
        this.class_id = class_id;
        this.class_name = class_name;
    }

    public ClassAdapter(Context context, Activity activity, List<ClassModel> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
    }

    public void filterList(List<ClassModel> filteredlist) {
        list = filteredlist;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_class, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_class_name.setText(String.valueOf(class_name.get(position)));
        holder.tv_class_id.setText(String.valueOf(class_id.get(position)));
        holder.mainLayoutClass.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateClassActivity.class);
            intent.putExtra("class_id",String.valueOf(class_id.get(position)));
            intent.putExtra("class_name",String.valueOf(class_name.get(position)));
            activity.startActivityForResult(intent,1);
        });
    }

    @Override
    public int getItemCount() {
        return class_id.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_class_id, tv_class_name;
        LinearLayout mainLayoutClass;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_class_id = itemView.findViewById(R.id.tv_class_id);
            tv_class_name = itemView.findViewById(R.id.tv_class_name);
            mainLayoutClass = itemView.findViewById(R.id.mainLayoutClass);
            // anime recycleview
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayoutClass.setAnimation(translate_anim);
        }
    }

}
