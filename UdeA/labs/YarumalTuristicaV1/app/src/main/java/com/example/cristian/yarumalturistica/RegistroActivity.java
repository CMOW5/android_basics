package com.example.cristian.yarumalturistica;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    EditText mEditTextUsername, mEditTextPassword1, mEditTextPassword2, mEditTextEmail;
    Button mButtonCancel, mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int color_int = ContextCompat.getColor(getApplicationContext(), R.color.lightblue);
        getWindow().getDecorView().setBackgroundColor(color_int);

        setContentView(R.layout.activity_registro);

        mEditTextUsername = (EditText) findViewById(R.id.eTRegisterUsername);
        mEditTextPassword1 = (EditText) findViewById(R.id.eTRegisterPassword1);
        mEditTextPassword2 = (EditText) findViewById(R.id.eTRegisterPassword2);
        mEditTextEmail = (EditText) findViewById(R.id.eTRegisterEmail);

        mButtonCancel = (Button) findViewById(R.id.bRegisterCancel);
        mButtonRegister = (Button) findViewById(R.id.bRegisterStart);

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                setResult(RESULT_CANCELED,i);
                finish();
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mEditTextUsername.getText().toString();
                String password1 = mEditTextPassword1.getText().toString();
                String password2 = mEditTextPassword2.getText().toString();
                String email = mEditTextEmail.getText().toString();

                if(validateData(username, email, password1 , password2)){

                    Intent i = LoginActivity.newIntent(RegistroActivity.this,
                                                       username,password1,email);
                    //Intent i = LoginActivity.newIntent(username,password1,email);
                    setResult(RESULT_OK,i);
                    finish();
                }

            }
        });

    }

    private boolean validateData(String dataUsername, String dataEmail, String dataPassword1,
                                 String dataPassword2 ){

        if(dataUsername.matches("")){
            Toast.makeText(this,R.string.invalid_username, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (dataPassword1.matches("")){
            Toast.makeText(this,R.string.invalid_password, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (dataEmail.matches("")){
            Toast.makeText(this,R.string.invalid_email, Toast.LENGTH_SHORT).show();
            return false;
        }
        else {

            if(dataPassword1.equals(dataPassword2)){
                return true;
            }
            else{
                Toast.makeText(this,R.string.password_does_not_match, Toast.LENGTH_SHORT).show();
                return false;
            }

        }


    }
}
