package com.example.h2otracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView loginTextView = findViewById(R.id.loginTextView);
        EditText loginEmail = findViewById(R.id.loginEmail);
        EditText loginPassword = findViewById(R.id.loginPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView loginForgotPassword = findViewById(R.id.loginForgotPassword);
        TextView loginCreateAccount = findViewById(R.id.loginCreateAccount);
        TextView loginOr = findViewById(R.id.loginOr);
        TextView loginUsing = findViewById(R.id.loginUsing);
        ImageView loginGoogle = findViewById(R.id.loginGoogle);
        View bg = findViewById(R.id.bg);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fadein);

        loginTextView.setAnimation(animation);
        loginEmail.setAnimation(animation);
        loginPassword.setAnimation(animation);
        btnLogin.setAnimation(animation);
        loginForgotPassword.setAnimation(animation);
        loginCreateAccount.setAnimation(animation);



        /*loginTextView.animate().alpha(1).setDuration(1000).setStartDelay(500);
        loginEmail.animate().alpha(1).setDuration(1000).setStartDelay(750);
        loginPassword.animate().alpha(1).setDuration(1000).setStartDelay(1000);
        btnLogin.animate().alpha(1).setDuration(1000).setStartDelay(1250);
        loginForgotPassword.animate().alpha(1).setDuration(1000).setStartDelay(1250);
        loginCreateAccount.animate().alpha(1).setDuration(1000).setStartDelay(1500);
        loginOr.animate().alpha(1).setDuration(1000).setStartDelay(1750);
        loginUsing.animate().alpha(1).setDuration(1000).setStartDelay(2000);
        loginGoogle.animate().alpha(1).setDuration(1000).setStartDelay(2250);
        bg.animate().translationY(-70).setDuration(1000).setStartDelay(500);*/

    }
}