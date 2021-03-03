package com.example.health_up;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MyAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);
        SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        int patient_id = Integer.parseInt(sharedpreferences.getString("patientid","0"));
        ListView myappointments = findViewById(R.id.lv_myappointments);

        final DBMgr dbMgr = new DBMgr(getApplicationContext());
        List<String> myappointment = dbMgr.myAppointments(patient_id);

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myappointment);

        myappointments.setAdapter(itemsAdapter);
    }
}