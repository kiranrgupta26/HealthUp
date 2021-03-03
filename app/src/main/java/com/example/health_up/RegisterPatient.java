package com.example.health_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterPatient extends AppCompatActivity {
    private SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_register_patient);

        final EditText firstName = (EditText)findViewById(R.id.etFirstName);
        final EditText lastName = (EditText)findViewById(R.id.etLastName);
        final EditText email = (EditText)findViewById(R.id.etEmail);
        final EditText mobile = (EditText)findViewById(R.id.etPhone);
        final EditText password = (EditText)findViewById(R.id.etPatientPassword);
        EditText confirmpassword = (EditText)findViewById(R.id.etPatientConfirmPassword);
        Button registerPatient = (Button)findViewById(R.id.btnregisterPatient);

        registerPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patient patient = new Patient(firstName.getText().toString(),lastName.getText().toString(),email.getText().toString(),mobile.getText().toString(),password.getText().toString());
                DBMgr dbMgr = new DBMgr(getApplicationContext());
                dbMgr.addUserDB(patient);

                int patientid = dbMgr.getPatientID(firstName.getText().toString());
                prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                prefs.edit().putString("patientid",String.valueOf(patientid)).commit();
                Intent intent = new Intent(getApplicationContext(),SearchDoctor.class);
                startActivity(intent);
            }
        });
    }

}