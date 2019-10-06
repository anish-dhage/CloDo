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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowAccepted extends AppCompatActivity {
    private FirebaseDatabase firebaseDB;
    private DatabaseReference orgDatabase;

    private RecyclerView rec_view;

    AdapterDonations mAdapter;
    ArrayList<Donations> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_accepted);

        firebaseDB = FirebaseDatabase.getInstance();
        orgDatabase = firebaseDB.getReference().child("Accepted");
        rec_view = (RecyclerView) findViewById(R.id.accepted_recview) ;

        orgDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Donations>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
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
                Donations mDon = list.get(position);
                Toast.makeText(getApplicationContext(), mDon.getDonation(), Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rec_view.setLayoutManager(mLayoutManager);
        rec_view.setItemAnimator(new DefaultItemAnimator());
        rec_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
