package com.footech.clodo;

import java.io.Serializable;

public class OrganisationDetails implements Serializable {
    String email_id;
    String org_id;
    String name;
    String phone;
    String city;

    public OrganisationDetails(){

    }

    public OrganisationDetails(String email_id, String org_id, String name, String phone, String city) {
        this.email_id = email_id;
        this.org_id = org_id;
        this.name = name;
        this.phone = phone;
        this.city = city;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getDonor_id() {
        return org_id;
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

