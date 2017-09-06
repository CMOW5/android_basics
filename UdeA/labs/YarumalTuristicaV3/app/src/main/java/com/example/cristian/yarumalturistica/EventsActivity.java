package com.example.cristian.yarumalturistica;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String EXTRA_USERNAME = "com.example.cristian.yarumalturistica.username";
    private static final String EXTRA_PASSWORD = "com.example.cristian.yarumalturistica.password";
    private static final String EXTRA_EMAIL = "com.example.cristian.yarumalturistica.email";

    private static final int REQUEST_CODE_PROFILE_ACTIVITY = 1;
    private static final int REQUEST_CODE_ESTABLISHMENT_ACTIVITY = 2;

    private final int RESTAURANTS = 0;
    private final int HOTELS = 1;
    private final int TOURISTIC_SITES = 2;

    private String MENU_ITEM0_TITTLE, MENU_ITEM1_TITTLE, MENU_ITEM2_TITTLE, MENU_ITEM3_TITTLE,
            MENU_ITEM4_TITTLE, MENU_ITEM5_TITTLE;

    private String mUsername, mPassword, mEmail;
    private int mGoToEstablishment;
    //private GoToEstablishment mGoToEstablishment;
    ListView  lstOpciones;

    private Titular[] datos =
            new Titular[]{
                    new Titular("TORNEO DE MENORES INDERYAL", "fecha 03-06-2017"),
                    new Titular("FIESTA DEL YARUMO", "fecha 04-05-2017"),
                    new Titular("FESTIVAL DE POESÍA", "fecha 05-05-2017"),
                    new Titular("CAMINATA", "fecha 27-08-2017"),
                    };

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int color_int = ContextCompat.getColor(getApplicationContext(), R.color.lightblue);
        getWindow().getDecorView().setBackgroundColor(color_int);


        setContentView(R.layout.activity_events);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            mUsername = extras.getString(EXTRA_USERNAME);
            mPassword = extras.getString(EXTRA_PASSWORD);
            mEmail = extras.getString(EXTRA_EMAIL);
        }

        //set NavigationDrawer

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_events);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_events);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_events);
        navigationView.setNavigationItemSelectedListener(this);

        Menu drawerMenu = navigationView.getMenu();
        drawerMenu.findItem(R.id.menuMainDrawerItem4).setVisible(false);

        AdaptadorTitulares adaptador =
                new AdaptadorTitulares(this, datos);

        lstOpciones = (ListView)findViewById(R.id.LstOpciones);

        lstOpciones.setAdapter(adaptador);

        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                //Alternativa 1:
                String title =
                        ((Titular)a.getItemAtPosition(position)).getTitulo();

                String subtitle =
                        ((Titular)a.getItemAtPosition(position)).getSubtitulo();

                Intent i = singleEvent.newIntent(EventsActivity.this, mUsername,mPassword,mEmail,title,subtitle);
                startActivity(i);
                //Toast.makeText(getApplicationContext(), opcionSeleccionada,Toast.LENGTH_SHORT).show();
                //Alternativa 2:
                // opcionSeleccionada =
                //((TextView)v.findViewById(R.id.LblTitulo))
                // .getText().toString();

                // lblEtiqueta.setText("Opción seleccionada: " + opcionSeleccionada);
            }
        });


        //init menu Strings
        MENU_ITEM0_TITTLE = getResources().getString(R.string.restaurants);
        MENU_ITEM1_TITTLE = getResources().getString(R.string.hotels);
        MENU_ITEM2_TITTLE = getResources().getString(R.string.touristic_sites);
        MENU_ITEM3_TITTLE = getResources().getString(R.string.main);
        MENU_ITEM4_TITTLE = getResources().getString(R.string.myProfile);
        MENU_ITEM5_TITTLE = getResources().getString(R.string.close_session);

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

    public class AdaptadorTitulares extends ArrayAdapter<Titular> {

        public AdaptadorTitulares(Context context, Titular[] datos) {
            super(context, R.layout.list_layout, datos);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.list_layout, null);

            TextView lblTitulo = (TextView)item.findViewById(R.id.LblTitulo);
            lblTitulo.setText(datos[position].getTitulo());

            TextView lblSubtitulo = (TextView)item.findViewById(R.id.LblSubTitulo);
            lblSubtitulo.setText(datos[position].getSubtitulo());

            return(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menuMainDrawerItem0) {
            mGoToEstablishment = RESTAURANTS;
            i = EstablishmentActivity.newIntent(EventsActivity.this, mUsername, mPassword, mEmail,
                    mGoToEstablishment);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();
        } else if (id == R.id.menuMainDrawerItem1) {
            mGoToEstablishment = HOTELS;
            i = EstablishmentActivity.newIntent(EventsActivity.this, mUsername, mPassword, mEmail,
                    mGoToEstablishment);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();

        } else if (id == R.id.menuMainDrawerItem2) {
            mGoToEstablishment = TOURISTIC_SITES;
            i = EstablishmentActivity.newIntent(EventsActivity.this, mUsername, mPassword, mEmail,
                    mGoToEstablishment);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();

        } else if (id == R.id.menuMainDrawerItem3) {

            i = MainActivity.newIntent(EventsActivity.this, mUsername, mPassword, mEmail);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();

        } else if (id == R.id.menuMainDrawerItem4) {
            i = PerfilActivity.newIntent(EventsActivity.this,
                    EventsActivity.this.getClass().getSimpleName(),
                    mUsername, mPassword, mEmail);
            startActivityForResult(i,REQUEST_CODE_PROFILE_ACTIVITY);

        } else if (id == R.id.menuMainDrawerItem5) {
            i = LoginActivity.newIntent(EventsActivity.this, mUsername, mPassword, mEmail);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivityForResult(i,REQUEST_CODE_ESTABLISHMENT_ACTIVITY);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_events);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Intent newIntent(Context packageContext, String username, String password, String email){

        Intent i = new Intent(packageContext, EventsActivity.class);
        i.putExtra(EXTRA_USERNAME,username);
        i.putExtra(EXTRA_EMAIL,email);
        i.putExtra(EXTRA_PASSWORD,password);
        return i;
    }


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

                i = LoginActivity.newIntent(EventsActivity.this, mUsername, mPassword, mEmail);
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
                i = LoginActivity.newIntent(EventsActivity.this, mUsername, mPassword, mEmail);
                setResult(RESULT_OK,i);
                finish();
            }
            else{
                //program control reaches here when user press back button
            }
        }


    }


}
