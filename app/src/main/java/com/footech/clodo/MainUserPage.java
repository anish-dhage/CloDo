package com.footech.clodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainUserPage extends AppCompatActivity {

    private FirebaseDatabase firebaseDB;
    private DatabaseReference orgDatabase;
    private RecyclerView rec_view;
    AdapterOrganisation mAdapter;


    ArrayList<OrganisationDetails> list;

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

                mAdapter = new AdapterOrganisation(list);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                rec_view.setLayoutManager(mLayoutManager);
                rec_view.setItemAnimator(new DefaultItemAnimator());
                rec_view.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
