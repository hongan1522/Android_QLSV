package com.example.ltdd_9.Account.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import com.example.ltdd_9.Account.model.Account;
import com.example.ltdd_9.DataBase;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private DataBase dbHelper;
    public UserDAO(Context context) {
        dbHelper = new DataBase(context);
    }
    //kiểm tra username và password
    public boolean checkUsernamePassword(String username, String password){
        String sql = "SELECT * FROM users WHERE username = ? and password = ?";
        String[] args = {username,password};
        SQLiteDatabase st = dbHelper.getWritableDatabase();
        Cursor cursor=  st.rawQuery(sql,args);
        if(cursor.getCount()>0){
            return true;
        }
        else return false;
    }
    //lấy ra thông tin account sau khi check user, pass
    public Account login(String username, String password){
        Account account =null;
        String sql = "SELECT * FROM users WHERE username = ? and password = ?";
        String[] args = {username,password};
        SQLiteDatabase st = dbHelper.getWritableDatabase();
        Cursor cursor=  st.rawQuery(sql,args);
        if(cursor.moveToFirst()){
            account = new Account();
            account.setId(cursor.getInt(0));
            account.setName(cursor.getString(1));
            account.setEmail(cursor.getString(2));
            account.setUsername(cursor.getString(3));
        }
        return account;
    }
    //
    @SuppressLint("Range")
    public int getRoleByUsername(String username) {
        int role = -1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT role FROM users WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        if (cursor != null && cursor.moveToFirst()) {
            role = cursor.getInt(cursor.getColumnIndex("role"));
            cursor.close();
        }
        db.close();
        return role;
    }
    //kiểm tra username
    public boolean checkUsername(String username){
        String sql = "SELECT * FROM users WHERE username =? ";
        String[] args = {username};
        SQLiteDatabase st = dbHelper.getWritableDatabase();
        Cursor cursor = st.rawQuery(sql,args);
        if(cursor.getCount()>0){
            return true;
        }
        else  return false;
    }
    //add user
    public void addUser(Account account){
        String sql = "INSERT INTO users(name,email,username,password,role)" +
                "VALUES(?,?,?,?,?)";
        String[] args = {account.getName(),account.getEmail(),account.getUsername(),account.getPassword(), String.valueOf(account.getRole())};
        SQLiteDatabase st = dbHelper.getWritableDatabase();
        st.execSQL(sql,args);
    }
    //    public void addUserIsTeacher(Account account){
//        String sql = "INSERT INTO users(name,email,username,password,role, teacher_id)" +
//                "VALUES(?,?,?,?,?,?)";
//        String[] args = {account.getName(),account.getEmail(),account.getUsername(),account.getPassword(), String.valueOf(account.getRole()),String.valueOf(account.getTeacher_id())};
//        SQLiteDatabase st = getWritableDatabase();
//        st.execSQL(sql,args);
//    }
    //đổi mật khẩu
    public void changePassword(Account account){
        String sql ="UPDATE users SET password = ? WHERE username = ?";
        String[] args ={account.getPassword(),account.getUsername()};
        SQLiteDatabase st = dbHelper.getWritableDatabase();
        st.execSQL(sql,args);
    }
    public List<Account> getAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        if (cursor.moveToFirst()) {
            do {
                Account account = new Account();
                account.setId(cursor.getInt(0));
                account.setName(cursor.getString(1));
                account.setEmail(cursor.getString(2));
                account.setUsername(cursor.getString(3));
                account.setPassword(cursor.getString(4));
                account.setRole(cursor.getInt(5));
//                account.setId_teacher(cursor.getInt(6));
                accountList.add(account);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return accountList;
    }
    @SuppressLint("Range")
    public List<Account> searchAccounts(String keyword) {
        List<Account> accountList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"id", "name", "email", "username", "password", "role"}; // Các cột của bảng
        String selection = "name LIKE ? OR email LIKE ? OR username LIKE ?"; // Điều kiện tìm kiếm
        String[] selectionArgs = {"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"}; // Giá trị tìm kiếm
        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Account account = new Account();
                account.setId(cursor.getInt(cursor.getColumnIndex("id")));
                account.setName(cursor.getString(cursor.getColumnIndex("name")));
                account.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                account.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                account.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                account.setRole(cursor.getInt(cursor.getColumnIndex("role")));
                accountList.add(account);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return accountList;
    }

    public void updateAccount(Account account) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", account.getName());
        values.put("email", account.getEmail());
        values.put("username", account.getUsername());
        values.put("password", account.getPassword());
        values.put("role", account.getRole());
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(account.getId())};
        db.update("users", values, whereClause, whereArgs);
        db.close();
    }
    public void updateRole(int accountId, int newRole) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("role", newRole);
        db.update("users", values, "id = ?", new String[]{String.valueOf(accountId)});
        db.close();
    }
    public boolean deleteAccount(int accountId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(accountId)};
        int affectedRows = db.delete("users", whereClause, whereArgs);
        db.close();
        return affectedRows > 0;
    }
    public Account getUserByUsername(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {"id", "name", "username", "email", "password", "role"};
        String selection = "username=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

        Account user = null;

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int usernameIndex = cursor.getColumnIndex("username");
            int emailIndex = cursor.getColumnIndex("email");
            int passwordIndex = cursor.getColumnIndex("password");
            int roleIndex = cursor.getColumnIndex("role");

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String retrievedUsername = cursor.getString(usernameIndex);
            String email = cursor.getString(emailIndex);
            String password = cursor.getString(passwordIndex);
            int role = cursor.getInt(roleIndex);

            user = new Account(id, name, email, retrievedUsername, password, role);
        }

        cursor.close();
        db.close();

        return user;
    }

}
