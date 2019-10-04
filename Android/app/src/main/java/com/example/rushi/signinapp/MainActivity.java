package com.example.rushi.signinapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
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

    public static User user;
    public static String EXTRA_USERNAME = "USERNAME";
    public static String EXTRA_FULL = "FULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String susername = username.getText().toString() + "@rushi.example.com";
                String spassword = password.getText().toString();
                if(susername.length() > 0 && spassword.length() > 0) {
                    auth.signInWithEmailAndPassword(susername, spassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            login(authResult.getUser());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        auth.signOut();
    }

    private void login(FirebaseUser authUser){
        if(authUser != null && !authUser.getUid().equals("")){
            database.child("users").child(authUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent success = new Intent(getApplicationContext(), Success.class);
                            success.putExtra(EXTRA_USERNAME, user.username);
                            success.putExtra(EXTRA_FULL,user.fullname);
                            startActivity(success);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    public void signup(View view)
    {
        Intent nextscreen = new Intent(getApplicationContext(), SignUp.class);
        startActivityForResult(nextscreen, 3);
    }
}
