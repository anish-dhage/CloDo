package com.footech.clodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainUserPage extends AppCompatActivity {

    private FirebaseDatabase firebaseDB;
    private DatabaseReference orgDatabase, donor;

    private RecyclerView rec_view;

    AdapterOrganisation mAdapter;
    ArrayList<OrganisationDetails> list;

    DonorDetails mydonor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_page);

        firebaseDB = FirebaseDatabase.getInstance();
        orgDatabase = firebaseDB.getReference().child("Organisation");
        rec_view = (RecyclerView) findViewById(R.id.org_recycler_view) ;

        orgDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<OrganisationDetails>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    OrganisationDetails p = dataSnapshot1.getValue(OrganisationDetails.class);
                    list.add(p);
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        donor = firebaseDB.getReference().child("Donor");
        Intent intent = getIntent();
        String donor_email = intent.getStringExtra("email_id");
        Query query = donor.orderByChild("email_id").equalTo(donor_email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        mydonor = issue.getValue(DonorDetails.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initRecyclerView(){

        mAdapter = new AdapterOrganisation(list, new AdapterOrganisation.OnNoteListener() {
            @Override
            public void onNoteClick(int position) {
                OrganisationDetails mOrg = list.get(position);
                Toast.makeText(getApplicationContext(), mOrg.getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), DonateToOrg.class);
                intent.putExtra("OrgDet",mOrg);
                intent.putExtra("DonorDet", mydonor);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rec_view.setLayoutManager(mLayoutManager);
        rec_view.setItemAnimator(new DefaultItemAnimator());
        rec_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}
