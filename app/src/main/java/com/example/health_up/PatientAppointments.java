package com.example.health_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class PatientAppointments extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_nav);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        int doctor_id = Integer.parseInt(sharedpreferences.getString("doctorid","0"));

        final DBMgr dbMgr = new DBMgr(getApplicationContext());
        System.out.println("Doctor id is "+doctor_id);
        final List<String> allpatients = dbMgr.getPatientsAppointments(doctor_id);

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allpatients);

        ListView listView = (ListView) findViewById(R.id.display_donors);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String p = allpatients.get(position);
                System.out.println("ID is "+p);
                int p_id = dbMgr.getPatientID(p);
                Intent intent = new Intent(getApplicationContext(),DisplayBookedPatient.class);
                intent.putExtra("patientid", p_id);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout_patient);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_patient_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id==R.id.nav_mydetails)
                {
                    Intent intent1 = new Intent(getApplicationContext(),DoctorDetails.class);
                    startActivity(intent1);
                }
                if(id==R.id.nav_scheduleApp)
                {
                    Intent intent1 = new Intent(getApplicationContext(),DoctorSetAppointments.class);
                    startActivity(intent1);
                }
                if(id==R.id.nav_searchdonor)
                {
                    Intent intent1 = new Intent(getApplicationContext(),SearchDonor.class);
                    startActivity(intent1);
                }
                if(id==R.id.nav_logout)
                {
                    Intent intent = new Intent(PatientAppointments.this,
                            Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_patient_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}