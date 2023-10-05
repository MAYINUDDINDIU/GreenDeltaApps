package com.mayin.greendelta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class Dashboard extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    LinearLayout layNonet;
    SharedPreferences prf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        layNonet = findViewById(R.id.layNonet);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);


        tabLayout.addTab(tabLayout.newTab().setText("Add Employee"));
        tabLayout.addTab(tabLayout.newTab().setText("Employee Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Skills"));



        final UI.TabAdapter adapter = new UI.TabAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void delete(){

        SharedPreferences.Editor editor = prf.edit();
        editor.clear();
        editor.commit();
        Intent intent=new Intent(Dashboard.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        delete();
         return super.onOptionsItemSelected(item);
        }

}