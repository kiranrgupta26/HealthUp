package com.example.health_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DisplayBookedPatient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_booked_patient);

        final EditText et_name = findViewById(R.id.etpatient_name);
        final EditText et_address = findViewById(R.id.et_paddress);
        final EditText et_mobile = findViewById(R.id.et_pmobile);
        final EditText et_email = findViewById(R.id.et_pemail);
        final EditText et_bloodGroup = findViewById(R.id.et_pbloodgroup);
        final EditText et_symtoms= findViewById(R.id.et_psymtoms);

        Button cancelAppointment = findViewById(R.id.btncancel);
        Button closeAppointment = findViewById(R.id.btnclose);
        final int patient_id = getIntent().getIntExtra("patientid",0);
        final DBMgr dbMgr = new DBMgr(getApplicationContext());
        Patient patient = dbMgr.getPatientDEtails(patient_id);

        et_name.setText(patient.getFirstName());
        et_email.setText(patient.getEmail());
        et_mobile.setText(patient.getMobile());
        et_address.setText(patient.getAddress());
        et_symtoms.setText(patient.getSymtoms());
        et_bloodGroup.setText(patient.getBlood_group());

        SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final int doctor_id = Integer.parseInt(sharedpreferences.getString("doctorid","0"));

        cancelAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbMgr.cancelAppointment(patient_id,doctor_id);
                Toast.makeText(getApplicationContext(),"Appointment Cancelled",Toast.LENGTH_LONG).show();
            }
        });

        closeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbMgr.closeAppointment(patient_id,doctor_id);
                Toast.makeText(getApplicationContext(),"Appointment Closed",Toast.LENGTH_LONG).show();
            }
        });
    }
}