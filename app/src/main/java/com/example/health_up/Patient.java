package com.example.health_up;

public class Patient {
    String firstName;
    String lastName;
    String email;
    String mobile;
    String password;
    public Patient(String firstName,String lastName,String email,String mobile,String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile=mobile;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
