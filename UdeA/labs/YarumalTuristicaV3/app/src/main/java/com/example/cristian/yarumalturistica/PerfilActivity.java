package com.example.cristian.yarumalturistica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class PerfilActivity extends AppCompatActivity {

    private static final String EXTRA_FROM_WHICH_ACTIVITY = "com.example.cristian.yarumalturistica.activity";
    private static final String EXTRA_USERNAME = "com.example.cristian.yarumalturistica.username";
    private static final String EXTRA_PASSWORD = "com.example.cristian.yarumalturistica.password";
    private static final String EXTRA_EMAIL = "com.example.cristian.yarumalturistica.email";

    private static final int REQUEST_CODE_ESTABLISHMENT_ACTIVITY = 2;

    private String MENU_ITEM0_TITTLE, MENU_ITEM1_TITTLE, MENU_ITEM2_TITTLE, MENU_ITEM3_TITTLE,
                   MENU_ITEM4_TITTLE;


    private final int RESTAURANTS = 0;
    private final int HOTELS = 1;
    private final int TOURISTIC_SITES = 2;

    private TextView mTextViewUsername, mTextViewEmail;
    private int mGoToEstablishment;

    private String mUsername, mPassword, mEmail, mActivity;

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setBackgroundColor(Color.GREEN);

        setContentView(R.layout.activity_perfil);

        ActionBar bar = getSupportActionBar();
        //bar.setBackgroundDrawable();
        bar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        bar.setTitle(getResources().getString(R.string.myProfile));

        //get data from previous activity
        Bundle extras = getIntent().getExtras();
        mUsername = extras.getString(EXTRA_USERNAME);
        mPassword = extras.getString(EXTRA_PASSWORD);
        mEmail = extras.getString(EXTRA_EMAIL);
        mActivity = extras.getString(EXTRA_FROM_WHICH_ACTIVITY);

        //init widgets
        mTextViewUsername = (TextView) findViewById(R.id.tV_perfilUsername);
        mTextViewEmail = (TextView) findViewById(R.id.tV_perfilEmail);

        mTextViewUsername.setText(mUsername);
        mTextViewEmail.setText(mEmail);

        //init menu Strings
        MENU_ITEM0_TITTLE = getResources().getString(R.string.restaurants);
        MENU_ITEM1_TITTLE = getResources().getString(R.string.hotels);
        MENU_ITEM2_TITTLE = getResources().getString(R.string.touristic_sites);
        MENU_ITEM3_TITTLE = getResources().getString(R.string.main);
        MENU_ITEM4_TITTLE = getResources().getString(R.string.close_session);

        //Toast.makeText(this, mActivity, Toast.LENGTH_SHORT).show();

    }

    public static Intent newIntent(Context packageContext, String fromActivity,
                                   String username, String password, String email){

        Intent i = new Intent(packageContext,PerfilActivity.class);
        i.putExtra(EXTRA_USERNAME,username);
        i.putExtra(EXTRA_PASSWORD,password);
        i.putExtra(EXTRA_EMAIL,email);
        i.putExtra(EXTRA_FROM_WHICH_ACTIVITY,fromActivity);
        return i;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.getItem(0).setTitle(MENU_ITEM0_TITTLE);
        menu.getItem(1).setTitle(MENU_ITEM1_TITTLE);
        menu.getItem(2).setTitle(MENU_ITEM2_TITTLE);
        menu.getItem(3).setTitle(MENU_ITEM3_TITTLE);
        menu.getItem(4).setTitle(MENU_ITEM4_TITTLE);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

                case (R.id.menuItem0): //Goto Restaurants

                    mGoToEstablishment = RESTAURANTS;
                    i = EstablishmentActivity.newIntent(PerfilActivity.this, mUsername, mPassword, mEmail,
                            mGoToEstablishment);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    /*
                     * A->B->C->D
                     *
                     * if in D you want to start activity B (from stack and not from a nee instance) then you
                     * can use FLAG_ACTIVITY_CLEAR_TOP to remove all the other activities on Top of B (C and D)
                     */

                    //Toast.makeText(this, "Intent", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this, "mActivity ="+mActivity, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this, "CLASS ="+EstablishmentActivity.class.getSimpleName(), Toast.LENGTH_SHORT).show();
                    if(mActivity.equals(EstablishmentActivity.class.getSimpleName())) {
                        //Toast.makeText(this, "setResult and Finish", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        //setResult(RESULT_OK,i);
                        finish();
                    }
                    else{
                        //Toast.makeText(this, "start and finish", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        finish();
                    }

                    //setResult(RESULT_OK,i);
                    //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
                    //startActivity(i);
                    //finish();

                    break;

                case (R.id.menuItem1): //Goto Hotels

                    mGoToEstablishment = HOTELS;
                    i = EstablishmentActivity.newIntent(PerfilActivity.this, mUsername, mPassword, mEmail,
                            mGoToEstablishment);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    if(mActivity.equals(EstablishmentActivity.class.getSimpleName())) {
                        //setResult(RESULT_OK,i);
                        startActivity(i);
                        finish();
                    }
                    else{
                        startActivity(i);
                        finish();
                    }

                    //setResult(RESULT_OK,i);
                    //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
                    //startActivity(i);
                    //finish();

                    break;

                case (R.id.menuItem2): //Goto Touristic Sites

                    mGoToEstablishment = TOURISTIC_SITES;
                    i = EstablishmentActivity.newIntent(PerfilActivity.this, mUsername, mPassword, mEmail,
                            mGoToEstablishment);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                    if(mActivity.equals(EstablishmentActivity.class.getSimpleName())) {
                        //setResult(RESULT_OK,i);
                        startActivity(i);
                        finish();
                    }
                    else{
                        startActivity(i);
                        finish();
                    }

                    //setResult(RESULT_OK,i);
                    //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
                    //startActivity(i);
                    //finish();

                    break;

                case (R.id.menuItem3):
                    //toMain

                    i = MainActivity.newIntent(PerfilActivity.this, mUsername, mPassword, mEmail);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(i);
                    finish();

                    //startActivity(i);

                    break;

                case (R.id.menuItem4):
                    //close session
                    i = MainActivity.newIntent(PerfilActivity.this, mUsername, mPassword, mEmail);
                    //startActivity(i);
                    setResult(RESULT_FIRST_USER, i);
                    finish();

                break;

        }


        return super.onOptionsItemSelected(item);
    }
}
