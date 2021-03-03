package com.example.health_up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterDonor extends AppCompatActivity {
    String quanrantine_completed="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_donor);

        final EditText diagnosis = findViewById(R.id.etDiagnosis);
        final EditText name = findViewById(R.id.etDonorName);
        final EditText address = findViewById(R.id.etAddress);
        final EditText mobile = findViewById(R.id.etMobile);
        final EditText blood_group = findViewById(R.id.etBloodGroup);
        final EditText symtoms = findViewById(R.id.etSymptoms);

        RadioGroup rg = findViewById(R.id.radioGroup);
        rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.radio_yes:
                        quanrantine_completed="Yes";
                    case R.id.radio_no:
                        quanrantine_completed="No";
                        break;
                }
            }
        });
        Button registerDonor = findViewById(R.id.btnRegisterDonor);
        final DBMgr dbMgr  = new DBMgr(getApplicationContext());

        registerDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbMgr.registerDonor(quanrantine_completed,diagnosis.getText().toString(),name.getText().toString(),address.getText().toString(),mobile.getText().toString(),blood_group.getText().toString(),symtoms.getText().toString());
                Toast.makeText(getApplicationContext(),"Registered as Donor",Toast.LENGTH_LONG).show();
            }
        });


    }
}