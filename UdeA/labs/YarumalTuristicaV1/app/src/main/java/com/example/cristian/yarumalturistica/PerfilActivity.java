package com.example.cristian.yarumalturistica;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    private static final String EXTRA_USERNAME = "com.example.cristian.yarumalturistica.username";
    private static final String EXTRA_PASSWORD = "com.example.cristian.yarumalturistica.password";
    private static final String EXTRA_EMAIL = "com.example.cristian.yarumalturistica.email";

    private TextView mTextViewUsername, mTextViewEmail;

    private String mUsername, mPassword, mEmail;

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setBackgroundColor(Color.GREEN);

        setContentView(R.layout.activity_perfil);

        Bundle extras = getIntent().getExtras();
        mUsername = extras.getString(EXTRA_USERNAME);
        mPassword = extras.getString(EXTRA_PASSWORD);
        mEmail = extras.getString(EXTRA_EMAIL);

        mTextViewUsername = (TextView) findViewById(R.id.tV_perfilUsername);
        mTextViewEmail = (TextView) findViewById(R.id.tV_perfilEmail);

        mTextViewUsername.setText(mUsername);
        mTextViewEmail.setText(mEmail);

    }

    public static Intent newIntent(Context packageContext,
                                   String username, String password, String email){

        Intent i = new Intent(packageContext,PerfilActivity.class);
        i.putExtra(EXTRA_USERNAME,username);
        i.putExtra(EXTRA_PASSWORD,password);
        i.putExtra(EXTRA_EMAIL,email);
        return i;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case (R.id.menu2ItemClose):

                i = MainActivity.newIntent(PerfilActivity.this, mUsername, mPassword, mEmail);
                //startActivity(i);
                setResult(RESULT_FIRST_USER, i);
                finish();

                break;

            case (R.id.menu2ItemMain):

                i = MainActivity.newIntent(PerfilActivity.this, mUsername, mPassword, mEmail);
                setResult(RESULT_OK,i);
                //startActivity(i);
                finish();
                break;

        }


        return super.onOptionsItemSelected(item);
    }
}
