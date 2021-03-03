package com.example.health_up;

import androidx.annotation.NonNull;
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
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DoctorAppointments extends AppCompatActivity {
    String date="";
    String selected_time="";

    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointments);


        final GridView displayTime = findViewById(R.id.displayday);
        final ArrayList<String> time=new ArrayList<>();
        DBMgr dbMgr = new DBMgr(getApplicationContext());
        SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        int doctor_id = Integer.parseInt(sharedpreferences.getString("doctorid","0"));
        final Appointments appointments = dbMgr.getDoctorAppointments(doctor_id);

        System.out.println("Appointments Days "+appointments.getDays());
        System.out.println("Appointments times "+appointments.getTimes());

        final ColorDrawable[] colors = {
                new ColorDrawable(Color.GREEN), // Animation starting color
                new ColorDrawable(Color.WHITE) // Animation ending color
        };
        CalendarView appointmentdate = findViewById(R.id.calendarView);

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,time)
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

        appointmentdate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth+"/"+month+"/"+year;
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, (month+1), dayOfMonth);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                String available_times;
                String[] atimes;
                time.clear();

                switch (dayOfWeek)
                {
                    case 5:
                        available_times = appointments.getTimes().get(0);
                        System.out.println("dayOfWeek "+dayOfWeek+" "+available_times);
                        atimes = available_times.split(",");
                        for(String t : atimes)
                        {
                            time.add(t);
                        }
                        adapter1.notifyDataSetChanged();
                        break;
                    case 6:
                        available_times = appointments.getTimes().get(1);
                        System.out.println("dayOfWeek "+dayOfWeek+" "+available_times);
                        atimes = available_times.split(",");
                        for(String t : atimes)
                        {
                            time.add(t);
                        }
                        adapter1.notifyDataSetChanged();
                        break;
                    case 7:

                        available_times = appointments.getTimes().get(2);
                        System.out.println("dayOfWeek "+dayOfWeek+" "+available_times);
                        atimes = available_times.split(",");
                        for(String t : atimes)
                        {
                            time.add(t);
                        }
                        adapter1.notifyDataSetChanged();
                        break;
                    case 1:

                        available_times = appointments.getTimes().get(3);
                        System.out.println("dayOfWeek "+dayOfWeek+" "+available_times);
                        atimes = available_times.split(",");
                        for(String t : atimes)
                        {
                            time.add(t);
                        }
                        adapter1.notifyDataSetChanged();
                        break;
                    case 2:

                        available_times = appointments.getTimes().get(4);
                        System.out.println("dayOfWeek "+dayOfWeek+" "+available_times);
                        atimes = available_times.split(",");
                        for(String t : atimes)
                        {
                            time.add(t);
                        }
                        adapter1.notifyDataSetChanged();
                        break;
                    case 3:

                        available_times = appointments.getTimes().get(5);
                        System.out.println("dayOfWeek "+dayOfWeek+" "+available_times);
                        atimes = available_times.split(",");
                        for(String t : atimes)
                        {
                            time.add(t);
                        }
                        adapter1.notifyDataSetChanged();
                        break;
                    case 4:

                        available_times = appointments.getTimes().get(6);
                        System.out.println("dayOfWeek "+dayOfWeek+" "+available_times);
                        atimes = available_times.split(",");
                        for(String t : atimes)
                        {
                            time.add(t);
                        }
                        adapter1.notifyDataSetChanged();
                        break;
                }
                Toast.makeText(getApplicationContext(),year+" "+(month+1)+" "+dayOfMonth+" "+dayOfWeek,Toast.LENGTH_LONG).show();
            }
        });

        Button bookAppointment = findViewById(R.id.btnbookappointment);
        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String datetime =date+" "+selected_time;
                Date date = null;
                try {
                    date= new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(datetime);
                    //System.out.println(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DBMgr dbMgr = new DBMgr(getApplicationContext());
                SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
                int doctor_id = Integer.parseInt(sharedpreferences.getString("doctorid","0"));
                int patient_id = Integer.parseInt(sharedpreferences.getString("patientid","0"));
                System.out.println("Patient is"+patient_id);
                dbMgr.addPatientsAppointments(doctor_id,patient_id,date);
                Toast.makeText(getApplicationContext(),"Appointment Booked",Toast.LENGTH_LONG).show();
            }
        });

        displayTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int backposition=-1;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Apply a click effect for the current clicked item
                TransitionDrawable transitionDrawable = new TransitionDrawable(colors);
                view.setBackground(transitionDrawable);
                transitionDrawable.startTransition(600);
                if(backposition!=-1)
                {
                    view = displayTime.getChildAt(backposition);
                    view.setSelected(false);
                    view.setBackgroundColor(Color.WHITE);
                }
                view = displayTime.getChildAt(position);
                view.setBackgroundColor(Color.GREEN);
                selected_time = displayTime.getItemAtPosition(position).toString();

                backposition = position;
            }
        });
        displayTime.setAdapter(adapter1);
    }
}