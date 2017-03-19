package com.example.david.cfpbm;

/**
 * Created by David on 12/22/2016.
 */

public class Person {

    public String gmail;
    public String name;
    public String income;
    public String transportation;
    public String diet;
    public String utilities;
    public String health;
    public String extras;
    public static final String KEY_EMAIL = "google_email";
    public static final String KEY_NAME = "google_name";
    public static final String KEY_INCOME = "income";
    public static final String KEY_TRANS = "transportation";
    public static final String KEY_DIET = "diet";
    public static final String KEY_UTILITIES = "utilities";
    public static final String KEY_HEALTH = "health";
    public static final String KEY_EXTRAS = "extras";


    public Person(){

    }

    public Person(String gmail, String name, String income, String transportation, String diet, String utilities, String health, String extras) {
        this.gmail = gmail;
        this.name = name;
        this.income = income;
        this.transportation = transportation;
        this.diet = diet;
        this.utilities = utilities;
        this.health = health;
        this.extras = extras;
    }

    public Person(String gmail, String name) {
        this.gmail = gmail;
        this.name = name;
    }

    public String getEmail() {return gmail;}

    public void setEmail(String email) {this.gmail = email;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getIncome() {return income;}

    public void setIncome(String income) {this.income = income;}

    public String getTransportation() {return transportation;}

    public void setTransportation(String transportation) {this.transportation = transportation;}

    public String getDiet() {return diet;}

    public void setDiet(String diet) {this.diet = diet;}

    public String getUtilities() {return utilities;}

    public void setUtilities(String utilities) {this.utilities = utilities;}

    public String getHealth() {return health;}

    public void setHealth(String health) {this.health = health;}

    public String getExtras() {return extras;}

    public void setExtras(String extras) {this.extras = extras;}

}
