package com.example.david.cfpbm;

/**
 * Created by David on 12/22/2016.
 */

public class Person {

    private String email;
    private String name;
    public static final String KEY_EMAIL = "google_email";
    public static final String KEY_NAME = "google_name";

    public Person(){}

    public Person(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
