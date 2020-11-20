package com.example.health_up;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchDoctor extends AppCompatActivity implements DoctorAdapter.onDoctorListner {
    public static final String MyPREFERENCES = "MyPrefs";

    private AppBarConfiguration mAppBarConfiguration;
    private List<Doctor> doctorList = new ArrayList<>();
    private RecyclerView displayDoctors;
    private DoctorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner specialisation = findViewById(R.id.specialisation);
        final Spinner location = findViewById(R.id.location);
        Button searchDoctor = findViewById(R.id.btnsearch);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.doctor_specialisation, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialisation.setAdapter(adapter);
        
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.doctor_location, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(adapter1);

        displayDoctors = findViewById(R.id.display_doctors);
        /*mAdapter = new DoctorAdapter(getApplicationContext(),doctorList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        displayDoctors.setLayoutManager(mLayoutManager);
        displayDoctors.setItemAnimator(new DefaultItemAnimator());
        displayDoctors.setAdapter(mAdapter);*/

        searchDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBMgr dbMgr = new DBMgr(getApplicationContext());
                System.out.println(specialisation.getSelectedItem().toString()+location.getSelectedItem().toString());
                doctorList =  dbMgr.getDoctors(specialisation.getSelectedItem().toString(),location.getSelectedItem().toString());
                mAdapter = new DoctorAdapter(getApplicationContext(),doctorList,SearchDoctor.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                displayDoctors.setLayoutManager(mLayoutManager);
                displayDoctors.setItemAnimator(new DefaultItemAnimator());
                displayDoctors.setAdapter(mAdapter);
            }
        });

        displayDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),DoctorAppointments.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_myappointment, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id==R.id.nav_mydetails)
                {
                    Intent intent1 = new Intent(getApplicationContext(),PatientDetails.class);
                    startActivity(intent1);
                }
                if(id==R.id.nav_donor)
                {
                    Intent intent1 = new Intent(getApplicationContext(),RegisterDonor.class);
                    startActivity(intent1);
                }
                return false;
            }
        });
    }

    private void displayDoctors()
    {
        Doctor doctor1 = new Doctor("Doctor 1", "Specialisation", "location");
        doctorList.add(doctor1);

        Doctor doctor2 = new Doctor("Doctor 2", "Specialisation", "location");
        doctorList.add(doctor2);

        Doctor doctor3 = new Doctor("Doctor 3", "Specialisation", "location");
        doctorList.add(doctor3);

        Doctor doctor4 = new Doctor("Doctor 4", "Specialisation", "location");
        doctorList.add(doctor4);

        Doctor doctor5 = new Doctor("Doctor 5", "Specialisation", "location");
        doctorList.add(doctor5);

        Doctor doctor6 = new Doctor("Doctor 6", "Specialisation", "location");
        doctorList.add(doctor6);

        Doctor doctor7 = new Doctor("Doctor 7", "Specialisation", "location");
        doctorList.add(doctor7);

        Doctor doctor8 = new Doctor("Doctor 8", "Specialisation", "location");
        doctorList.add(doctor8);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onDoctorClick(int position) {
        Doctor doctor = doctorList.get(position);
        DBMgr dbMgr = new DBMgr(getApplicationContext());
        int doctorid = dbMgr.getDoctorID(doctor.getFirstname());
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        prefs.edit().putString("patientid",String.valueOf(doctorid)).commit();
        Intent intent = new Intent(getApplicationContext(),DoctorAppointments.class);
        startActivity(intent);
    }
}