package com.footech.clodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterOrgPage extends AppCompatActivity {

    private EditText regUsername, regPassword, regName, regPhone, regCity;
    private Button regButton;
    private FirebaseDatabase firebaseDB;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference orgDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_org_page);

        regButton = (Button) findViewById(R.id.reg_button);
        regUsername = (EditText) findViewById(R.id.reg_username);
        regPassword = (EditText) findViewById(R.id.reg_password);
        regName = (EditText) findViewById(R.id.reg_name);
        regPhone = (EditText) findViewById(R.id.reg_phone);
        regCity = (EditText) findViewById(R.id.reg_city);

        firebaseDB = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        orgDatabase = firebaseDB.getReference("Organisation");

        regButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user_name = regName.getText().toString();
                String phone_num = regPhone.getText().toString();
                String id = orgDatabase.push().getKey();
                String email = regUsername.getText().toString().trim();
                String city = regCity.getText().toString();
                if (!(TextUtils.isEmpty(user_name) && TextUtils.isEmpty(phone_num) && TextUtils.isEmpty(phone_num))){
                    OrganisationDetails newOrg= new OrganisationDetails(email,id,user_name,phone_num,city);
                    orgDatabase.child(id).setValue(newOrg);
                    Toast.makeText(getApplicationContext(), "Organisation Added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Enter Details", Toast.LENGTH_SHORT).show();
                    return;
                }

                register_to_database(regUsername.getText().toString().trim(), regPassword.getText().toString().trim());


                // finish();
            }
        });
    }

    protected void register_to_database(String email,String password) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();finish();
                        } else {
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(RegisterOrgPage.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }
}
