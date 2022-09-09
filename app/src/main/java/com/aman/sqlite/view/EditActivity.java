package com.aman.sqlite.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.aman.sqlite.R;
import com.aman.sqlite.data.DatabaseInitializer;
import com.aman.sqlite.data.db.UserDatabase;
import com.aman.sqlite.databinding.ActivityEditBinding;
import com.aman.sqlite.models.UserEntity;
import com.aman.sqlite.viewmodel.EditActivityViewModel;
import com.aman.sqlite.viewmodel.MainViewModel;

public class EditActivity extends BaseActivity<ActivityEditBinding, EditActivityViewModel> {

    private EditText nameEditText;
    private EditText phoneEditText;
    private Button saveBtn;
    private int id;

    @NonNull
    @Override
    protected EditActivityViewModel createViewModel() {
        viewModel = new ViewModelProvider(this).get(EditActivityViewModel.class);
        return viewModel;
    }

    @NonNull
    @Override
    protected ActivityEditBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityEditBinding.inflate(layoutInflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nameEditText = binding.nameEdittext;
        phoneEditText = binding.phoneEdittext;
        saveBtn = binding.saveBtn;
        nameEditText.setText(getIntent().getStringExtra("name"));
        phoneEditText.setText(getIntent().getStringExtra("phone"));
        id = getIntent().getIntExtra("id", 0);
        saveBtn.setOnClickListener(view -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(id);
            userEntity.setName(nameEditText.getText().toString());
            userEntity.setPhone(phoneEditText.getText().toString());
            DatabaseInitializer.updateUser(EditActivity.this,userEntity);
            Toast.makeText(EditActivity.this, "DB Updated! ", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}