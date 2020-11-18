package com.example.health_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userName = (EditText)findViewById(R.id.etUserName);
        final EditText password = (EditText)findViewById(R.id.etpassword);
        Button login = (Button)findViewById(R.id.btnLogin);
        Button registerDoctor = (Button)findViewById(R.id.btnRegisterDoctor);
        Button registerPatient = (Button)findViewById(R.id.btnRegisterPatient);
        registerDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDoctor();
            }
        });
        registerPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPatient();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userName.getText().toString();
                String pass = password.getText().toString();
                DBMgr dbMgr = new DBMgr(getApplicationContext());
                //int te = dbMgr.getRecords();
                //String tem = String.valueOf(te);
                //Toast.makeText(getApplicationContext(),tem,Toast.LENGTH_SHORT).show();
                if(dbMgr.isUserExists(username))
                {
                    String password = dbMgr.getUserPasssword(username);
                    if(password.equals(pass))
                    {
                        Intent intent = new Intent(getApplicationContext(),SearchDoctor.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Invalid Username Password",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(dbMgr.isDoctorExists(username))
                {
                    String password = dbMgr.getDoctorPassword(username);
                    if(password.equals(pass))
                    {
                        Intent intent = new Intent(getApplicationContext(),PatientAppointments.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Invalid Doctor Username Password",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void registerDoctor(){
        Intent intent = new Intent(this, RegisterDoctor.class);
        startActivity(intent);
    }
    public void registerPatient(){
        Intent intent = new Intent(this, RegisterPatient.class);
        startActivity(intent);
    }
}