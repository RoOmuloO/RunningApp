package com.example.romuloroger.runningapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.romuloroger.runningapp.fragment.CadastraAtletaUsuarioFragment;
import com.example.romuloroger.runningapp.fragment.CadastraCorridaFragment;
import com.example.romuloroger.runningapp.fragment.DetalhesCorridaFragment;
import com.example.romuloroger.runningapp.fragment.ListaCorridas;
import com.example.romuloroger.runningapp.fragment.ListaInscricoesFragment;
import com.example.romuloroger.runningapp.models.response.LoginResponse;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ListaCorridas.OnFragmentInteractionListener,
        CadastraAtletaUsuarioFragment.OnFragmentInteractionListener,
        CadastraCorridaFragment.OnFragmentInteractionListener,
        ListaInscricoesFragment.OnFragmentInteractionListener,
        DetalhesCorridaFragment.OnFragmentInteractionListener{

    public static final int REQUEST_LOGIN = 1;

    public LoginResponse login = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Fragment fInject = null;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_minhas_inscricoes) {
            fInject = ListaInscricoesFragment.newInstance("", "");
        }

        if (fInject != null) {
            showFragment(fInject);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fInject = null;

        switch (id) {
            case R.id.nav_login:
                Intent itn = new Intent(getApplicationContext(), LoginActivity.class);
                startActivityForResult(itn, REQUEST_LOGIN);
                break;
            case R.id.nav_listaCorridas:

                if(login != null){
                    fInject = ListaCorridas.newInstance(login.getToken(), "456");
                }else
                    fInject = ListaCorridas.newInstance(null, "456");
                break;
            case R.id.nav_sobre:
                break;
            case R.id.nav_sairSistema:
                break;
            case R.id.nav_cadastrarAtleta:
                fInject = CadastraAtletaUsuarioFragment.newInstance("Criando Atleta", "Criando Usuario");
                break;
            case R.id.nav_cadastrarCorrida:
                fInject = CadastraCorridaFragment.newInstance("", "");
                break;
            default:
                break;
        }

        if (fInject != null) {
            showFragment(fInject);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN && resultCode == 10) {
            login = (LoginResponse) data.getExtras().getSerializable("user");
            changeVisibilityMenu(R.id.nav_login, false);
            changeVisibilityMenu(R.id.nav_sairSistema, true);
        }

    }

    private void changeVisibilityMenu(int idMenu, boolean visivility) {
        MenuItem item;
        item = ((NavigationView) findViewById(R.id.nav_view)).getMenu().findItem(idMenu);
        item.setVisible(visivility);
    }

    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
