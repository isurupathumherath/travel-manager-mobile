package com.eadsliit.travel_manager_mobile;

public class User {
    private String firstName;
    private String lastName;
    private String nic;
    private String email;
    private String mobile;
    private String password;

    public User() {
        // Default constructor required for Firebase Realtime Database
    }

    public User(String firstName, String lastName, String nic, String email, String mobile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    public User(String firstName, String lastName, String nic, String email, String mobile, String password) {
    }

    // Getter and setter methods for the fields (if needed)

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

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
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

