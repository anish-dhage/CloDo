package com.footech.clodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrganisationMainPage extends AppCompatActivity {
    private FirebaseDatabase firebaseDB;
    private DatabaseReference orgDatabase, donation;

    private RecyclerView rec_view;

    AdapterDonations mAdapter;
    ArrayList<Donations> list;

    OrganisationDetails myOrg;
    String orgname = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation_main_page);

        Intent intent = getIntent();
        String org_email = intent.getStringExtra("email_id").trim();
        Log.i("name", org_email);

        rec_view = (RecyclerView) findViewById(R.id.donation_recview);
        firebaseDB = FirebaseDatabase.getInstance();
        donation = firebaseDB.getReference("Organisation");


        Query query = donation.orderByChild("email_id").equalTo(org_email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        myOrg = issue.getValue(OrganisationDetails.class);
                        Log.i("name", myOrg.getName());
                        orgname = myOrg.getName();
                        initDonations();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initDonations(){
            Log.i("name", myOrg.getName());
            orgDatabase = firebaseDB.getReference().child("Donation").child(myOrg.getName());
            orgDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list = new ArrayList<Donations>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Donations p = dataSnapshot1.getValue(Donations.class);
                        list.add(p);
                    }
                    initRecyclerView();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }

    private void initRecyclerView(){

        mAdapter = new AdapterDonations(list, new AdapterDonations.OnNoteListener() {
            @Override
            public void onNoteClick(int position) {
                Donations mOrg = list.get(position);
                Toast.makeText(getApplicationContext(), mOrg.getDonation(), Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rec_view.setLayoutManager(mLayoutManager);
        rec_view.setItemAnimator(new DefaultItemAnimator());
        rec_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}
