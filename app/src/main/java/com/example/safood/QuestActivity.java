package com.example.safood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.safood.Common.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuestActivity extends AppCompatActivity {
    private CheckBox vega,infant,gluFree,pets,lacFree;
    private Button mContinue,mSelectAll;
    private ArrayList<String> results;
    private FirebaseDatabase db;
    private DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        vega = findViewById(R.id.vegetarianism);
        infant = findViewById(R.id.infant);
        gluFree =findViewById(R.id.gluten_free);
        pets = findViewById(R.id.pets);
        lacFree =findViewById(R.id.lactose_free);

        mContinue=findViewById(R.id.btnContinue);
        mSelectAll =findViewById(R.id.btnSelectAll);

        results = new ArrayList<>();
        db = FirebaseDatabase.getInstance();
        users= db.getReference("Users");


        vega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vega.isChecked()){
                    results.add(getString(R.string.Vegetarianism));
                }else{
                    results.remove(getString(R.string.Vegetarianism));
                }
            }
        });

        infant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(infant.isChecked()){
                    results.add(getString(R.string.Infant));
                }else{
                    results.remove(getString(R.string.Infant));
                }
            }
        });

        gluFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gluFree.isChecked()){
                    results.add(getString(R.string.Gluten_free));
                }else{
                    results.remove(getString(R.string.Gluten_free));
                }
            }
        });

        pets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pets.isChecked()){
                    results.add(getString(R.string.Pets));
                }else{
                    results.remove(getString(R.string.Pets));
                }
            }
        });
        lacFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lacFree.isChecked()){
                    results.add(getString(R.string.Lactose_free));
                }else{
                    results.remove(getString(R.string.Lactose_free));
                }
            }
        });

        mSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vega.setChecked(true);
                infant.setChecked(true);
                gluFree.setChecked(true);
                pets.setChecked(true);
                lacFree.setChecked(true);
                if(!results.contains(getString(R.string.Vegetarianism))){
                    results.add(getString(R.string.Vegetarianism));
                }
                if(!results.contains(getString(R.string.Infant))){
                    results.add(getString(R.string.Infant));
                }
                if(!results.contains(getString(R.string.Gluten_free))){
                    results.add(getString(R.string.Gluten_free));
                }

                if(!results.contains(getString(R.string.Pets))){
                    results.add(getString(R.string.Pets));
                }

                if(!results.contains(getString(R.string.Lactose_free))){
                    results.add(getString(R.string.Lactose_free));
                }

            }
        });

        mContinue.setOnClickListener(new View.OnClickListener() {
            /**need to think how to implements jason file*/
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                if(results.size()>0) {
                    for (String s : results)
                        stringBuilder.append(s + " ");

                    Common.currentUser.setChoice(stringBuilder.toString());
                }else{
                    Common.currentUser.setChoice(getString(R.string.def));
                }
                    sendData();

            }
        });


    }

    private void sendData() {
       users.child(Common.currentUser.getPhone()).setValue(Common.currentUser);
       Common.currentUser=null;
        Intent signUpCongrats = new Intent(QuestActivity.this,SignUp_Congrats.class);
        startActivity(signUpCongrats);
        finish();
    }
}