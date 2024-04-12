package com.example.ltdd_9.Account.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd_9.Account.model.Account;
import com.example.ltdd_9.R;

import java.util.ArrayList;
import java.util.List;

public class apdapterUser extends RecyclerView.Adapter<apdapterUser.UserViewHolder> {
    private List<Account> accountList;
    private ItemListener itemListener;

    public apdapterUser() {
        accountList = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Account> accountList) {
        this.accountList = accountList;
        notifyDataSetChanged();
    }
    public void updateList(List<Account> accountList) {
        this.accountList = accountList;
        notifyDataSetChanged();
    }

    @Override
    public UserViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder( UserViewHolder holder, int position) {
        Account account = accountList.get(position);
        holder.tvName.setText(String.valueOf(account.getName()));
        holder.tvUserName.setText(String.valueOf(account.getUsername()));
        holder.tvEmail.setText(String.valueOf(account.getEmail()));
//        holder.tvIdteacher.setText(String.valueOf(account.getId_teacher()));
        // Set avatar based on user role or any other condition
        if (account.getRole() == 1) {
            holder.imgAvatar.setImageResource(R.drawable.img_admin);
        } else {
            holder.imgAvatar.setImageResource(R.drawable.img_user);
        }
        if (itemListener != null) {
            holder.itemView.setOnClickListener(v -> itemListener.onItemClick(v, position));
        }
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvUserName, tvEmail, tvIdteacher;
        private ImageView imgAvatar;

        public UserViewHolder( View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvUserName = itemView.findViewById(R.id.tvUsername);
            tvEmail = itemView.findViewById(R.id.tvEmail);
//            tvIdteacher = itemView.findViewById(R.id.tvidteacher);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemListener != null) {
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener {
        void onItemClick(View view, int position);
    }
    public Account getAccount(int position) {
        return accountList.get(position);
    }
}
