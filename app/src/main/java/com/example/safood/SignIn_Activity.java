package com.example.safood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.safood.Common.Common;
import com.example.safood.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn_Activity extends AppCompatActivity {
    private EditText edtPhone,edtPassword;
    private Button btnSignIn;
    private FirebaseDatabase db;
    private DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_);

        btnSignIn=(Button)findViewById(R.id.btnSignIn2);
        edtPhone=(MaterialEditText)findViewById(R.id.edtPhone);
        edtPassword=(MaterialEditText)findViewById(R.id.edtPassword);

        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                        //Check if user not exist in database
                        if (snapshot.child(edtPhone.getText().toString()).exists()) {

                            //Get User Information
                            Common.currentUser = snapshot.child(edtPhone.getText().toString()).getValue(User.class);

                            if (Common.currentUser.getPassword().equals(edtPassword.getText().toString())) {

                                Intent home = new Intent(SignIn_Activity.this,HomeSafood.class);
                                startActivity(home);

                                //maybe later change to loading message.
                                finish();
                            } else {
                                edtPassword.setError(getString(R.string.wrongPass));
                            }

                        } else {
                            Toast.makeText(SignIn_Activity.this, R.string.userNotExist, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                    }
                });
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //We don't want to allow the user go back to the screen of SignUp
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}