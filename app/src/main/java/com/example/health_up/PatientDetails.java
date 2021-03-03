package com.example.health_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PatientDetails extends AppCompatActivity {
    private Uri fileUri;
    private String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        Spinner blood_group = findViewById(R.id.et_pbloodgroup);


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

        final EditText et_name = findViewById(R.id.etpatient_name);
        final EditText et_address = findViewById(R.id.et_paddress);
        final EditText et_mobile = findViewById(R.id.et_pmobile);
        final EditText et_email = findViewById(R.id.et_pemail);
        final Spinner sp_bloodGroup = findViewById(R.id.et_pbloodgroup);
        final EditText et_symtoms= findViewById(R.id.et_psymtoms);
        Button btn_update_details = findViewById(R.id.btnupdatedetails);
        Button btn_uploadReport = findViewById(R.id.btn_uploadreport);

        SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final int patient_id = Integer.parseInt(sharedpreferences.getString("patientid","0"));
        final DBMgr dbMgr = new DBMgr(getApplicationContext());
        Patient patient = dbMgr.getPatientDEtails(patient_id);
        et_name.setText(patient.getFirstName());
        et_email.setText(patient.getEmail());
        et_mobile.setText(patient.getMobile());
        et_address.setText(patient.getAddress());
        et_symtoms.setText(patient.getSymtoms());

        btn_update_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patient patient1 = new Patient();
                patient1.setFirstName(et_name.getText().toString());
                patient1.setAddress(et_address.getText().toString());
                patient1.setMobile(et_mobile.getText().toString());
                patient1.setEmail(et_email.getText().toString());
                patient1.setSymtoms(et_symtoms.getText().toString());
                patient1.setBlood_group(sp_bloodGroup.getSelectedItem().toString());
                dbMgr.updatePatientDetails(patient1,patient_id);
                Toast.makeText(getApplicationContext(),"Details Updated",Toast.LENGTH_LONG).show();
            }
        });

        btn_uploadReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                System.out.println("File name "+chooseFile);
                startActivityForResult(chooseFile, 1);
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == -1) {
                    fileUri = data.getData();
                    filePath = fileUri.getPath();
                    Toast.makeText(getApplicationContext(),"File "+filePath+ " Uploaded",Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}