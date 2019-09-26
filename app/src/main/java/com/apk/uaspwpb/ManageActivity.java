package com.apk.uaspwpb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageActivity extends AppCompatActivity {

    EditText edtJudul, edtDesc;
    Button btnSubmit;
    DatabaseReference db_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        edtJudul = findViewById(R.id.edtJudul);
        edtDesc = findViewById(R.id.edtDes);
        btnSubmit = findViewById(R.id.btnSubmit);
        db_note = FirebaseDatabase.getInstance().getReference("note");


    }

}

