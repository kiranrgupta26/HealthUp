package com.example.health_up;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    private List<Doctor> doctorlist;
    private Context mContext;
    onDoctorListner onDoctorListner;
    public  DoctorAdapter(Context context,List<Doctor> doctorlist,onDoctorListner onDoctorListner)
    {
        this.doctorlist = doctorlist;
        this.mContext = context;
        this.onDoctorListner = onDoctorListner;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public  TextView doctorname;
        public  TextView specialisation;
        public  TextView location;
        onDoctorListner onDoctorListner;
        public MyViewHolder(@NonNull View itemView,onDoctorListner onDoctorListner) {
            super(itemView);
            doctorname = (TextView) itemView.findViewById(R.id.doctorname);
            specialisation = (TextView)itemView.findViewById(R.id.Specialist);
            location = (TextView)itemView.findViewById(R.id.sp_bloodgroup);
            this.onDoctorListner = onDoctorListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onDoctorListner.onDoctorClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_info, parent, false);

        return new MyViewHolder(itemView,onDoctorListner);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Doctor doctor = doctorlist.get(position);
        holder.doctorname.setText(doctor.getFirstname());
        holder.specialisation.setText(doctor.getSpecialisation());
        holder.location.setText(doctor.getLocation());



        /*holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return doctorlist.size();
    }

    public interface onDoctorListner{
        void onDoctorClick(int position);
    }
}
