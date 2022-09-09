package com.aman.sqlite.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.aman.sqlite.data.DatabaseInitializer;
import com.aman.sqlite.databinding.ListItemBinding;
import com.aman.sqlite.models.UserEntity;
import com.aman.sqlite.view.EditActivity;
import com.aman.sqlite.view.MainActivity;

import java.util.List;

public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.CustomViewHolder> implements MainActivity.OnIconClickListener {

    private List<UserEntity> userList;

    public RecyclerGridAdapter(List<UserEntity> userList) {
        this.userList = userList;
    }

    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CustomViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bind(userList.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public void onItemEditListener(Context context, int position) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("name", userList.get(position).getName());
        intent.putExtra("phone", userList.get(position).getPhone());
        intent.putExtra("id", userList.get(position).getId());
        context.startActivity(intent);
    }

    @Override
    public void onItemDeleteListener(Context context, int position) {
        DatabaseInitializer.deleteUser(context, userList.get(position).getPhone());
        userList = DatabaseInitializer.getAllUser(context);
        setUserList(userList);
        Toast.makeText(context, "User deleted!", Toast.LENGTH_SHORT).show();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ListItemBinding itemBinding;

        public CustomViewHolder(@NonNull ListItemBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }

        private void bind(Object object) {
            itemBinding.setVariable(BR._all, object);
            itemBinding.setVariable(BR.pos, getAdapterPosition());
            itemBinding.setVariable(BR.userData, userList.get(getAdapterPosition()));
            itemBinding.setOnItemClickListener(RecyclerGridAdapter.this);
            itemBinding.executePendingBindings();
        }

    }
}
