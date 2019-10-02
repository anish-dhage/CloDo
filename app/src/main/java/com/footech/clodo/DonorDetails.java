package com.footech.clodo;

import java.io.Serializable;

public class DonorDetails implements Serializable {
    String email_id;
    String donor_id;
    String address;
    String name;
    String phone;

    public DonorDetails(){

    }

    public DonorDetails(String myaddress, String email_id, String donor_id, String name, String phone) {
        this.email_id = email_id;
        this.address = myaddress;
        this.donor_id = donor_id;
        this.name = name;
        this.phone = phone;

    }

    public String getEmail_id() {
        return email_id;
    }

    public String getDonor_id() {
        return donor_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
