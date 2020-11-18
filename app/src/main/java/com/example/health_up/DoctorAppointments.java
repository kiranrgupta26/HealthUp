package com.example.health_up;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DoctorAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointments);

        GridView displayTime = (GridView)findViewById(R.id.displayTime);
        ArrayList<String> time = new ArrayList<>();
        final ColorDrawable[] colors = {
                new ColorDrawable(Color.GREEN), // Animation starting color
                new ColorDrawable(Color.WHITE) // Animation ending color
        };
        time.add("8:30 am");
        time.add("9:00 am");
        time.add("9:30 am");
        time.add("10:00 am");
        time.add("4:00 pm");
        time.add("5:00 pm");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,time)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Cast the grid view current item as a text view
                TextView tv_cell = (TextView) super.getView(position,convertView,parent);

                // Set the item background color
                tv_cell.setBackgroundColor(Color.WHITE);

                // Put item item text in cell center
                tv_cell.setGravity(Gravity.CENTER);

                // Return the modified item
                return tv_cell;
            }
        };
        displayTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Apply a click effect for the current clicked item
                TransitionDrawable transitionDrawable = new TransitionDrawable(colors);
                view.setBackground(transitionDrawable);
                transitionDrawable.startTransition(600);
            }
        });
        displayTime.setAdapter(adapter1);


    }
}