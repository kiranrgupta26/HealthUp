package com.example.health_up;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchDonor extends AppCompatActivity {
    List<String> donorsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchdonors);
        donorsList = new ArrayList<>();
        final ArrayAdapter<String> mAdapter1;
        final Spinner location = findViewById(R.id.splocation);
        final Spinner bloodgroup = findViewById(R.id.spbloodgroup);
        Button searchDonor = findViewById(R.id.btnsearchDonor);
        final ListView displayDonors = findViewById(R.id.lvdisplaydonors);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodgroup.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.doctor_location, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(adapter1);

        mAdapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, donorsList);

        searchDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBMgr dbMgr = new DBMgr(getApplicationContext());
                //donorsList = new ArrayList();
                List<String> donorsList1 =  dbMgr.getDonors(bloodgroup.getSelectedItem().toString(),location.getSelectedItem().toString());
                for(int i=0;i<donorsList1.size();i++)
                {
                    donorsList.add(donorsList1.get(i));
                }

                displayDonors.setAdapter(mAdapter1);
                mAdapter1.notifyDataSetChanged();
            }
        });
    }
}