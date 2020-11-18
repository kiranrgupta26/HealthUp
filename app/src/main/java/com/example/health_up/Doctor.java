package com.example.health_up;

public class Doctor {
    private String firstname;
    private String lastname;
    private String email;
    private String mobile;
    private String license;
    private String password;
    private String specialisation;
    private String location;
    private String experience;

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLicense() {
        return license;
    }

    public String getPassword() {
        return password;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public String getLocation() {
        return location;
    }

    public String getExperience() {
        return experience;
    }

    public Doctor(String firstname, String lastname, String email, String mobile, String license, String password)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.mobile = mobile;
        this.license = license;
        this.password=password;

    }
    public Doctor(String firstname, String specialisation,String location)
    {
        this.firstname =firstname;
        this.specialisation = specialisation;
        this.location = location;
    }

}
