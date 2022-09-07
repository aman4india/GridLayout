package com.aman.sqlite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aman.sqlite.db.Database;
import com.aman.sqlite.models.UserEntity;

import java.util.List;

public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.CustomViewHolder> {

    List<UserEntity> userList;
    Database database;
    private Context context;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        database = new Database(parent.getContext());
        context = parent.getContext();
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.userName.setText(userList.get(position).getName());
        holder.userPhone.setText(userList.get(position).getPhone());
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.deleteUser(userList.get(holder.getAdapterPosition()).getPhone());
                userList = database.fetch();
                setUserList(userList);
                Toast.makeText(context, "User deleted!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.editImage.setOnClickListener(view -> {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra("name", userList.get(holder.getAdapterPosition()).getName());
            intent.putExtra("phone", userList.get(holder.getAdapterPosition()).getPhone());
            intent.putExtra("id", userList.get(holder.getAdapterPosition()).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView userPhone;
        ImageView deleteImage;
        ImageView editImage;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userPhone = itemView.findViewById(R.id.user_phone);
            deleteImage = itemView.findViewById(R.id.delete_image);
            editImage = itemView.findViewById(R.id.edit_image);
        }
    }
}
