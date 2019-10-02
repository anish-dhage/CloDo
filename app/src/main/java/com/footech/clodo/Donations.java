package com.footech.clodo;


import java.io.Serializable;

public class Donations implements Serializable {
    String donor_email_id;
    String donation;
    String donor_name;
    String donor_phone;
    String city;

    public Donations(){

    }

    public Donations(String donation, String donor_email_id, String donor_name, String donor_phone, String city){
        this.donation = donation;
        this.donor_email_id = donor_email_id;
        this.donor_name = donor_name;
        this.donor_phone = donor_phone;
        this.city = city;
    }

    public String getDonor_email_id() {
        return donor_email_id;
    }

    public String getDonation() {
        return donation;
    }

    public String getDonor_name() {
        return donor_name;
    }

    public String getDonor_phone() {
        return donor_phone;
    }
    public String getCity() {
        return city;
    }

}

