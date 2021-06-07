package com.example.safood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.safood.Common.Common;
import com.example.safood.Fargments.HomeContentFragment;
import com.example.safood.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeSafood<onNavigationItemSelected> extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,HomeContentFragment.FragmentHomeContentListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView mImageView;
    private TextView mTextView_Phone;
    private FirebaseDatabase database;
    private DatabaseReference users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_safood);

        //initialize for menu
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //default fragment as we begin is the home contentp
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeContentFragment()).commit();
            navigationView.setCheckedItem(R.id.Home);
        }

        updateHeader(Common.currentUser);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.Home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeContentFragment()).commit();
                break;
            case R.id.EditProfile:
             /*   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EditProfileFragment()).commit();*/
                break;
            case R.id.Definitions:
              /*  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DefinitionsFragment()).commit();*/
                break;
            case R.id.NotificationHistory:
             /*   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GameHistoryFragment()).commit();*/
                break;
            case R.id.nav_company_name:
                Toast.makeText(this,getResources().getString(R.string.app_name),Toast.LENGTH_SHORT).show();
                break;
            case R.id.LogOut:
                startActivity(new Intent(HomeSafood.this,MainActivity.class));
                finish();
                break;
            case R.id.contact:
                Toast.makeText(this,getResources().getString(R.string.contactUs),Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void updateHeader(User currentUser) {

        View header = navigationView.getHeaderView(0);
        mTextView_Phone= header.findViewById(R.id.user_phone);
        if(currentUser.getPhone() != null) {
            mTextView_Phone.setText(currentUser.getPhone());
        }

    }


    @Override
    public void moveToFoodNews() {
        //need intent
    }

    @Override
    public void moveToAllNotifications() {
    //need intent
    }
}