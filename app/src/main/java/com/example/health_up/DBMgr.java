package com.example.health_up;

import android.content.ContentValues;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.DatabaseUtils;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Date;
import java.util.ArrayList;

import java.util.List;

public class DBMgr extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "healthupDB12";

    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "userid";
    private static final String KEY_FNAME = "firstname";
    private static final String KEY_LNAME = "lastname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ADDR = "address";
    private static final String KEY_BG = "bloodgroup";
    private static final String KEY_SYM = "symtoms";


    private static final String TABLE_DOCTORS = "doctors";
    private static final String KEY_DID = "doctorid";
    private static final String KEY_DFNAME = "firstname";
    private static final String KEY_DLNAME = "lastname";
    private static final String KEY_DEMAIL = "email";
    private static final String KEY_DMOBILE = "mobile";
    private static final String KEY_DPASSWORD = "password";
    private static final String KEY_LICENSE = "license";
    private static final String KEY_SPECIALISATION = "specialisation";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_EXPERIENCE = "experience";

    private static final String TABLE_BOOK_APPOINTMENTS = "bookappointments";
    private static final String KEY_APPOINTMENT_ID = "id";
    private static final String KEY_DOC_ID = "doctorid";
    private static final String KEY_PAT_ID = "userid";
    private static final String KEY_DATE = "date";
    private static final String KEY_ISCANCELLED = "iscancelled";
    private static final String KEY_ISCLOSED = "isclosed";

    private static final String TABLE_SET_APPOINTMENTS = "setappointments";
    private static final String KEY_DOCTOR_ID = "doctorid";
    private static final String KEY_DAY = "day";
    private static final String KEY_TIME = "time";

    private static final String TABLE_DONORS = "donors";
    private static final String KEY_QUARANTINE = "quarantine";
    private static final String KEY_DIAGNOSIS = "diagnosis";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE  = "mobile";
    private static final String KEY_BLOODGROUP = "bloodgroup";
    private static final String KEY_SYMTOMS = "symtoms";

    public DBMgr(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FNAME + " TEXT,"
                + KEY_LNAME + " TEXT," + KEY_EMAIL + " TEXT," + KEY_MOBILE + " TEXT," + KEY_PASSWORD+" TEXT," + KEY_ADDR+" TEXT,"+ KEY_BG+" TEXT,"+ KEY_SYM+" TEXT" +")";

        String CREATE_DOCTOR_TABLE = "CREATE TABLE " + TABLE_DOCTORS + "("
                + KEY_DID + " INTEGER PRIMARY KEY," + KEY_DFNAME + " TEXT,"
                + KEY_DLNAME + " TEXT," + KEY_DEMAIL + " TEXT," + KEY_DMOBILE + " TEXT," +  KEY_LICENSE + " TEXT," + KEY_SPECIALISATION + " TEXT," + KEY_LOCATION + " TEXT," + KEY_EXPERIENCE+ " TEXT," + KEY_DPASSWORD+" TEXT" +")";

        String CREATE_BOOK_APPOINTMENTS_TABLE = "CREATE TABLE " + TABLE_BOOK_APPOINTMENTS + "("
                + KEY_APPOINTMENT_ID + " INTEGER PRIMARY KEY," + KEY_DOC_ID + " INTEGER,"
                + KEY_PAT_ID + " INTEGER," + KEY_DATE + " DATETIME," + KEY_ISCLOSED + " INTEGER," + KEY_ISCANCELLED+" INTEGER" +")";

        String CREATE_SET_APPOINTMENTS_TABLE = "CREATE TABLE " + TABLE_SET_APPOINTMENTS + "("
                + KEY_DOCTOR_ID + " INTEGER ," + KEY_DAY + " TEXT,"
                + KEY_TIME + " TEXT" +")";

        String CREATE_DONORS_TABLE = "CREATE TABLE " + TABLE_DONORS + "("
                + KEY_QUARANTINE + " TEXT," + KEY_DIAGNOSIS + " TEXT,"
                + KEY_NAME + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_PHONE + " TEXT," + KEY_BLOODGROUP+" TEXT," + KEY_SYMTOMS+" TEXT" +")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_DOCTOR_TABLE);
        db.execSQL(CREATE_BOOK_APPOINTMENTS_TABLE);
        db.execSQL(CREATE_SET_APPOINTMENTS_TABLE);
        db.execSQL(CREATE_DONORS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK_APPOINTMENTS);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SET_APPOINTMENTS);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONORS);
        onCreate(db);
    }

    public void closeAppointment(int patientid, int doctorid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ISCLOSED,1);

        db.update(TABLE_BOOK_APPOINTMENTS,cv,"doctorid="+doctorid +" AND userid="+patientid,null);
        db.close();
    }

    public void cancelAppointment(int patientid, int doctorid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ISCANCELLED,1);

        db.update(TABLE_BOOK_APPOINTMENTS,cv,"doctorid="+doctorid +" AND userid="+patientid,null);
        db.close();
    }

    public List<String> myAppointments(int patientid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query="Select * from "+TABLE_BOOK_APPOINTMENTS +" where "+KEY_PAT_ID + " =" +patientid;
        Cursor cursor = db.rawQuery(query,null);
        List<String> myappointments = new ArrayList<>();

        cursor.moveToFirst();
        int count = cursor.getCount();
        System.out.println("Patient is is s "+patientid);
        for(int i=0;i<count;i++)
        {
            int doctor_id = cursor.getInt(1);
            System.out.println("Doctor is is s "+doctor_id);
            String query1="Select * from "+TABLE_DOCTORS +" where "+KEY_DID + " =" +doctor_id;
            Cursor cursor1 = db.rawQuery(query1,null);
            cursor1.moveToFirst();
            String d_name = cursor1.getString(1);
            String info = "Dr."+d_name+" "+cursor.getString(3);
            myappointments.add(info);
            cursor.moveToNext();
        }
        return myappointments;
    }
    public void addUserDB(Patient patient)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, patient.getFirstName()); // Contact Name
        values.put(KEY_LNAME, patient.getLastName());
        values.put(KEY_EMAIL, patient.getEmail());
        values.put(KEY_MOBILE, patient.getMobile());
        values.put(KEY_PASSWORD, patient.getPassword());

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void addDoctorDB(Doctor doctor)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DFNAME, doctor.getFirstname());
        values.put(KEY_DLNAME, doctor.getLastname());
        values.put(KEY_DEMAIL, doctor.getEmail());
        values.put(KEY_DMOBILE, doctor.getMobile());
        values.put(KEY_DPASSWORD, doctor.getPassword());
        values.put(KEY_LICENSE, doctor.getLicense());

        // Inserting Row
        db.insert(TABLE_DOCTORS, null, values);
        db.close();

    }

    public void registerDonor(String quarantine,String diagnosis,String name,String address,String mobile,String bloodgroup,String symtoms)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUARANTINE,quarantine);
        values.put(KEY_DIAGNOSIS,diagnosis);
        values.put(KEY_NAME,name);
        values.put(KEY_ADDRESS,address);
        values.put(KEY_PHONE,mobile);
        values.put(KEY_BLOODGROUP,bloodgroup);
        values.put(KEY_SYMTOMS,symtoms);
        db.insert(TABLE_DONORS, null, values);
        db.close();

    }
    public List<String> getDonors(String bloodgroup,String location)
    {
        List<String> donors = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query="Select * from "+TABLE_DONORS +" where "+KEY_ADDRESS+" ='"+location+"' AND "+KEY_BLOODGROUP+"='"+bloodgroup+"'";
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        for(int i=0;i<count;i++)
        {
            donors.add(cursor.getString(2)+" "+cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(4)+" "+cursor.getString(6));
            if (cursor != null)
            {
                cursor.moveToNext();
            }
        }
        return donors;
    }

    public Appointments getDoctorAppointments(int doctorid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query="Select * from "+TABLE_SET_APPOINTMENTS +" where "+KEY_DOCTOR_ID + " =" +doctorid;
        Cursor cursor = db.rawQuery(query,null);
        Appointments appointments = new Appointments();
        if (cursor != null) {
            cursor.moveToFirst();
        }
        appointments.setDoctorid(doctorid);
        int count = cursor.getCount();
        for(int i=0;i<count;i++)
        {
            appointments.addDays(cursor.getString(1));
            appointments.addTimes(cursor.getString(2));
            cursor.moveToNext();
        }
        return  appointments;
    }


    public String getDoctorPassword(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DOCTORS, new String[] { KEY_DPASSWORD
                }, KEY_DFNAME + "=?",
                new String[] { String.valueOf(username) }, null, null, null, null);
        String password="a";
        if (cursor != null)
        {
            cursor.moveToFirst();
            password = cursor.getString(0);
        }
        return password;
    }

    public void scheduleAppointments(int doctorid,String day, String times)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DOCTOR_ID, doctorid);
        values.put(KEY_DAY, day);
        values.put(KEY_TIME, times);
        db.insert(TABLE_SET_APPOINTMENTS, null, values);
        db.close();
    }
    public int getDoctorID(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DOCTORS, new String[] { KEY_DID
                }, KEY_DFNAME + "=?",
                new String[] { String.valueOf(username) }, null, null, null, null);

        if (cursor != null)
        {
            cursor.moveToFirst();

        }
        int doctid= cursor.getInt(0);
        return doctid;
    }

    public int getPatientID(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_ID
                }, KEY_FNAME + "=?",
                new String[] { String.valueOf(username) }, null, null, null, null);
        System.out.println("Count is "+cursor.getCount());
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        int patientid= cursor.getInt(0);

        return patientid;
    }

    public void updateDoctorDetails(Doctor doctor,int doctorid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_LOCATION,doctor.getLocation());
        cv.put(KEY_SPECIALISATION,doctor.getSpecialisation());
        cv.put(KEY_EXPERIENCE,doctor.getExperience());
        db.update(TABLE_DOCTORS,cv,"doctorid="+doctorid,null);
        db.close();

    }
    public String getUserPasssword(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_PASSWORD
                         }, KEY_FNAME + "=?",
                new String[] { String.valueOf(username) }, null, null, null, null);

        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        String password = cursor.getString(0);
        return password;
    }

    public List<Doctor> getDoctors(String specialisation,String location)
    {
        List<Doctor> all_doctors = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query="Select * from "+TABLE_DOCTORS +" where "+KEY_LOCATION+" ='"+location+"' AND "+KEY_SPECIALISATION+"='"+specialisation+"'";
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        for(int i=0;i<count;i++)
        {

            Doctor doctor = new Doctor(cursor.getString(1),cursor.getString(6),cursor.getString(7));
            all_doctors.add(doctor);
            if (cursor != null)
            {
                cursor.moveToNext();
            }
        }
        return  all_doctors;
    }

    public void addPatientsAppointments(int patientid, int doctorid, Date date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_DOC_ID,doctorid);
        cv.put(KEY_PAT_ID,patientid);
        cv.put(KEY_DATE, String.valueOf(date));
        db.insert(TABLE_BOOK_APPOINTMENTS, null, cv);
        db.close();
    }
    public void getData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from users",null);
        resultSet.moveToFirst();
        String username = resultSet.getString(0);
        String password = resultSet.getString(1);
    }

    public Patient getPatientDEtails(int userid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from users where userid="+userid;
        Cursor cursor = db.rawQuery(query,null);
        Patient patient =  new Patient();
        cursor.moveToFirst();
        patient.setFirstName(cursor.getString(1));
        patient.setEmail(cursor.getString(3));
        patient.setMobile(cursor.getString(4));
        patient.setAddress(cursor.getString(6));
        patient.setSymtoms(cursor.getString(8));
        patient.setBlood_group(cursor.getString(7));
        return patient;
    }

    public void updatePatientDetails(Patient patient ,int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_FNAME,patient.getFirstName());
        cv.put(KEY_EMAIL,patient.getEmail());
        cv.put(KEY_MOBILE,patient.getMobile());
        cv.put(KEY_ADDR,patient.getAddress());
        cv.put(KEY_BG,patient.getBlood_group());
        cv.put(KEY_SYM,patient.getSymtoms());

        db.update(TABLE_USERS,cv,"userid="+userid,null);
        db.close();
    }

    public List<String> getPatientsAppointments(int doctorid)
    {
        List<String> all_patients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from bookappointments where doctorid="+doctorid;
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        for(int i=0;i<count;i++)
        {
            System.out.println("User id is "+cursor.getInt(2));
            String pat_query = "Select * from users where userid="+cursor.getInt(2);
            Cursor cursor1 = db.rawQuery(pat_query,null);
            cursor1.moveToFirst();
            System.out.println("Size is "+cursor1.getCount());

            if (cursor1 != null && cursor1.getCount()!=0)
            {
                all_patients.add(cursor1.getString(1));
                cursor1.moveToNext();
            }
            cursor.moveToNext();
        }
        db.close();
        return  all_patients;
    }
    public boolean isUserExists(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from " + TABLE_USERS + " where firstname  = " + "'"+username+"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        return  true;
    }

    public boolean isDoctorExists(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from " + TABLE_DOCTORS + " where firstname  = " + "'"+username+"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        return  true;
    }

    public int getRecords()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_USERS);
        return numRows;
    }
}
