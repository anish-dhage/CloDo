package com.footech.clodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {

    private EditText regUsername, regPassword, regName, regPhone, regAddress;
    private Button regButton;
    private FirebaseDatabase firebaseDB;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference donorDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        regButton = (Button) findViewById(R.id.reg_button);
        regUsername = (EditText) findViewById(R.id.reg_username);
        regPassword = (EditText) findViewById(R.id.reg_password);
        regName = (EditText) findViewById(R.id.reg_name);
        regPhone = (EditText) findViewById(R.id.reg_phone);
        regAddress = (EditText) findViewById(R.id.reg_address) ;

        firebaseDB = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        donorDatabase = firebaseDB.getReference("Donor");

        regButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user_name = regName.getText().toString();
                String phone_num = regPhone.getText().toString();
                String id = donorDatabase.push().getKey();
                String email = regUsername.getText().toString().trim();
                String address = regAddress.getText().toString();
                if (!(TextUtils.isEmpty(user_name) && TextUtils.isEmpty(phone_num) && TextUtils.isEmpty(email) && TextUtils.isEmpty(address))){
                    DonorDetails newDonor= new DonorDetails(address,email,id,user_name,phone_num);
                    donorDatabase.child(id).setValue(newDonor);
                    Log.i("Register",newDonor.getAddress());
                    Toast.makeText(getApplicationContext(), "Donor Added", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(RegisterPage.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
