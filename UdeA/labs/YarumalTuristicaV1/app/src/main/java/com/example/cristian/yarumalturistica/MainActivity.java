package com.example.cristian.yarumalturistica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_USERNAME = "com.example.cristian.yarumalturistica.username";
    private static final String EXTRA_PASSWORD = "com.example.cristian.yarumalturistica.password";
    private static final String EXTRA_EMAIL = "com.example.cristian.yarumalturistica.email";

    private static final int REQUEST_CODE_PROFILE_ACTIVITY = 1;

    private String mUsername, mPassword, mEmail;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int color_int = ContextCompat.getColor(getApplicationContext(), R.color.lightblue);
        getWindow().getDecorView().setBackgroundColor(color_int);

        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        mUsername = extras.getString(EXTRA_USERNAME);
        mPassword = extras.getString(EXTRA_PASSWORD);
        mEmail = extras.getString(EXTRA_EMAIL);
        /*
        Toast.makeText(this,mUsername + " " + mPassword
                        + " " + mEmail, Toast.LENGTH_SHORT).show();
        */
    }

    public static Intent newIntent(Context packageContext, String username, String password, String email){

        Intent i = new Intent(packageContext, MainActivity.class);
        i.putExtra(EXTRA_USERNAME,username);
        i.putExtra(EXTRA_EMAIL,email);
        i.putExtra(EXTRA_PASSWORD,password);
        return i;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case (R.id.menuItemClose):

                i = LoginActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail);
                setResult(RESULT_OK,i);
                //startActivity(i);
                finish();

                break;

            case (R.id.menuItemPerfil):

                i = PerfilActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail);
                startActivityForResult(i,REQUEST_CODE_PROFILE_ACTIVITY);
                //startActivity(i);
                //finish();

                break;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_PROFILE_ACTIVITY)
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
            else if (resultCode == RESULT_FIRST_USER){

                i = LoginActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail);
                setResult(RESULT_OK,i);
                finish();
            }
            else{
                //Toast.makeText(this, R.string.register_error, Toast.LENGTH_SHORT).show();
            }
        }


    }
}
