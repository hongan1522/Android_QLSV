package com.example.ltdd_9;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.ltdd_9.Account.model.Account;

import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    public static final String COLUMN_USER_ROLE = "role";
    private Context context;
    private static final String DATABASE_NAME = "StudentManager.db";
    private static final int DATABASE_VERSION = 1;

    //USER
    public static final String TABLE_USER = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_NAME = "name";

    //STUDENT
    public static final String TABLE_STUDENT = "student";
    public static final String COLUMN_STUDENT_ID = "id";
    public static final String COLUMN_STUDENT_BIRTHDAY = "student_birthday";
    public static final String COLUMN_STUDENT_NAME = "student_name" ;
    public static final String COLUMN_STUDENT_GENDER = "student_gender";
    public static final String COLUMN_STUDENT_EMAIL = "student_email";

    //CLASS
    public static final String TABLE_CLASS = "class";
    public static final String COLUMN_CLASS_ID = "class_id" ;
    public static final String COLUMN_CLASS_NAME = "class_name" ;

    //SUBJECT
    private static final String TABLE_SUBJECT = "subject";
    private static final String COLUMN_SUBJECT_ID = "subject_id";
    private static final String COLUMN_SUBJECT_NAME = "subject_name";
    //EXAM
    public static final String TABLE_EXAM = "exam";
    public static final String COLUMN_EXAM_ID = "exam_id";
    public static final String COLUMN_EXAM_MARK = "exam_mark";
    //LECTURER
    private static final String TABLE_LECTURER = "lecturer";
    private static final String COLUMN_LECTURER_ID = "lecturer_id";
    private static final String COLUMN_LECTURER_NAME = "lecturer_name";



    public DataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    // this is called the first time a database is accessed. There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_STUDENT +
                " (" + COLUMN_STUDENT_ID + " TEXT PRIMARY KEY, " + COLUMN_STUDENT_NAME + " TEXT, " +
                COLUMN_STUDENT_GENDER + " TEXT, " + COLUMN_STUDENT_EMAIL + " TEXT, " +
                COLUMN_STUDENT_BIRTHDAY + " DATE, " +
                COLUMN_CLASS_ID + " TEXT, FOREIGN KEY (" + COLUMN_CLASS_ID + " ) REFERENCES " +
                TABLE_CLASS + " (" + COLUMN_CLASS_ID + "))";
        db.execSQL(query);
        String query_class = "CREATE TABLE " + TABLE_CLASS +
                " (" + COLUMN_CLASS_ID + " TEXT PRIMARY KEY, " + COLUMN_CLASS_NAME + " TEXT)";
        db.execSQL(query_class);

        String query_subject = "CREATE TABLE " + TABLE_SUBJECT +
                " (" + COLUMN_SUBJECT_ID + " TEXT PRIMARY KEY, " +
                COLUMN_SUBJECT_NAME + " TEXT, " +
                COLUMN_CLASS_ID + " TEXT, " +
                "FOREIGN KEY (" + COLUMN_CLASS_ID + ") REFERENCES " + TABLE_CLASS + " (" + COLUMN_CLASS_ID + "))";
        db.execSQL(query_subject);
        String query_account = "CREATE TABLE " + TABLE_USER + "(" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_EMAIL + " TEXT," +
                COLUMN_USER_USERNAME + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT, " + COLUMN_USER_ROLE + " INTEGER)";
        db.execSQL(query_account);
        String query_exam = "CREATE TABLE " + TABLE_EXAM + "( " + COLUMN_EXAM_ID + " TEXT PRIMARY KEY, " +
                COLUMN_CLASS_ID + " TEXT, " + COLUMN_SUBJECT_ID + " TEXT, "+ COLUMN_STUDENT_ID +" TEXT,"+ COLUMN_EXAM_MARK + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_CLASS_ID + ") REFERENCES " + TABLE_CLASS + " (" + COLUMN_CLASS_ID + "), "
                + "FOREIGN KEY (" + COLUMN_STUDENT_ID + ") REFERENCES " + TABLE_STUDENT + " (" + COLUMN_STUDENT_ID + "), "
                + "FOREIGN KEY (" + COLUMN_SUBJECT_ID + ") REFERENCES " + TABLE_SUBJECT + " (" + COLUMN_SUBJECT_ID + "))";
        db.execSQL(query_exam);

        String query_lecturer = "CREATE TABLE " + TABLE_LECTURER + "( " + COLUMN_LECTURER_ID + " TEXT PRIMARY KEY, " +
                COLUMN_LECTURER_NAME + " TEXT, " +
                COLUMN_CLASS_ID + " TEXT, " + COLUMN_SUBJECT_ID + " TEXT, id TEXT, "
                + "FOREIGN KEY (" + COLUMN_CLASS_ID + ") REFERENCES " + TABLE_CLASS + " (" + COLUMN_CLASS_ID + "), "
                + "FOREIGN KEY (" + COLUMN_SUBJECT_ID + ") REFERENCES " + TABLE_SUBJECT + " (" + COLUMN_SUBJECT_ID + "), "
                + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + " (" + COLUMN_USER_ID + "))";
        db.execSQL(query_lecturer);
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, "admin");
        values.put(COLUMN_USER_EMAIL, "admin@gmail.com");
        values.put(COLUMN_USER_USERNAME, "admin");
        values.put(COLUMN_USER_PASSWORD, "admin");
        values.put(COLUMN_USER_ROLE, 1);
        db.insert(TABLE_USER, null, values);
        ContentValues values_class = new ContentValues();
        values_class.put(COLUMN_CLASS_ID, "DH1");
        values_class.put(COLUMN_CLASS_NAME, "CNTT");
        db.insert(TABLE_CLASS, null, values_class);
        ContentValues values_subject = new ContentValues();
        values_subject.put(COLUMN_SUBJECT_ID, "MH1");
        values_subject.put(COLUMN_SUBJECT_NAME, "LTDD");
        values_subject.put(COLUMN_CLASS_ID, "DH1");
        db.insert(TABLE_SUBJECT, null, values_subject);
        ContentValues values_student = new ContentValues();
        values_student.put(COLUMN_STUDENT_ID, "SV1");
        values_student.put(COLUMN_STUDENT_NAME, "SinhVien");
        values_student.put(COLUMN_STUDENT_EMAIL, "sv@gmail.com");
        values_student.put(COLUMN_STUDENT_BIRTHDAY, "10/01/2002");
        values_student.put(COLUMN_STUDENT_GENDER, "Male");
        values_student.put(COLUMN_CLASS_ID,"DH1");
        db.insert(TABLE_STUDENT, null, values_student);
        ContentValues values_lecturer = new ContentValues();
        values_lecturer.put(COLUMN_LECTURER_ID, "GV1");
        values_lecturer.put(COLUMN_LECTURER_NAME, "NguyenVanA");
        values_lecturer.put(COLUMN_SUBJECT_ID, "MH1");
        values_lecturer.put(COLUMN_CLASS_ID,"DH1");
        values_lecturer.put(COLUMN_USER_ID,"1");
        db.insert(TABLE_LECTURER, null, values_lecturer);

    }
    // this is called if the database version number changes. It prevents previous users app from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAM);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LECTURER);
        onCreate(db);
    }

    //TABLE_STUDENT
    private boolean isStudentIdExist(String studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_STUDENT_ID + " = ?", new String[]{studentId});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public int getStudentCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_STUDENT, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public void addStudent(String name, Date bir, String gender, String email, String class_Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int studentCount = getStudentCount();
        String studentId = "SV" + (studentCount + 1);

        while (isStudentIdExist(studentId)) {
            studentCount++;
            studentId = "SV" + (studentCount + 1);
        }

        cv.put(COLUMN_STUDENT_ID, studentId);
        cv.put(COLUMN_STUDENT_NAME, name);
        cv.put(COLUMN_STUDENT_BIRTHDAY, bir.getTime());
        cv.put(COLUMN_STUDENT_EMAIL, email);
        cv.put(COLUMN_STUDENT_GENDER, gender);
        cv.put(COLUMN_CLASS_ID, class_Id);

        long result = db.insert(TABLE_STUDENT, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }
    }


    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_STUDENT;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }





    public void updateData(String row_id, String name, Date bir, String gender, String email,String student_class_id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENT_NAME,name);
        cv.put(COLUMN_STUDENT_BIRTHDAY, String.valueOf(bir));
        cv.put(COLUMN_STUDENT_EMAIL,email);
        cv.put(COLUMN_CLASS_ID,student_class_id);
        if (gender.equals("Male") || gender.equals("Female")) {
            cv.put(COLUMN_STUDENT_GENDER, gender);
        }
        long result = db.update(TABLE_STUDENT, cv, COLUMN_USER_ID + "=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_STUDENT, COLUMN_USER_ID + "=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }
    
    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " +TABLE_STUDENT);
    }

    //TABLE_CLASS

    public ArrayList<String> getAllNamesClass_id_update() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            // Add "Choose option" as the first item in the list
            list.add(0, "Class_" + COLUMN_USER_ID);

            String selectQuery = "SELECT * FROM " + TABLE_CLASS;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("class_id" ));
                    list.add(name);
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return list;
    }
    private boolean isClassIdExist(String classtId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CLASS + " WHERE " + COLUMN_CLASS_ID + " = ?", new String[]{classtId});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public int getClassCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_CLASS, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public void addClass(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int classCount = getClassCount();
        String classId = "DH" + (classCount + 1);

        while (isClassIdExist(classId)) {
            classCount++;
            classId = "DH" + (classCount + 1);
        }

        cv.put(COLUMN_CLASS_ID, classId);
        cv.put(COLUMN_CLASS_NAME, name);
        long result = db.insert(TABLE_CLASS, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Add successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllDataClass() {
        String query = "SELECT * FROM " + TABLE_CLASS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateDataClass(String row_id, String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CLASS_NAME,name);
        long result = db.update(TABLE_CLASS, cv, "class_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRowClass(String class_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_CLASS, "class_id=?", new String[]{class_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllDataClass(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " +TABLE_CLASS);
    }


    //TABLE SUBJECT
    private boolean isSubjectIdExist(String subjectId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SUBJECT + " WHERE " + COLUMN_SUBJECT_ID + " = ?", new String[]{subjectId});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public int getSubjectCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_SUBJECT, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public void addSubject(String name, String class_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int subjectCount = getSubjectCount();
        String subjectId = "MH" + (subjectCount + 1);

        while (isSubjectIdExist(subjectId)) {
            subjectCount++;
            subjectId = "MH" + (subjectCount + 1);
        }

        cv.put(COLUMN_SUBJECT_ID, subjectId);
        cv.put(COLUMN_SUBJECT_NAME, name);
        cv.put(COLUMN_CLASS_ID, class_id);
        long result = db.insert(TABLE_SUBJECT, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Add successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllDataSubject() {
        String query = "SELECT * FROM " + TABLE_SUBJECT;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateDataSubject(String subject_id, String name, String sb_classId){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SUBJECT_NAME,name);
        cv.put(COLUMN_CLASS_ID, sb_classId);
        long result = db.update(TABLE_SUBJECT, cv, "subject_" + COLUMN_USER_ID + "=?", new String[]{subject_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRowSubject(String subject_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_SUBJECT, "subject_" + COLUMN_USER_ID + "=?", new String[]{subject_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllDataSubject(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " +TABLE_SUBJECT);
    }

    public int getUserCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USER, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }
    //EXAM
    private boolean isExamIdExist(String exam_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXAM + " WHERE " + COLUMN_EXAM_ID + " = ?", new String[]{exam_id});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public int getExamCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_EXAM, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }
    @SuppressLint("Range")
    public double getAverageExamMark() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT AVG( "+ COLUMN_EXAM_MARK+" ) AS average_mark FROM " +TABLE_EXAM, null);
        if (cursor.moveToFirst()) {
            return cursor.getDouble(cursor.getColumnIndex("average_mark"));
        }
        return -1; // Error handling, in case the query fails
    }
    public void addExam(String exam_mark, String class_id, String subject_id, String student_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int ExamCount = getExamCount();
        String examId = "EXAM" + (ExamCount +1);

        while (isExamIdExist(examId)){
            ExamCount++;
            examId= "EXAM" + (ExamCount +1);
        }
        cv.put(COLUMN_EXAM_ID,examId);
        cv.put(COLUMN_CLASS_ID,class_id);
        cv.put(COLUMN_SUBJECT_ID,subject_id);
        cv.put(COLUMN_EXAM_MARK,exam_mark);
        cv.put(COLUMN_STUDENT_ID,student_id);
        long result = db.insert(TABLE_EXAM,null,cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor readAllDataExam(){
        String query = "SELECT * FROM " + TABLE_EXAM;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
    public void updateExam(String row_id, String exam_class_id, String exam_subject_id, String exam_student_id, String exam_mark){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CLASS_ID, exam_class_id);
        cv.put(COLUMN_SUBJECT_ID,exam_subject_id);
        cv.put(COLUMN_STUDENT_ID,exam_student_id);
        cv.put(COLUMN_EXAM_MARK,exam_mark);
        long result = db.update(TABLE_EXAM, cv, COLUMN_EXAM_ID + "=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteOneRowExam(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_EXAM, COLUMN_EXAM_ID + "=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllDataExam(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " +TABLE_EXAM);
    }
    //Statistic


    //LECTURER
    private boolean isLecturerIdExist(String lecturer_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LECTURER + " WHERE " + COLUMN_LECTURER_ID + " = ?", new String[]{lecturer_id});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    private int getLecturerCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_LECTURER, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public void addLecturer(String name, String class_id, String subject_id, String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int LecturerCount = getLecturerCount();
        String lecturerId = "GV" + (LecturerCount +1);

        while (isLecturerIdExist(lecturerId)){
            LecturerCount++;
            lecturerId= "GV" + (LecturerCount +1);
        }
        cv.put(COLUMN_LECTURER_ID,lecturerId);
        cv.put(COLUMN_LECTURER_NAME, name);
        cv.put(COLUMN_CLASS_ID,class_id);
        cv.put(COLUMN_SUBJECT_ID,subject_id);
        cv.put(COLUMN_USER_ID,user_id);
        long result = db.insert(TABLE_LECTURER,null,cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllDataLecturer(){
        String query = "SELECT * FROM " + TABLE_LECTURER;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateDataLecturer(String lecturer_id, String name, String class_id, String subject_id, String user_id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CLASS_ID,class_id);
        cv.put(COLUMN_LECTURER_NAME, name);
        cv.put(COLUMN_SUBJECT_ID, subject_id);
        cv.put(COLUMN_USER_ID, user_id);
        long result = db.update(TABLE_LECTURER, cv, "lecturer_id=?", new String[]{lecturer_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRowLecturer(String lecturer_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_LECTURER, "lecturer_id=?", new String[]{lecturer_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllDataLecturer(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " +TABLE_LECTURER);
    }
    //GET FOREIGN KEY
    public ArrayList<String> getAllNamesClass_id() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String selectQuery = "SELECT * FROM " + TABLE_CLASS;
            cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("class_id" ));
                    list.add(name);
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return list;
    }

    public ArrayList<String> getAllNamesSubject_id() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            String selectQuery = "SELECT * FROM " + TABLE_SUBJECT;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("subject_id"));
                    list.add(name);
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return list;
    }
    public ArrayList<String> getAllNamesStudent_id() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            String selectQuery = "SELECT * FROM " + TABLE_STUDENT;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID));
                    list.add(name);
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return list;
    }
    public ArrayList<String> getAllNamesUser_id() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String selectQuery = "SELECT * FROM " + TABLE_USER;
            cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("id" ));
                    list.add(name);
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return list;
    }
}
