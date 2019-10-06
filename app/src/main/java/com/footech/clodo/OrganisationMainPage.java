package com.footech.clodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

    Button showAccepted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation_main_page);

        final Intent intent = getIntent();
        String org_email = intent.getStringExtra("email_id").trim();
        Log.i("name", org_email);

        rec_view = (RecyclerView) findViewById(R.id.donation_recview);
        firebaseDB = FirebaseDatabase.getInstance();
        donation = firebaseDB.getReference("Organisation");
        showAccepted = (Button) findViewById(R.id.accepted_btn);

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

        showAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), ShowAccepted.class);
                intent1.putExtra("Org",myOrg);
                startActivity(intent1);
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
                final Donations mDon = list.get(position);
                //Toast.makeText(getApplicationContext(), mOrg.getDonation(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(OrganisationMainPage.this);
                View mView = getLayoutInflater().inflate(R.layout.donation_dialog, null);
                TextView donor_name = (TextView) mView.findViewById(R.id.donor_name);
                TextView donor_address = (TextView) mView.findViewById(R.id.donor_address);
                TextView donation = (TextView) mView.findViewById(R.id.donation);
                Button reject_btn = (Button) mView.findViewById(R.id.reject_btn);
                Button accept_btn = (Button) mView.findViewById(R.id.accept_btn);
                donation.setText(mDon.getDonation());
                donor_address.setText(mDon.getCity());
                donor_name.setText(mDon.getDonor_name());
                accept_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"Accepted", Toast.LENGTH_SHORT).show();
                        DatabaseReference oldDonation = firebaseDB.getReference("Donation").child(myOrg.getName()).child(mDon.getId());
                        DatabaseReference newDonation = firebaseDB.getReference("Accepted").child(myOrg.getName()).child(mDon.getId());
                        newDonation.setValue(mDon);
                        oldDonation.removeValue();
                        return;
                    }
                });
                reject_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Rejected", Toast.LENGTH_SHORT).show();
                        DatabaseReference oldDonation = firebaseDB.getReference("Donation").child(myOrg.getName()).child(mDon.getId());
                        oldDonation.removeValue();
                        return;
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rec_view.setLayoutManager(mLayoutManager);
        rec_view.setItemAnimator(new DefaultItemAnimator());
        rec_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}
