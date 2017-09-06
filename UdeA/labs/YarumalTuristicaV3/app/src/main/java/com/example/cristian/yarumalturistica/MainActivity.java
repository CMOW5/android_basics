package com.example.cristian.yarumalturistica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener    {

    private static final String EXTRA_USERNAME = "com.example.cristian.yarumalturistica.username";
    private static final String EXTRA_PASSWORD = "com.example.cristian.yarumalturistica.password";
    private static final String EXTRA_EMAIL = "com.example.cristian.yarumalturistica.email";

    private static final int REQUEST_CODE_PROFILE_ACTIVITY = 1;
    private static final int REQUEST_CODE_ESTABLISHMENT_ACTIVITY = 2;
    private static final int REQUEST_CODE_EVENTS_ACTIVITY = 3;

    private final int RESTAURANTS = 0;
    private final int HOTELS = 1;
    private final int TOURISTIC_SITES = 2;

    private String MENU_ITEM0_TITTLE, MENU_ITEM1_TITTLE, MENU_ITEM2_TITTLE, MENU_ITEM3_TITTLE,
                     MENU_ITEM4_TITTLE, MENU_ITEM5_TITTLE;

    private String mUsername, mPassword, mEmail;
    private int mGoToEstablishment;
    //private GoToEstablishment mGoToEstablishment;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int color_int = ContextCompat.getColor(getApplicationContext(), R.color.lightblue);
        getWindow().getDecorView().setBackgroundColor(color_int);

        setContentView(R.layout.activity_main);

/*
        mUsername = "CRISTIAN";
        mPassword = "123456";
        mEmail = "c@mow.com";
*/

        Bundle extras = getIntent().getExtras();
        mUsername = extras.getString(EXTRA_USERNAME);
        mPassword = extras.getString(EXTRA_PASSWORD);
        mEmail = extras.getString(EXTRA_EMAIL);


        //init menu Strings
        MENU_ITEM0_TITTLE = getResources().getString(R.string.restaurants);
        MENU_ITEM1_TITTLE = getResources().getString(R.string.hotels);
        MENU_ITEM2_TITTLE = getResources().getString(R.string.touristic_sites);
        MENU_ITEM3_TITTLE = getResources().getString(R.string.events);
        MENU_ITEM4_TITTLE = getResources().getString(R.string.myProfile);
        MENU_ITEM5_TITTLE = getResources().getString(R.string.close_session);

        //set NavigationDrawer

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu drawerMenu = navigationView.getMenu();
        drawerMenu.findItem(R.id.menuMainDrawerItem4).setVisible(false);



        drawerMenu.findItem(R.id.menuMainDrawerItem0).setTitle(MENU_ITEM0_TITTLE);
        drawerMenu.findItem(R.id.menuMainDrawerItem1).setTitle(MENU_ITEM1_TITTLE);
        drawerMenu.findItem(R.id.menuMainDrawerItem2).setTitle(MENU_ITEM2_TITTLE);
        drawerMenu.findItem(R.id.menuMainDrawerItem3).setTitle(MENU_ITEM3_TITTLE);
        drawerMenu.findItem(R.id.menuMainDrawerItem4).setTitle(MENU_ITEM4_TITTLE);
        drawerMenu.findItem(R.id.menuMainDrawerItem5).setTitle(MENU_ITEM5_TITTLE);

        View header = navigationView.getHeaderView(0);
        TextView tVusername = (TextView) header.findViewById(R.id.textView2);
        tVusername.setText(mUsername);

        TextView tVemail = (TextView) header.findViewById(R.id.textView);
        tVemail.setText(mEmail);
        //drawerMenu.findItem(R.id.menuMainDrawerItem5).setTitle(MENU_ITEM5_TITTLE);
        //ActionBar bar = getSupportActionBar();
        //bar.setBackgroundDrawable();
        //bar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
    }

    public static Intent newIntent(Context packageContext, String username, String password, String email){

        Intent i = new Intent(packageContext, MainActivity.class);
        i.putExtra(EXTRA_USERNAME,username);
        i.putExtra(EXTRA_EMAIL,email);
        i.putExtra(EXTRA_PASSWORD,password);
        return i;
    }

    public enum GoToEstablishment {
        RESTAURANTS, HOTELS, TOURISTIC_SITES, MY_PROFILE, LOGOUT
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menuMainDrawerItem0) {
            mGoToEstablishment = RESTAURANTS;
            i = EstablishmentActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail,
                    mGoToEstablishment);
            startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);

        } else if (id == R.id.menuMainDrawerItem1) {
            mGoToEstablishment = HOTELS;
            i = EstablishmentActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail,
                    mGoToEstablishment);
            startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);

        } else if (id == R.id.menuMainDrawerItem2) {
            mGoToEstablishment = TOURISTIC_SITES;
            i = EstablishmentActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail,
                    mGoToEstablishment);
            startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);

        } else if (id == R.id.menuMainDrawerItem3) {

            i = EventsActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail);
            startActivityForResult(i,REQUEST_CODE_EVENTS_ACTIVITY);


        } else if (id == R.id.menuMainDrawerItem4) {
            i = PerfilActivity.newIntent(MainActivity.this,
                    MainActivity.this.getClass().getSimpleName(),
                    mUsername, mPassword, mEmail);
            startActivityForResult(i,REQUEST_CODE_PROFILE_ACTIVITY);

        } else if (id == R.id.menuMainDrawerItem5) {
            i = LoginActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail);
            setResult(RESULT_OK,i);
            //startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        //menu.se

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){


            case (R.id.menuItem0): //Goto Restaurants

                mGoToEstablishment = RESTAURANTS;
                i = EstablishmentActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail,
                                                    mGoToEstablishment);
                startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
                //startActivity(i);
                //finish();

                break;

            case (R.id.menuItem1): //Goto Hotels

                mGoToEstablishment = HOTELS;
                i = EstablishmentActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail,
                        mGoToEstablishment);
                startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
                //startActivity(i);
                //finish();

                break;

            case (R.id.menuItem2): //Goto Touristic Sites

                mGoToEstablishment = TOURISTIC_SITES;
                i = EstablishmentActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail,
                        mGoToEstablishment);
                startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
                //startActivity(i);
                //finish();

                break;



            case (R.id.menuItem3): //Goto Profile


                i = PerfilActivity.newIntent(MainActivity.this,
                                            MainActivity.this.getClass().getSimpleName(),
                                            mUsername, mPassword, mEmail);
                startActivityForResult(i,REQUEST_CODE_PROFILE_ACTIVITY);
                //startActivity(i);
                //finish();

                break;

            case (R.id.menuItem4): //Goto Close

                i = LoginActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail);
                setResult(RESULT_OK,i);
                //startActivity(i);
                finish();

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

                //si quito esto no manda los datos
                mUsername = data.getExtras().getString(EXTRA_USERNAME);
                mPassword = data.getExtras().getString(EXTRA_PASSWORD);
                mEmail = data.getExtras().getString(EXTRA_EMAIL);

                i = LoginActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail);
                setResult(RESULT_OK,i);
                finish();
            }
            else{
                //Toast.makeText(this, R.string.register_error, Toast.LENGTH_SHORT).show();
            }
        }


        else if(requestCode == REQUEST_CODE_ESTABLISHMENT_ACTIVITY)
        {
            if(resultCode == RESULT_OK) {
                //from menu item Main
                mUsername = data.getExtras().getString(EXTRA_USERNAME);
                mPassword = data.getExtras().getString(EXTRA_PASSWORD);
                mEmail = data.getExtras().getString(EXTRA_EMAIL);
                /*
                        Toast.makeText(this, mUsername+" " + mPassword + " " + mEmail
                        ,Toast.LENGTH_SHORT).show();
                */
            }
            else if (resultCode == RESULT_FIRST_USER){
                //user pressed log out
                i = LoginActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail);
                setResult(RESULT_OK,i);
                finish();
            }
            else{
              //program control reaches here when user press back button
            }
        }

        else if(requestCode == REQUEST_CODE_EVENTS_ACTIVITY)
        {
            if(resultCode == RESULT_OK) {
                //from menu item Main
                mUsername = data.getExtras().getString(EXTRA_USERNAME);
                mPassword = data.getExtras().getString(EXTRA_PASSWORD);
                mEmail = data.getExtras().getString(EXTRA_EMAIL);
                /*
                        Toast.makeText(this, mUsername+" " + mPassword + " " + mEmail
                        ,Toast.LENGTH_SHORT).show();
                */
            }
            else if (resultCode == RESULT_FIRST_USER){
                //user pressed log out
                i = LoginActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail);
                setResult(RESULT_OK,i);
                finish();
            }
            else{
                //program control reaches here when user press back button
            }
        }


    }
}
