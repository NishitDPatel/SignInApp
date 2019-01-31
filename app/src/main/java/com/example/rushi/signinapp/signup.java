package com.example.rushi.signinapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
    public void onSignup(View view)
    {
        final EditText fullname = (EditText)findViewById(R.id.fullname);
        final EditText phonenumber = (EditText)findViewById(R.id.phonenumber);
        final EditText username = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.password);
        final EditText repassword = (EditText)findViewById(R.id.repassword);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("fullname");
        myRef.setValue(fullname.getText().toString());

        myRef = database.getReference("phonenumber");
        myRef.setValue(phonenumber.getText().toString());

        myRef = database.getReference("username");
        myRef.setValue(username.getText().toString());

        myRef = database.getReference("password");
        myRef.setValue(password.getText().toString());
    }
}
