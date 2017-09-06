package com.example.cristian.yarumalturistica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

public class EstablishmentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener       {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    /**
     *  Keys to identify data from other activities
     *
     */

    private static final String EXTRA_USERNAME = "com.example.cristian.yarumalturistica.username";
    private static final String EXTRA_PASSWORD = "com.example.cristian.yarumalturistica.password";
    private static final String EXTRA_EMAIL = "com.example.cristian.yarumalturistica.email";
    private static final String EXTRA_GOTO_ESTABLISHMENT = "com.example.cristian.yarumalturistica.gotoEstablishment";

    private static final int REQUEST_CODE_PROFILE_ACTIVITY = 1;
    private static final int REQUEST_CODE_ESTABLISHMENT_ACTIVITY = 2;
    private static final int REQUEST_CODE_EVENTS_ACTIVITY = 3;

    private final int RESTAURANTS = 0;
    private final int HOTELS = 1;
    private final int TOURISTIC_SITES = 2;

    private String mUsername, mPassword, mEmail;
    private int mGoToEstablishment;

    private String MENU_ITEM0_TITTLE, MENU_ITEM1_TITTLE, MENU_ITEM2_TITTLE, MENU_ITEM3_TITTLE, MENU_ITEM_MAIN_TITTLE,
            MENU_ITEM4_TITTLE, MENU_ITEM5_TITTLE;

    private Intent i;
    //Establishment data
    private int mIdCurrentEstablishmentImage1, mIdCurrentEstablishmentImage2, mIdCurrentEstablishmentImage3;
    private int mIdCurrentEstablishmentText1, mIdCurrentEstablishmentText2, mIdCurrentEstablishmentText3;

    private String mIdCurrentEstablishmentPageTitle1, mIdCurrentEstablishmentPageTitle2, mIdCurrentEstablishmentPageTitle3;
    private int mIdCurrentPageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        mUsername = extras.getString(EXTRA_USERNAME);
        mPassword = extras.getString(EXTRA_PASSWORD);
        mEmail = extras.getString(EXTRA_EMAIL);
        mGoToEstablishment = extras.getInt(EXTRA_GOTO_ESTABLISHMENT);

        //set Establishment's ID
        setEstablishmentId();

        //init menu Strings
        MENU_ITEM0_TITTLE = getResources().getString(R.string.restaurants);
        MENU_ITEM1_TITTLE = getResources().getString(R.string.hotels);
        MENU_ITEM2_TITTLE = getResources().getString(R.string.touristic_sites);
        MENU_ITEM3_TITTLE = getResources().getString(R.string.events);
        MENU_ITEM4_TITTLE = getResources().getString(R.string.main);
        MENU_ITEM5_TITTLE = getResources().getString(R.string.close_session);


        //set Layout

        int color_int = ContextCompat.getColor(getApplicationContext(), R.color.lightgreen);
        getWindow().getDecorView().setBackgroundColor(color_int);

        setContentView(R.layout.activity_establishment);

        //ActionBar bar = getSupportActionBar();
        //bar.setBackgroundDrawable();
        //bar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_establishment);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //set Toolbar and tabLayout color

        toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                R.color.darkgreen));
        tabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                R.color.darkgreen));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_establishment);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_establishment);
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


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menuMainDrawerItem0) { //RESTAURANTS

            mGoToEstablishment = RESTAURANTS;
            i = EstablishmentActivity.newIntent(EstablishmentActivity.this, mUsername, mPassword, mEmail,
                    mGoToEstablishment);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();

        } else if (id == R.id.menuMainDrawerItem1) { //HOTELS


            mGoToEstablishment = HOTELS;
            i = EstablishmentActivity.newIntent(EstablishmentActivity.this, mUsername, mPassword, mEmail,
                    mGoToEstablishment);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();


        } else if (id == R.id.menuMainDrawerItem2) { //TOURISTIC

            mGoToEstablishment = TOURISTIC_SITES;
            i = EstablishmentActivity.newIntent(EstablishmentActivity.this, mUsername, mPassword, mEmail,
                    mGoToEstablishment);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();

        }else if (id == R.id.menuMainDrawerItem3) { //EVENTS

            i = EventsActivity.newIntent(EstablishmentActivity.this, mUsername, mPassword, mEmail);
            startActivityForResult(i,REQUEST_CODE_EVENTS_ACTIVITY);


        } else if (id == R.id.menuMainDrawerItem4) { //MAIN


            //mGoToEstablishment = TOURISTIC_SITES;
                /*
                i = EstablishmentActivity.newIntent(EstablishmentActivity.this, mUsername, mPassword, mEmail,
                        mGoToEstablishment);
                */
            i = MainActivity.newIntent(EstablishmentActivity.this, mUsername, mPassword, mEmail);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();



        } else if (id == R.id.menuMainDrawerItem5) { //close


            i = MainActivity.newIntent(EstablishmentActivity.this, mUsername, mPassword, mEmail);
            setResult(RESULT_FIRST_USER,i);
            //startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_establishment);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_establishment, menu);
        getMenuInflater().inflate(R.menu.menu,menu);

        setMenuOptions(menu);

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){


            case (R.id.menuItem0): //Goto Establishment tab 1

                if(mGoToEstablishment == RESTAURANTS){
                    mGoToEstablishment = HOTELS;
                }
                else {
                    mGoToEstablishment = RESTAURANTS;
                }

                //mGoToEstablishment = RESTAURANTS;
                i = EstablishmentActivity.newIntent(EstablishmentActivity.this, mUsername, mPassword, mEmail,
                        mGoToEstablishment);
                //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
                startActivity(i);
                finish();

                break;

            case (R.id.menuItem1): //Goto Establishment tab 2


                if(mGoToEstablishment == RESTAURANTS || mGoToEstablishment == HOTELS){
                    mGoToEstablishment = TOURISTIC_SITES;
                }
                else {
                    mGoToEstablishment = HOTELS;
                }

                i = EstablishmentActivity.newIntent(EstablishmentActivity.this, mUsername, mPassword, mEmail,
                        mGoToEstablishment);
                //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
                startActivity(i);
                finish();

                break;


            //Goto Main
            case (R.id.menuItem2): //Goto Main

                //mGoToEstablishment = TOURISTIC_SITES;
                /*
                i = EstablishmentActivity.newIntent(EstablishmentActivity.this, mUsername, mPassword, mEmail,
                        mGoToEstablishment);
                */
                i = MainActivity.newIntent(EstablishmentActivity.this, mUsername, mPassword, mEmail);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
                startActivity(i);
                finish();

                break;



            case (R.id.menuItem3): //Goto Profile

                i = PerfilActivity.newIntent(EstablishmentActivity.this,
                                             EstablishmentActivity.this.getClass().getSimpleName(),
                                             mUsername, mPassword, mEmail);
                //startActivityForResult(i,REQUEST_CODE_PROFILE_ACTIVITY);
                startActivity(i);
                //finish();

                break;

            case (R.id.menuItem4): //Goto Close

                i = MainActivity.newIntent(EstablishmentActivity.this, mUsername, mPassword, mEmail);
                setResult(RESULT_FIRST_USER,i);
                //startActivity(i);
                finish();

                break;
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_establishment, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position){
                case 0:
                    //EstablishmentsFragment tab1Fragment = new EstablishmentsFragment();
                    return EstablishmentFragment.newInstance(mIdCurrentEstablishmentImage1,
                                                            mIdCurrentEstablishmentText1);

                case 1:
                    return EstablishmentFragment.newInstance(mIdCurrentEstablishmentImage2,
                                                             mIdCurrentEstablishmentText2);

                case 2:
                    return EstablishmentFragment.newInstance(mIdCurrentEstablishmentImage3,
                                                            mIdCurrentEstablishmentText3);

                default:
                    return PlaceholderFragment.newInstance(position+1);
                    //return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.section_page_format, getResources().getString(mIdCurrentPageTitle)
                                                                  ,position+1);
                    //return "SECTION 1";
                case 1:
                    return getString(R.string.section_page_format, getResources().getString(mIdCurrentPageTitle)
                            ,position+1);
                //return "SECTION 2";
                case 2:
                    return getString(R.string.section_page_format, getResources().getString(mIdCurrentPageTitle)
                            ,position+1);
//                return "SECTION 3";
            }
            return null;
        }
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
                Toast.makeText(this, "RESULT_OK FROM PROFILE", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_FIRST_USER){

                //i = LoginActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail);
                //setResult(RESULT_OK,i);
                //finish();
                Toast.makeText(this, "RESULT_FIRST_USER FROM PROFILE", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "RESULT_CANCEL FROM PROFILE", Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, R.string.register_error, Toast.LENGTH_SHORT).show();
            }
        }

        //NOT USED
        else if(requestCode == REQUEST_CODE_ESTABLISHMENT_ACTIVITY)
        {
            if(resultCode == RESULT_OK) {
                mUsername = data.getExtras().getString(EXTRA_USERNAME);
                mPassword = data.getExtras().getString(EXTRA_PASSWORD);
                mEmail = data.getExtras().getString(EXTRA_EMAIL);

                Toast.makeText(this, "RESULT_OK FROM establishment", Toast.LENGTH_SHORT).show();
                /*
                Toast.makeText(this, mUsername+" " + mPassword + " " + mEmail
                        ,Toast.LENGTH_SHORT).show();

                */
            }
            else if (resultCode == RESULT_FIRST_USER){
                Toast.makeText(this, "RESULT_FIRST_USER FROM establishment", Toast.LENGTH_SHORT).show();
                //i = LoginActivity.newIntent(MainActivity.this, mUsername, mPassword, mEmail);
                //setResult(RESULT_OK,i);
                //finish();
            }
            else{
                Toast.makeText(this, "RESULT_CANCEL FROM establishment", Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, R.string.register_error, Toast.LENGTH_SHORT).show();
            }
        }


    }

    public static Intent newIntent(Context packageContext, String username, String password,
                                   String email, int goToEstablishment
    ){

        Intent i = new Intent(packageContext, EstablishmentActivity.class);
        i.putExtra(EXTRA_USERNAME,username);
        i.putExtra(EXTRA_PASSWORD,password);
        i.putExtra(EXTRA_EMAIL,email);
        i.putExtra(EXTRA_GOTO_ESTABLISHMENT,goToEstablishment);
        return i;

    }

    //define establishment's ID
    private void setEstablishmentId() {

        switch (mGoToEstablishment){

            case RESTAURANTS:
                //get images resources
                mIdCurrentEstablishmentImage1 = R.drawable.restaurante_mediterraneo;
                mIdCurrentEstablishmentImage2 = R.drawable.restaurante_parador_regional;
                mIdCurrentEstablishmentImage3 = R.drawable.restaurante_ranchorico;
                //get texts resources
                mIdCurrentEstablishmentText1 = R.string.restaurant1;
                mIdCurrentEstablishmentText2 = R.string.restaurant2;
                mIdCurrentEstablishmentText3 = R.string.restaurant3;
                //set pager's Page title
                mIdCurrentPageTitle = R.string.restaurant;

                break;

            case HOTELS:
                //get images resources
                mIdCurrentEstablishmentImage1 = R.drawable.hotel_amajari;
                mIdCurrentEstablishmentImage2 = R.drawable.hotel_balconesdelparaiso;
                mIdCurrentEstablishmentImage3 = R.drawable.hotel_gran_yarumal;
                //get texts resources
                mIdCurrentEstablishmentText1 = R.string.hotel1;
                mIdCurrentEstablishmentText2 = R.string.hotel2;
                mIdCurrentEstablishmentText3 = R.string.hotel3;
                //set pager's Page title
                mIdCurrentPageTitle = R.string.hotel;
                break;


            case TOURISTIC_SITES:
                //get images resources
                mIdCurrentEstablishmentImage1 = R.drawable.turistico_basilica_nuestra_senora;
                mIdCurrentEstablishmentImage2 = R.drawable.turistico_escuela_rosenda_torres;
                mIdCurrentEstablishmentImage3 = R.drawable.turistico_museo_nepomuceno;
                //get text resource
                mIdCurrentEstablishmentText1 = R.string.touristic1;
                mIdCurrentEstablishmentText2 = R.string.touristic2;
                mIdCurrentEstablishmentText3 = R.string.touristic3;
                //set pager's Page title
                mIdCurrentPageTitle = R.string.touristic_site;
                break;

            default:
                break;

        }

    }

    private void setMenuOptions(Menu menu) {

        if(mGoToEstablishment == RESTAURANTS){

            menu.getItem(0).setTitle(MENU_ITEM1_TITTLE);
            menu.getItem(1).setTitle(MENU_ITEM2_TITTLE);
            //menu.getItem(0).setVisible(false);
            //menu.getItem(0).setTitle(MENU_ITEM0_TITTLE);
            //menu.getItem(1).setTitle(MENU_ITEM1_TITTLE);
            //menu.getItem(2).setTitle(MENU_ITEM2_TITTLE);
            menu.getItem(2).setTitle(MENU_ITEM_MAIN_TITTLE);
            menu.getItem(3).setTitle(MENU_ITEM3_TITTLE);
            menu.getItem(4).setTitle(MENU_ITEM4_TITTLE);
        }
        else if (mGoToEstablishment == HOTELS){

            menu.getItem(0).setTitle(MENU_ITEM0_TITTLE);
            menu.getItem(1).setTitle(MENU_ITEM2_TITTLE);
            //menu.getItem(0).setTitle(MENU_ITEM0_TITTLE);
            //menu.getItem(1).setVisible(false);
            //menu.getItem(1).setTitle(MENU_ITEM1_TITTLE);
            //menu.getItem(2).setTitle(MENU_ITEM2_TITTLE);
            menu.getItem(2).setTitle(MENU_ITEM_MAIN_TITTLE);
            menu.getItem(3).setTitle(MENU_ITEM3_TITTLE);
            menu.getItem(4).setTitle(MENU_ITEM4_TITTLE);
        }
        else if (mGoToEstablishment == TOURISTIC_SITES){

            menu.getItem(0).setTitle(MENU_ITEM0_TITTLE);
            menu.getItem(1).setTitle(MENU_ITEM1_TITTLE);
            //menu.getItem(0).setTitle(MENU_ITEM0_TITTLE);
            //menu.getItem(1).setTitle(MENU_ITEM1_TITTLE);
            //menu.getItem(2).setVisible(false);
            //menu.getItem(2).setTitle(MENU_ITEM2_TITTLE);
            menu.getItem(2).setTitle(MENU_ITEM_MAIN_TITTLE);
            menu.getItem(3).setTitle(MENU_ITEM3_TITTLE);
            menu.getItem(4).setTitle(MENU_ITEM4_TITTLE);
        }

    }

}
