package com.aman.sqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aman.sqlite.db.Database;

public class EditActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText phoneEditText;
    private Button saveBtn;
    private Database db;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        nameEditText = findViewById(R.id.name_edittext);
        phoneEditText = findViewById(R.id.phone_edittext);
        saveBtn = findViewById(R.id.save_btn);
        db = new Database(this);
        nameEditText.setText(getIntent().getStringExtra("name"));
        phoneEditText.setText(getIntent().getStringExtra("phone"));
        id = getIntent().getIntExtra("id", 0);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.update(id, nameEditText.getText().toString(), phoneEditText.getText().toString());
                Toast.makeText(EditActivity.this, "DB Updated! ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}