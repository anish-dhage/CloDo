package com.footech.clodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    OrganisationDetails mOrg;

    Button clear_accepted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_accepted);

        Intent intent1 = getIntent();
        clear_accepted = (Button) findViewById(R.id.clear_accepted);
        mOrg =(OrganisationDetails) intent1.getSerializableExtra("Org");
        String name = " ";
        name = mOrg.getName();
        Log.i("name1",mOrg.getName());
        initDonations();

        clear_accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orgDatabase = FirebaseDatabase.getInstance().getReference().child("Accepted").child(mOrg.getName());
                orgDatabase.removeValue();
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

    private void initDonations(){
        firebaseDB = FirebaseDatabase.getInstance();
        orgDatabase = firebaseDB.getReference().child("Accepted").child(mOrg.getName());
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
}
