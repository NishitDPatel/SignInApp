package com.example.rushi.signinapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity
{
    public DatabaseReference mDatabase;
    boolean found_user;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void onSignup(View view)
    {
        final EditText fullname = (EditText)findViewById(R.id.fullname);
        final EditText phonenumber = (EditText)findViewById(R.id.phonenumber);
        final EditText username = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.password);
        final EditText repassword = (EditText)findViewById(R.id.repassword);

        final String sfullname,sphonenumber,susername,spassword,srepassword;
        sfullname = fullname.getText().toString();
        sphonenumber = phonenumber.getText().toString();
        susername = username.getText().toString();
        spassword = password.getText().toString();
        srepassword = repassword.getText().toString();
        found_user=false;

        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    //Toast.makeText(getApplicationContext(),"-"+user.getUsername()+"-"+susername+"-",Toast.LENGTH_SHORT).show();
                    if(user.getUsername().equals(susername)) {
                        found_user = true;
                    }
                }

                if(found_user)
                    Toast.makeText(getApplicationContext(), "Username is unavailable.", Toast.LENGTH_SHORT).show();
                else if(sfullname.equals("") || sphonenumber.equals("") || susername.equals("") || spassword.equals("") || srepassword.equals(""))
                    Toast.makeText(getApplicationContext(), "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                else if(!spassword.equals(srepassword))
                    Toast.makeText(getApplicationContext(),"Password does not match with re-entered password!",Toast.LENGTH_SHORT).show();
                else if(!found_user)
                    writeNewUser(sfullname,sphonenumber,susername,spassword);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"cancel", Toast.LENGTH_SHORT).show();
            }
        });

        fullname.setText("");
        phonenumber.setText("");
        username.setText("");
        password.setText("");
        repassword.setText("");
    }

    private void writeNewUser(String fullname, String phonenumber, String username, String password)
    {
        Toast.makeText(getApplicationContext(),"New user: "+username+" added!",Toast.LENGTH_SHORT).show();
        User user = new User(fullname,phonenumber,username,password);
        mDatabase.child("users").child(user.getUsername()).setValue(user);
        Intent mainscreen = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainscreen);
    }
}
