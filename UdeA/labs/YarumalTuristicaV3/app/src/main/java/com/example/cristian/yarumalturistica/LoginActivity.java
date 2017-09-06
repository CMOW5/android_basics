package com.example.cristian.yarumalturistica;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText mEditTextUserName, mEditTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    String mUsername, mPassword, mEmail;

    private static final int REQUEST_CODE_REGISTER_ACTIVITY = 1;
    private static final int REQUEST_CODE_MAIN_ACTIVITY = 2;

    private static final String EXTRA_USERNAME = "com.example.cristian.yarumalturistica.username";
    private static final String EXTRA_PASSWORD = "com.example.cristian.yarumalturistica.password";
    private static final String EXTRA_EMAIL = "com.example.cristian.yarumalturistica.email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int color_int = ContextCompat.getColor(getApplicationContext(), R.color.lightblue);
        getWindow().getDecorView().setBackgroundColor(color_int);

        setContentView(R.layout.activity_login);

        mEditTextUserName = (EditText) findViewById(R.id.eTLoginUsername);
        mEditTextPassword = (EditText) findViewById(R.id.eTLoginPassword);
        mButtonLogin = (Button) findViewById(R.id.bLogin);
        mTextViewRegister = (TextView) findViewById(R.id.tViewRegister);

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivityForResult(i,REQUEST_CODE_REGISTER_ACTIVITY);

            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mUsername = "cristian";
                //mPassword = "123";
                //mEmail = "c@mow";

               // Intent i = MainActivity.newIntent(LoginActivity.this, mUsername, mPassword, mEmail);
               // startActivityForResult(i,REQUEST_CODE_MAIN_ACTIVITY);
                //startActivity(i);
                //finish();

                if(validateData(mEditTextUserName.getText().toString(),
                             mEditTextPassword.getText().toString())) {

                    Intent i = MainActivity.newIntent(LoginActivity.this, mUsername, mPassword, mEmail);
                    startActivityForResult(i,REQUEST_CODE_MAIN_ACTIVITY);
                    //startActivity(i);
                    //finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.invalid_data,
                                    Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_REGISTER_ACTIVITY)
        {
            if(resultCode == RESULT_OK) {
                mUsername = data.getExtras().getString(EXTRA_USERNAME);
                mPassword = data.getExtras().getString(EXTRA_PASSWORD);
                mEmail = data.getExtras().getString(EXTRA_EMAIL);
                /*
                Toast.makeText(this, mUsername+" " + mPassword + " " + mEmail
                                ,Toast.LENGTH_SHORT).show();
                */
            }
            else{
                Toast.makeText(this, R.string.register_error, Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == REQUEST_CODE_MAIN_ACTIVITY){
            if(resultCode == RESULT_OK){

                //no es necesario
                //mUsername = data.getExtras().getString(EXTRA_USERNAME);
                //mPassword = data.getExtras().getString(EXTRA_PASSWORD);
                //mEmail = data.getExtras().getString(EXTRA_EMAIL);
                /*
                Toast.makeText(this, mUsername+" " + mPassword + " " + mEmail
                        ,Toast.LENGTH_SHORT).show();
                */
            }

        }


    }

    public static Intent newIntent(Context packageContext, String username, String password, String email){

        Intent i = new Intent(packageContext,LoginActivity.class);
        i.putExtra(EXTRA_USERNAME, username);
        i.putExtra(EXTRA_PASSWORD, password);
        i.putExtra(EXTRA_EMAIL, email);
        return i;
    }

    private boolean validateData(String username, String password){

        if(username.equals(mUsername)){
            if(password.equals(mPassword)){
                return true;
            }
        }

        return false;

    }

}
