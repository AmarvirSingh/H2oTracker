package com.example.h2otracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView profileName, profileGender, profileHeight, profileWeight, profileAge;
    Button logout;
    GoogleSignInAccount account;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileAge = findViewById(R.id.profileAge);
        profileGender = findViewById(R.id.profileGender);
        profileName = findViewById(R.id.profileName);
        profileHeight = findViewById(R.id.profileHeight);
        profileWeight = findViewById(R.id.profileWeight);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");

        /*

        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            reference = FirebaseDatabase.getInstance().getReference("User");
            mAuth = FirebaseAuth.getInstance();

            user = mAuth.getCurrentUser();

        }
*/
/*

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
*/


        reference.child(user.getUid()).child("UserInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile = snapshot.getValue(User.class);
                String name, age, height, weight, gender;

                if (profile != null) {
                    name = profile.getFullName();
                    age = profile.getAge();
                    height = profile.getHeight();
                    weight = profile.getWeight();
                    gender = profile.getGender();

                    profileName.setText(name);
                    profileAge.setText(age + " Years Old");
                    profileWeight.setText(weight +" KG");
                    profileHeight.setText(height + " CM");
                    profileGender.setText(gender);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profileHelp:
                Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.help_layout);

                Button dismissDialog = dialog.findViewById(R.id.dismissDialog);
                dismissDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}