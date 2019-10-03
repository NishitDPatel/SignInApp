package com.example.rushi.signinapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    boolean found_user, match_password;
    public static User user;
    public static String EXTRA_USERNAME = "USERNAME";
    public static String EXTRA_FULL = "FULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user = auth.getCurrentUser();
    }

//    public void login(View view)
//    {
//        username = (EditText) findViewById(R.id.username);
//        password = (EditText) findViewById(R.id.password);
//        final String susername, spassword;
//        susername = username.getText().toString();
//        spassword = password.getText().toString();
//
//
//        found_user = false;
//        match_password = false;
//        database.child("users")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String sfullname="";
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            user = snapshot.getValue(User.class);
//                            if(user.getUsername().equals(susername)) {
//                                found_user = true;
//                                if(user.getPassword().equals(spassword)){
//                                    match_password = true;
//                                    sfullname = user.getFullname();
//                                    Toast.makeText(getApplicationContext(), "Hello "+susername+". You've successfully signed in!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//                        if(found_user && !match_password)
//                            Toast.makeText(getApplicationContext(), "Password does not match. Please try again.", Toast.LENGTH_SHORT).show();
//                        else if(!found_user)
//                            Toast.makeText(getApplicationContext(), "Username not found. Please sign up for iMedicine", Toast.LENGTH_SHORT).show();
//                        else if(found_user && match_password)
//                        {
//                            //Same problem happening here
//                            //TODO next activity
//                            Intent success = new Intent(getApplicationContext(), Success.class);
//                            success.putExtra(EXTRA_USERNAME, susername);
//                            success.putExtra(EXTRA_FULL,sfullname);
//                            startActivity(success);
//                        }
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Toast.makeText(getApplicationContext(),"cancel", Toast.LENGTH_SHORT).show();
//                    }
//                });
//        username.setText("");
//        password.setText("");
//    }

    public void signup(View view)
    {
        Intent nextscreen = new Intent(getApplicationContext(), SignUp.class);
        startActivityForResult(nextscreen, 3);
    }
}
