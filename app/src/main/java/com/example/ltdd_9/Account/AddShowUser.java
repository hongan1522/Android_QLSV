package com.example.ltdd_9.Account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd_9.Account.adapter.apdapterUser;
import com.example.ltdd_9.Account.dao.UserDAO;
import com.example.ltdd_9.Account.model.Account;
import com.example.ltdd_9.AdminActivity;
import com.example.ltdd_9.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AddShowUser extends AppCompatActivity implements apdapterUser.ItemListener {
    private RecyclerView recyclerView;
    private SearchView searchView;
    private apdapterUser adapter;
    private FloatingActionButton floatingActionButton,floatingActionButtonHome;
    private List<Account> accountList;
    private UserDAO userDAO;
    public static AddShowUser addShowUserInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_show_user);
        addShowUserInstance = this;
        recyclerView = findViewById(R.id.recycleViewUser);
        searchView = findViewById(R.id.searchUser);
        floatingActionButton = findViewById(R.id.fabUser);
        floatingActionButtonHome = findViewById(R.id.fabHome);

        userDAO = new UserDAO(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddShowUser.this,AddUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        floatingActionButtonHome.hide();
        floatingActionButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddShowUser.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

        accountList = userDAO.getAllAccounts();
        adapter = new apdapterUser();
        adapter.setList(accountList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setItemListener(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchText = newText.toLowerCase().trim();
                List<Account> filteredList = userDAO.searchAccounts(searchText);
                adapter.setList(filteredList);

                return true;
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Account account = adapter.getAccount(position);
        if (account != null) {
            Intent intent = new Intent(getApplicationContext(), UpdateDeleteUser.class);
            intent.putExtra("account", account);
            startActivity(intent);
        } else {
            // Xử lý trường hợp khi tài khoản là null
            Toast.makeText(this, "Username doesn't exist.", Toast.LENGTH_SHORT).show();
        }
    }
    public void updateAccountRole(int accountId, int newRole) {
        userDAO.updateRole(accountId, newRole); // Gọi phương thức updateRole của UserDAO
        adapter.notifyDataSetChanged(); // Cập nhật lại RecyclerView
        Log.i("AddShowUser", "Updated role for account with ID " + accountId + " to " + newRole);
    }

    @Override
    protected void onResume() {
        super.onResume();


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("newAccount")) {
            Account newAccount = intent.getParcelableExtra("newAccount");
            if (newAccount != null) {
                // Thêm tài khoản mới vào danh sách và cập nhật RecyclerView
                accountList.add(newAccount);
                adapter.notifyDataSetChanged();
            }
        } else if (intent != null && intent.hasExtra("deletedAccountId")) {
            int deletedAccountId = intent.getIntExtra("deletedAccountId", -1);
            Log.i("DELETE ACC: ", String.valueOf(deletedAccountId));
            adapter.notifyDataSetChanged();
            if (deletedAccountId != -1) {
                // Xóa tài khoản khỏi danh sách và cập nhật RecyclerView
                deleteAccountAndUpdateRecyclerView(deletedAccountId);
                adapter.notifyDataSetChanged();
            }
        }
    }
    private void deleteAccountAndUpdateRecyclerView(int accountId) {
        // Xóa tài khoản khỏi cơ sở dữ liệu
        boolean isDeleted = userDAO.deleteAccount(accountId);
        adapter.notifyDataSetChanged();
        if (isDeleted) {
            // Xóa tài khoản khỏi danh sách
            for (int i = 0; i < accountList.size(); i++) {
                if (accountList.get(i).getId() == accountId) {
                    accountList.remove(i);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
            // Cập nhật RecyclerView
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Delete successfully.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Delete fail.", Toast.LENGTH_SHORT).show();
        }
    }
//    public void updateAccountRole(int accountId, int newRole) {
//        for (Account account : accountList) {
//            if (account.getId() == accountId) {
//                account.setRole(newRole);
//                adapter.notifyDataSetChanged();
//                break;
//            }
//        }
//    }

}
