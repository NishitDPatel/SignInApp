package com.example.rushi.signinapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity
{
    DatabaseReference mDatabase;
    FirebaseAuth auth;
    boolean passMatch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();


        final EditText fullname = (EditText)findViewById(R.id.fullname);
        final EditText phonenumber = (EditText)findViewById(R.id.phonenumber);
        final EditText username = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.password);
        final EditText repassword = (EditText)findViewById(R.id.repassword);
        final Button signup = findViewById(R.id.signup);

        repassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(password.getText().length() > 2 && repassword.getText().length() > 2){
                    if(password.getText().toString().equals(repassword.getText().toString())) {
                        passMatch = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.password_error).setVisibility(View.GONE);
                            }
                        });
                    } else {
                        passMatch = false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.password_error).setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sfullname = fullname.getText().toString();
                final String sphonenumber = phonenumber.getText().toString();
                final String susername = username.getText().toString();
                final String spassword = password.getText().toString();
                final String srepassword = repassword.getText().toString();

                if(sfullname.length() == 0 || sphonenumber.length() == 0 || susername.length() == 0){
                    Toast.makeText(getApplicationContext(), "All fields are mandatory.", Toast.LENGTH_SHORT).show();
                } else if(passMatch) {
                    auth.createUserWithEmailAndPassword(susername + "@rushi.example.com", spassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            User user = new User(sfullname,sphonenumber,susername);
                            mDatabase.child("users").child(authResult.getUser().getUid()).setValue(user);
                            Toast.makeText(getApplicationContext(),"New user: "+ susername + " added!",Toast.LENGTH_SHORT).show();
                            SignUp.this.finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("auth error", e.toString());
                            if(e.getClass() == FirebaseAuthUserCollisionException.class)
                                Toast.makeText(getApplicationContext(), "The username is already in use by another account", Toast.LENGTH_SHORT).show();
                            else if(e.getClass() == FirebaseAuthWeakPasswordException.class)
                                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    password.requestFocus();
                }
            }
        });
    }
}
