package com.example.ltdd_9.statistic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ltdd_9.DataBase;
import com.example.ltdd_9.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {
    TextView user_count,student_count,
            subject_count,mark_average,class_count,exam_count;
    PieChart pieChart;
    DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        user_count = findViewById(R.id.user_count);

        student_count = findViewById(R.id.student_count);
        subject_count = findViewById(R.id.subject_count);
        mark_average = findViewById(R.id.mark_average);
        class_count = findViewById(R.id.class_count);
        exam_count = findViewById(R.id.exam_count);
        pieChart = findViewById(R.id.piechart);
        db = new DataBase(StatisticActivity.this);
        int countFromStudent = db.getStudentCount();
        student_count.setText("Students: "+countFromStudent);
        int countFromClass = db.getClassCount();
        class_count.setText("Classes: " +countFromClass);
        int countFromExam = db.getExamCount();
        exam_count.setText("Exams: "+countFromExam);
        int countFromSubject = db.getSubjectCount();
        subject_count.setText("Subjects: "+countFromSubject);
        int countFromUser = db.getUserCount();
        user_count.setText("Users: " + countFromUser);
        double avgMark = db.getAverageExamMark();
        mark_average.setText("Mark avg: " +avgMark);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(countFromClass, "Class"));
        entries.add(new PieEntry(countFromStudent, "Student"));
        entries.add(new PieEntry(countFromExam, "Exam"));
        entries.add(new PieEntry(countFromSubject, "Subject"));
        entries.add(new PieEntry(countFromUser, "User"));
        PieDataSet dataSet = new PieDataSet(entries, "Category");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS); // Set the colors for the different sections
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.setDescription(null); // Hide the description label
        pieChart.invalidate(); // Refresh the pie chart with new data
    }
}