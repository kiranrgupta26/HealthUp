package com.example.health_up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class PatientDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        Spinner blood_group = findViewById(R.id.spinner);


        List<String> bg = new ArrayList<String>();
        bg.add("A +ve");
        bg.add("A -ve");
        bg.add("B +ve");
        bg.add("B -ve");
        bg.add("AB +ve");
        bg.add("AB -ve");
        bg.add("O +ve");
        bg.add("O +ve");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, bg);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blood_group.setAdapter(dataAdapter);

    }
}