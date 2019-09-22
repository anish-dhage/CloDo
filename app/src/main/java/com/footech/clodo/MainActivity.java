package com.footech.clodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText username_edit;
    private EditText password_edit;
    private Button login,register;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username_edit = (EditText) findViewById(R.id.username_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        login = (Button) findViewById(R.id.login_button);
        register = (Button) findViewById(R.id.register_button);
        mAuth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(username_edit.getText().toString(),password_edit.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_intent = new Intent(getApplicationContext(),RegisterPage.class);
                startActivity(reg_intent);
            }
        });
    }

    private void validate(String username, String password){
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
                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(MainActivity.this, MainUserPage.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
