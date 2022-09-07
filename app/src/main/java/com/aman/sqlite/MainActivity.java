package com.aman.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aman.sqlite.db.Database;
import com.aman.sqlite.models.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<UserEntity> list;
    EditText nameEdit;
    EditText phoneEdit;
    Database database;
    RecyclerGridAdapter recyclerGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        nameEdit = findViewById(R.id.name_edittext);
        phoneEdit = findViewById(R.id.phone_edittext);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerGridAdapter = new RecyclerGridAdapter(new ArrayList<>());
        recyclerView.setAdapter(recyclerGridAdapter);

        database = new Database(this);

        Button saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(view -> {
            if (nameEdit.getText().toString().length() > 0 && phoneEdit.getText().toString().length() > 0)
                database.save(nameEdit.getText().toString(), phoneEdit.getText().toString());
            else
                Toast.makeText(this, "Name & Phone should not be empty!", Toast.LENGTH_SHORT).show();
            nameEdit.setText("");
            phoneEdit.setText("");

            list = database.fetch();
            if (list.size() > 0)
                recyclerGridAdapter.setUserList(list);
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });

        list = database.fetch();
        if (list.size() > 0)
            recyclerGridAdapter.setUserList(list);

    }

    @Override
    protected void onResume() {
        super.onResume();
        list = database.fetch();
        if (list.size() > 0)
            recyclerGridAdapter.setUserList(list);
    }
}