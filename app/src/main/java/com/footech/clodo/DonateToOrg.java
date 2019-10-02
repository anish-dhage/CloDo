package com.footech.clodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonateToOrg extends AppCompatActivity {

    private TextView donation;
    private Button donate_button;

    private FirebaseDatabase firebaseDB;
    private DatabaseReference donationBase;

    private Donations newDonation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_to_org);

        donation = (TextView) findViewById(R.id.donationDetails);
        donate_button = (Button) findViewById(R.id.donate_button);
        Intent intent = getIntent();
        final OrganisationDetails mOrg =  (OrganisationDetails) intent.getSerializableExtra("OrgDet");

        firebaseDB = FirebaseDatabase.getInstance();
        String orgEmail = mOrg.getEmail_id();
        donationBase = firebaseDB.getReference("Donation");

        donate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String donationDet = donation.getText().toString();
                String id = donationBase.push().getKey();

                Intent intent = getIntent();
                DonorDetails mDon = (DonorDetails) intent.getSerializableExtra("DonorDet");

                if (!(TextUtils.isEmpty(donationDet))){
                    newDonation = new Donations(donationDet,mDon.getEmail_id(),mDon.getName(),mDon.getPhone(),mDon.getAddress());
                    donationBase.child(mOrg.getName()).child(id).setValue(newDonation);
                    Toast.makeText(getApplicationContext(), "Donation Added", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "There was some Error", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });



    }
}
