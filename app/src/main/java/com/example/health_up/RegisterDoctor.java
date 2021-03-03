package com.example.health_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterDoctor extends AppCompatActivity {
    private SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_register_doctor);

        final EditText firstname = findViewById(R.id.etFirstName);
        final EditText lastname = findViewById(R.id.etLastName);
        final EditText email = findViewById(R.id.etEmail);
        final EditText mobile = findViewById(R.id.etPhone);
        final EditText license = findViewById(R.id.etLicenseNumber);
        final EditText password =findViewById(R.id.etDoctorPassword);
        final EditText confirm_password = findViewById(R.id.etDoctorConfirmPassword);

        Button registerDoctor = findViewById(R.id.btnRegister);
        registerDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBMgr dbMgr = new DBMgr(getApplicationContext());
                Doctor doctor = new Doctor(firstname.getText().toString(),lastname.getText().toString(),email.getText().toString(),mobile.getText().toString(),license.getText().toString(),password.getText().toString());

                dbMgr.addDoctorDB(doctor);

                int doctorid = dbMgr.getDoctorID(firstname.getText().toString());
                prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                prefs.edit().putString("doctorid",String.valueOf(doctorid)).commit();
                Intent intent = new Intent(getApplicationContext(),PatientAppointments.class);
                startActivity(intent);
            }
        });
    }
}