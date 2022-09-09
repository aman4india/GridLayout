package com.aman.sqlite.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aman.sqlite.R;
import com.aman.sqlite.data.DatabaseInitializer;
import com.aman.sqlite.databinding.ActivityMainBinding;
import com.aman.sqlite.models.UserEntity;
import com.aman.sqlite.view.adapter.RecyclerGridAdapter;
import com.aman.sqlite.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView;
    List<UserEntity> list;
    EditText nameEdit;
    EditText phoneEdit;
    RecyclerGridAdapter recyclerGridAdapter;

    @NonNull
    @Override
    protected MainViewModel createViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        return viewModel;
    }

    @NonNull
    @Override
    protected ActivityMainBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityMainBinding.inflate(layoutInflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = binding.recyclerview;
        nameEdit = binding.nameEdittext;
        phoneEdit = binding.phoneEdittext;

        recyclerGridAdapter = new RecyclerGridAdapter(new ArrayList<>());
        recyclerView.setAdapter(recyclerGridAdapter);



        Button saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(view -> {
            Log.e(TAG, "onCreate: " +nameEdit.getText().toString() );
            if (nameEdit.getText().toString().length() > 0 && phoneEdit.getText().toString().length() > 0) {
                UserEntity userEntity = new UserEntity();
                userEntity.setName(nameEdit.getText().toString());
                userEntity.setPhone(phoneEdit.getText().toString());
                nameEdit.setText("");
                phoneEdit.setText("");
                DatabaseInitializer.insertUser(this, userEntity);
            } else
                Toast.makeText(this, "Name & Phone should not be empty!", Toast.LENGTH_SHORT).show();


            list = DatabaseInitializer.getAllUser(this);
            if (list.size() > 0)
                recyclerGridAdapter.setUserList(list);
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });

        list = DatabaseInitializer.getAllUser(this);
        if (list.size() > 0)
            recyclerGridAdapter.setUserList(list);

    }

    @Override
    protected void onResume() {
        super.onResume();
        list = DatabaseInitializer.getAllUser(this);
        if (list.size() > 0)
            recyclerGridAdapter.setUserList(list);
    }

    public interface OnIconClickListener {
        void onItemEditListener(Context context, int position);

        void onItemDeleteListener(Context context, int position);
    }

}