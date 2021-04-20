package com.example.h2otracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    private static final int RC_SIGN_IN = 123;
    FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    Button btnLogin;

    SharedPreferences sharedPreferencesForUserInfo;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            goToMain();

        }
    }

    private void goToMain() {

        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        // receiving user data from firebase database

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

                    Log.d("TAG", "onDataChange: " + name);


                    // saving data in shared preferences
                    sharedPreferencesForUserInfo.edit().putString("name", name).apply();
                    sharedPreferencesForUserInfo.edit().putInt("age", Integer.parseInt(age)).apply();
                    sharedPreferencesForUserInfo.edit().putInt("height", Integer.parseInt(height)).apply();
                    sharedPreferencesForUserInfo.edit().putInt("weight", Integer.parseInt(weight)).apply();
                    sharedPreferencesForUserInfo.edit().putString("gender", gender).apply();

                    // Toast.makeText(LoginActivity.this, "age in get user data" + sharedPreferencesForUserInfo.getInt("age", 0), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        startActivity(new Intent(this, MainContent.class));
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView loginTextView = findViewById(R.id.loginTextView);
        EditText loginEmail = findViewById(R.id.loginEmail);
        EditText loginPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        TextView loginForgotPassword = findViewById(R.id.loginForgotPassword);
        TextView loginCreateAccount = findViewById(R.id.loginCreateAccount);
        // TextView loginOr = findViewById(R.id.loginOr);
        TextView loginUsing = findViewById(R.id.loginUsing);
        // ImageView loginGoogle = findViewById(R.id.loginGoogle);
        //  View bg = findViewById(R.id.bg);

        sharedPreferencesForUserInfo = getSharedPreferences("UserInfo", MODE_PRIVATE);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadein);

        loginTextView.setAnimation(animation);
        loginEmail.setAnimation(animation);
        loginPassword.setAnimation(animation);
        btnLogin.setAnimation(animation);
        loginForgotPassword.setAnimation(animation);
        loginCreateAccount.setAnimation(animation);

        loginCreateAccount.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
        //getting instance
        mAuth = FirebaseAuth.getInstance();
        // creating request
        createRequest();

        loginUsing.setOnClickListener(v -> {

            signIn();
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    loginEmail.setError("Please enter email");
                    loginEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    loginEmail.setError("Please provide valid email address");
                    loginEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    loginPassword.setError("please enter password");
                    loginPassword.requestFocus();
                    return;
                }

                // progressBarLogin.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            String Uid = user.getUid();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
                            // receiving user data from firebase database

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

                                        Log.d("TAG", "onDataChange: " + name);


                                        // saving data in shared preferences
                                        sharedPreferencesForUserInfo.edit().putString("name", name).apply();
                                        sharedPreferencesForUserInfo.edit().putInt("age", Integer.parseInt(age)).apply();
                                        sharedPreferencesForUserInfo.edit().putInt("height", Integer.parseInt(height)).apply();
                                        sharedPreferencesForUserInfo.edit().putInt("weight", Integer.parseInt(weight)).apply();
                                        sharedPreferencesForUserInfo.edit().putString("gender", gender).apply();

                                        // Toast.makeText(LoginActivity.this, "age in get user data" + sharedPreferencesForUserInfo.getInt("age", 0), Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }


                            });
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // redirect to user profile
                                    startActivity(new Intent(LoginActivity.this, MainContent.class));
                                    finish();
                                }
                            }, 5000);

                        } else {
                            Toast.makeText(LoginActivity.this, "failed to logfin", Toast.LENGTH_SHORT).show();
                            // progressBarLogin.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        loginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginEmail.getText().toString().isEmpty()) {
                    loginEmail.setError("Please enter email address then press Forgot password");
                    loginEmail.requestFocus();
                    return;
                }
                mAuth.sendPasswordResetEmail(loginEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Reset password Link has been sent to the given Email ", Toast.LENGTH_LONG).show();
                            loginEmail.setText("");

                        } else {
                            Toast.makeText(LoginActivity.this, "Some Error occur, Please try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


    }

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "signin failed " + e, Toast.LENGTH_SHORT).show();
                Log.w("TAG", "Google sign in failed" + e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "signed in successfully" + user.getUid(), Toast.LENGTH_SHORT).show();
                            goToMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "sign in failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}