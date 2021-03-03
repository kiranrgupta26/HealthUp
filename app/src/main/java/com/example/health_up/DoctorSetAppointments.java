package com.example.health_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DoctorSetAppointments extends AppCompatActivity {
    String selected_day="";
    String selected_time="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_set_appointments);
        final ColorDrawable[] colors = {
                new ColorDrawable(Color.GREEN), // Animation starting color
                new ColorDrawable(Color.WHITE) // Animation ending color
        };
        final GridView displayTime = (GridView)findViewById(R.id.displayTime);
        final GridView displayDay = (GridView)findViewById(R.id.displayday);

        final ArrayList<String> time = new ArrayList<>();
        ArrayList<String> day = new ArrayList<>();

        day.add("Monday");
        day.add("Tuesday");
        day.add("Wednesday");
        day.add("Thursday");
        day.add("Friday");
        day.add("Saturday");
        day.add("Sunday");

        ArrayAdapter<String> adapter_day = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,day)
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
        final ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,time)
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
        displayDay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int backposition=-1;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Apply a click effect for the current clicked item
                TransitionDrawable transitionDrawable = new TransitionDrawable(colors);
                view.setBackground(transitionDrawable);
                transitionDrawable.startTransition(600);
                selected_time="";
                displayTime.clearChoices();
                adapter_time.notifyDataSetChanged();
                if(backposition!=-1)
                {
                    view = displayDay.getChildAt(backposition);
                    view.setSelected(false);
                    view.setBackgroundColor(Color.WHITE);
                }
                view = displayDay.getChildAt(position);
                view.setBackgroundColor(Color.GREEN);
                selected_day = displayDay.getItemAtPosition(position).toString();
                backposition = position;
            }
        });
        displayDay.setAdapter(adapter_day);

        time.add("8:30");
        time.add("9:00");
        time.add("9:30");
        time.add("10:00");
        time.add("16:00");
        time.add("17:00");

        displayTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Apply a click effect for the current clicked item
                TransitionDrawable transitionDrawable = new TransitionDrawable(colors);
                view.setBackground(transitionDrawable);
                transitionDrawable.startTransition(600);

                view = displayTime.getChildAt(position);
                view.setBackgroundColor(Color.GREEN);
                String temp_time = displayTime.getItemAtPosition(position).toString();
                selected_time += temp_time+",";

            }
        });
        displayTime.setAdapter(adapter_time);

        Button schedule=findViewById(R.id.btnschedule);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBMgr dbMgr = new DBMgr(getApplicationContext());
                SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
                int doctor_id = Integer.parseInt(sharedpreferences.getString("doctorid","0"));
                System.out.println("Selected is "+selected_day+" "+selected_time);
                dbMgr.scheduleAppointments(doctor_id,selected_day,selected_time);
                Toast.makeText(getApplicationContext(),"Appointment Scheduled",Toast.LENGTH_LONG).show();
            }
        });
    }
}