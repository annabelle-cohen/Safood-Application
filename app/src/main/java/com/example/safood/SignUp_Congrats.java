package com.example.safood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.safood.Fargments.SucceedFragment;

public class SignUp_Congrats extends AppCompatActivity implements SucceedFragment.FragmentSucceedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_congrats);

        SucceedFragment succeed = new SucceedFragment();
        Fragment fragment = (Fragment)getSupportFragmentManager().findFragmentById(R.id.succeed_frag);
        fragment.getView().setBackgroundColor(Color.WHITE);
        getSupportFragmentManager().beginTransaction().add(R.id.succeed_frag,succeed).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //We don't want to allow the user go back to the screen of SignUp
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void moveToLogin() {
        startActivity(new Intent(this,SignIn_Activity.class));
        finish();
    }

    @Override
    public void moveToMain() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}