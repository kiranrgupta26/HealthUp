package com.example.health_up;

import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DoctorDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        EditText doctor_fn = findViewById(R.id.etdoctor_fn);
        EditText doctor_ln = findViewById(R.id.etdoctor_ln);
        EditText doctor_license = findViewById(R.id.etdoctor_license);
        final EditText doctor_location = findViewById(R.id.etdoctor_location);
        final EditText doctor_speciality = findViewById(R.id.etdoctor_speciality);
        final EditText doctor_experience = findViewById(R.id.etdoctor_experience);
        Button updateDoctor = findViewById(R.id.btnupdatedoctordetails);
        updateDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
                int doctor_id = Integer.parseInt(sharedpreferences.getString("doctorid","0"));
                Doctor doctor = new Doctor();
                doctor.setLocation(doctor_location.getText().toString());
                doctor.setSpecialisation(doctor_speciality.getText().toString());
                doctor.setExperience(doctor_experience.getText().toString());

                DBMgr dbMgr = new DBMgr(getApplicationContext());
                dbMgr.updateDoctorDetails(doctor,doctor_id);
                Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();
            }
        });
    }
}