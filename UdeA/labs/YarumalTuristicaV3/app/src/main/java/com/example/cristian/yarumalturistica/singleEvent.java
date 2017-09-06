package com.example.cristian.yarumalturistica;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class singleEvent extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String EXTRA_USERNAME = "com.example.cristian.yarumalturistica.username";
    private static final String EXTRA_PASSWORD = "com.example.cristian.yarumalturistica.password";
    private static final String EXTRA_EMAIL = "com.example.cristian.yarumalturistica.email";
    private static final String EXTRA_TITLE = "com.example.cristian.yarumalturistica.title";
    private static final String EXTRA_SUBTITLE = "com.example.cristian.yarumalturistica.subtitle";

    private final int RESTAURANTS = 0;
    private final int HOTELS = 1;
    private final int TOURISTIC_SITES = 2;

    private String MENU_ITEM0_TITTLE, MENU_ITEM1_TITTLE, MENU_ITEM2_TITTLE, MENU_ITEM3_TITTLE,
            MENU_ITEM4_TITTLE, MENU_ITEM5_TITTLE;

    String mTitle, mSubTitle, mUsername, mPassword, mEmail;
    TextView tVtitle, tVsubtitle;

    private int mGoToEstablishment;

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            mUsername = extras.getString(EXTRA_USERNAME);
            mPassword = extras.getString(EXTRA_PASSWORD);
            mEmail = extras.getString(EXTRA_EMAIL);
            mTitle = extras.getString(EXTRA_TITLE);
            mSubTitle = extras.getString(EXTRA_SUBTITLE);
        }

        tVtitle = (TextView) findViewById(R.id.textViewTitleSingleEvent);
        tVsubtitle = (TextView) findViewById(R.id.textViewSubTitleSingleEvent);

        tVtitle.setText(mTitle);
        tVsubtitle.setText(mSubTitle);

        //init menu Strings
        MENU_ITEM0_TITTLE = getResources().getString(R.string.restaurants);
        MENU_ITEM1_TITTLE = getResources().getString(R.string.hotels);
        MENU_ITEM2_TITTLE = getResources().getString(R.string.touristic_sites);
        MENU_ITEM3_TITTLE = getResources().getString(R.string.events);
        MENU_ITEM4_TITTLE = getResources().getString(R.string.main);
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
        //drawerMenu.findItem(R.id.menuMainDrawerItem4).setVisible(false);



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

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menuMainDrawerItem0) {
            mGoToEstablishment = RESTAURANTS;
            i = EstablishmentActivity.newIntent(singleEvent.this, mUsername, mPassword, mEmail,
                    mGoToEstablishment);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();
        } else if (id == R.id.menuMainDrawerItem1) {
            mGoToEstablishment = HOTELS;
            i = EstablishmentActivity.newIntent(singleEvent.this, mUsername, mPassword, mEmail,
                    mGoToEstablishment);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();

        } else if (id == R.id.menuMainDrawerItem2) {
            mGoToEstablishment = TOURISTIC_SITES;
            i = EstablishmentActivity.newIntent(singleEvent.this, mUsername, mPassword, mEmail,
                    mGoToEstablishment);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();

        } else if (id == R.id.menuMainDrawerItem3) {

            i = EventsActivity.newIntent(singleEvent.this, mUsername, mPassword, mEmail);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();


        } else if (id == R.id.menuMainDrawerItem4) {
            i = MainActivity.newIntent(singleEvent.this, mUsername, mPassword, mEmail);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();

        } else if (id == R.id.menuMainDrawerItem5) {
            i = LoginActivity.newIntent(singleEvent.this, mUsername, mPassword, mEmail);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Intent newIntent (Context packageContext, String username, String password,
                                    String email, String title, String subtitle){
        Intent i = new Intent(packageContext,singleEvent.class);
        i.putExtra(EXTRA_USERNAME,username);
        i.putExtra(EXTRA_PASSWORD,password);
        i.putExtra(EXTRA_EMAIL,email);
        i.putExtra(EXTRA_TITLE,title);
        i.putExtra(EXTRA_SUBTITLE,subtitle);
        return i;
    }
}
