package com.footech.clodo;

public class DonorDetails {
    String email_id;
    String donor_id;
    String name;
    String phone;
    String address;
    public DonorDetails(){

    }

    public DonorDetails(String email_id, String donor_id, String name, String phone, String address) {
        this.email_id = email_id;
        this.donor_id = donor_id;
        this.name = name;
        this.phone = phone;
        this.address = address;
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
        return phone;
    }
}
