package com.example.safood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.safood.Common.Common;
import com.example.safood.Model.User;
import com.example.safood.Style.LoadingDialog;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp_Activity extends AppCompatActivity {
    private TextInputLayout edtPassword, edtConfirmPassword, edtPhone;
    private boolean isSignUpValid = true;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);

        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword2);
        edtConfirmPassword = findViewById(R.id.edtPassword3);
        btnSignUp = (Button)findViewById(R.id.btnSignUp2);

        final LoadingDialog loadingDialog = new LoadingDialog(SignUp_Activity.this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isPhoneValid,isPasswordValid,isPassword2Valid;
                String phone = edtPhone.getEditText().getText().toString();
                String password1 = edtPassword.getEditText().getText().toString();
                String password2 = edtConfirmPassword.getEditText().getText().toString();

                isPhoneValid = isPhoneValid(phone);
                isPasswordValid = isPasswordValid(password1);
                isPassword2Valid=isPasswordValid(password2);

                if(isPhoneValid && isPasswordValid && isPassword2Valid){
                    if(password1.equals(password2)){
                        Log.d("phone",phone);
                      //  need check if phone already exist in data base
                        Common.currentUser.setPhone(phone);
                        Common.currentUser.setPassword(password1);
                        LoadingMessageDialog(loadingDialog);
                        Intent succeed = new Intent(SignUp_Activity.this,QuestActivity.class);
                        startActivity(succeed);

                    }else{
                        Toast.makeText(SignUp_Activity.this, getString(R.string.confirmationFaild), Toast.LENGTH_LONG).show();
                    }
                }



            }
        });


    }

    private void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    private void LoadingMessageDialog(final LoadingDialog loadingDialog){
        loadingDialog.startLoadingDialog();
        Handler handler= new Handler();
        //If the user press back then the loading message dialog disappear
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // loadingDialog.dismissDialog();
            }
        },3000);
    }


    private boolean isPhoneValid(String phone){
        boolean isValid=true;

        if(phone.length() != 10){
            edtPhone.setError(getString(R.string.phone_must_be_10digits));
            isValid = false;
        }else{
            isValid = isPhoneNumberValid(phone);
        }
        return isValid;
    }


    private boolean isPhoneNumberValid(String phone){
        boolean isValid = true;
        char [] digits = phone.toCharArray();
        if(digits[0] != '0'){
            edtPhone.setError(getString(R.string.phone_number_format));
            isValid=false;
        }else if(digits[1] != '5'){
            edtPhone.setError(getString(R.string.phone_number_format));
            isValid=false;
        }else{
            for(int i = 2;i<digits.length;i++ ){
                if(digits[1] == ' '){
                    edtPhone.setError(getString(R.string.phone_must_include_only_digits));
                    isValid=false;
                }
            }
        }
        return isValid;
    }

    private boolean isPasswordValid(String password){
        boolean isValid =true;

        if(!password.isEmpty()){

            if(isPasswordValidLength(password)){
                edtPassword.setError(null);
            }else {
                edtPassword.setError(getString(R.string.maxPassError));
                return isValid =false;
            }

        }else{
            edtPassword.setError(getString(R.string.error_empty));
            isValid=false;
        }
        return isValid;
    }

    public boolean isPasswordValidLength(String password){
        boolean isValid;
        if(password.length() < 8){
            isValid =false;
        }else{
            isValid = true;
        }
        return isValid;
    }
}