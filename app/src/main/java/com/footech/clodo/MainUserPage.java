package com.footech.clodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.R.layout;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainUserPage extends AppCompatActivity {

    private FirebaseDatabase firebaseDB;
    private DatabaseReference orgDatabase;
    private ListView lv1;

    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_page);

        firebaseDB = FirebaseDatabase.getInstance();
        orgDatabase = firebaseDB.getReference().child("Organisation");
        lv1 = (ListView) findViewById(R.id.lv) ;

        orgDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<String>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    OrganisationDetails p = dataSnapshot1.getValue(OrganisationDetails.class);
                    list.add(p.getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,list);
                lv1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
