package com.example.health_up;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.DatabaseUtils;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBMgr extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "healthupDB1";

    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "userid";
    private static final String KEY_FNAME = "firstname";
    private static final String KEY_LNAME = "lastname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_PASSWORD = "password";


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

    public DBMgr(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FNAME + " TEXT,"
                + KEY_LNAME + " TEXT," + KEY_EMAIL + " TEXT," + KEY_MOBILE + " TEXT," + KEY_PASSWORD+" TEXT" +")";

        String CREATE_DOCTOR_TABLE = "CREATE TABLE " + TABLE_DOCTORS + "("
                + KEY_DID + " INTEGER PRIMARY KEY," + KEY_DFNAME + " TEXT,"
                + KEY_DLNAME + " TEXT," + KEY_DEMAIL + " TEXT," + KEY_DMOBILE + " TEXT," +  KEY_LICENSE + " TEXT," + KEY_SPECIALISATION + " TEXT," + KEY_LOCATION + " TEXT," + KEY_EXPERIENCE+ " TEXT," + KEY_DPASSWORD+" TEXT" +")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_DOCTOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS );
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS );
        onCreate(db);
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
        System.out.println("Count is "+count);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        for(int i=0;i<count;i++)
        {
            System.out.println(cursor.getString(1)+cursor.getString(6)+cursor.getString(7));
            Doctor doctor = new Doctor(cursor.getString(1),cursor.getString(6),cursor.getString(7));
            all_doctors.add(doctor);
            if (cursor != null)
            {
                cursor.moveToNext();
            }
        }
        return  all_doctors;
    }
    public void getData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from users",null);
        resultSet.moveToFirst();
        String username = resultSet.getString(0);
        String password = resultSet.getString(1);
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
