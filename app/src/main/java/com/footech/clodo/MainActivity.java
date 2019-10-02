package com.footech.clodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText username_edit;
    private EditText password_edit;
    private Button login;
    private TextView register_as_donor, register_as_organisation;
    private RadioButton org_radio, donor_radio;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username_edit = (EditText) findViewById(R.id.username_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        login = (Button) findViewById(R.id.login_button);
        register_as_donor = (TextView) findViewById(R.id.reg_as_donor_text);
        register_as_organisation = (TextView) findViewById(R.id.reg_as_org_text);
        org_radio = (RadioButton) findViewById(R.id.organization_radio);
        donor_radio = (RadioButton) findViewById(R.id.donor_radio);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(username_edit.getText().toString(),password_edit.getText().toString());
            }
        });

        register_as_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_intent = new Intent(getApplicationContext(),RegisterPage.class);
                startActivity(reg_intent);
            }
        });

        register_as_organisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_intent = new Intent(getApplicationContext(),RegisterOrgPage.class);
                startActivity(reg_intent);
            }
        });
    }

    private void validate(final String username, String password){
        //Make Custom, fetch from Firebase
        if(TextUtils.isEmpty(username)){
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    if(donor_radio.isChecked()){

                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MainActivity.this, MainUserPage.class);
                        intent.putExtra("email_id",username);
                        startActivity(intent);
                    }
                    else if(org_radio.isChecked()){
                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MainActivity.this, OrganisationMainPage.class);
                        intent.putExtra("email_id",username);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Select an option (Organisation or Donor)", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
