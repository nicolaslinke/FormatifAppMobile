package com.example.formatifappmobile_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.formatifappmobile_1.databinding.ActivityMainBinding;
import com.example.formatifappmobile_1.http.RetrofitUtil;
import com.example.formatifappmobile_1.http.Service;
import com.example.formatifappmobile_1.objects.number;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle abToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });

        //ActionBar

        NavigationView nv = binding.navView;
        DrawerLayout dl = binding.drawerLayout;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        abToggle = new ActionBarDrawerToggle(this,dl, R.string.drawer_open, R.string.drawer_close);
        dl.addDrawerListener(abToggle);
        abToggle.syncState();
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_pageA)
                {
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                }
                if (item.getItemId() == R.id.nav_pageB)
                {
                    Intent i = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(i);
                }
                dl.closeDrawers();
                return false;
            }
        });

        //Service
        Service service = RetrofitUtil.get();
        service.getNumber("1212").enqueue(new Callback<List<number>>() {
            @Override
            public void onResponse(Call<List<number>> call, Response<List<number>> response) {
                if (response.isSuccessful()) {
                    //http 2-- http tout s'est bien passé
                    List<number> resultat = response.body();
                    Log.i("RETROFIT", resultat.get(0).nombre + "");
                } else {
                    //error http 400 404
                    Log.i("RETROFIT", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<List<number>> call, Throwable t) {

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (abToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        abToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        abToggle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }
}