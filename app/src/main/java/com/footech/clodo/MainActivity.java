package com.footech.clodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText username_edit;
    private EditText password_edit;
    private Button login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username_edit = (EditText) findViewById(R.id.username_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        login = (Button) findViewById(R.id.login_button);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(username_edit.getText().toString(),password_edit.getText().toString());
            }
        });
    }

    private void validate(String username, String password){
        if(username=="Admin" && password=="1234"){
            Intent intent = new Intent(MainActivity.this, MainUserPage.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Naahi Zhaalaa", Toast.LENGTH_SHORT).show();
        }
    }


}
