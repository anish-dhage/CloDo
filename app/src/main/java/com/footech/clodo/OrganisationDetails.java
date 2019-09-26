package com.footech.clodo;

public class OrganisationDetails {
    String email_id;
    String donor_id;
    String name;
    String phone;
    String city;

    public OrganisationDetails(){

    }

    public OrganisationDetails(String email_id, String donor_id, String name, String phone, String city) {
        this.email_id = email_id;
        this.donor_id = donor_id;
        this.name = name;
        this.phone = phone;
        this.city = city;
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

    public String getCity() {
        return city;
    }
}
