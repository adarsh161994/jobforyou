package com.efashtech.jobforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

public class Dashboard_employer extends AppCompatActivity implements FirebaseAuth.AuthStateListener{

    NavigationView nav_emplr;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    ExtendedFloatingActionButton fabExtBtn;

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer);
        mAuth = FirebaseAuth.getInstance();

        current_user_id = mAuth.getUid();

        //Shared preference
        SharedPreferences preferences = getSharedPreferences("PREFRENCE", MODE_PRIVATE);
        boolean FirstTime = preferences.getBoolean("FirstTimeInstall",true);

        //Check if application was opened for first time
        if (FirstTime){
            firstStartemployerDetails();
        }

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {

            startLoginActivity();
        }


        nav_emplr=(NavigationView)findViewById(R.id.navmenu_emplr);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_emplr);
        fabExtBtn = findViewById(R.id.fabExt);
        navMenuEmployer();
        fabExtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_employer.this,postJobByEmployer.class);
                startActivity(intent);
            }
        });

    }

    private void firstStartemployerDetails(){
        Intent intent = new Intent(Dashboard_employer.this,employer_details.class);
        startActivity(intent);
        SharedPreferences preferences = getSharedPreferences("PREFRENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("FirstTimeInstall",false);
        editor.apply();
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, loginRegisterEmployerActivity.class));
        this.finish();
    }

    private void navMenuEmployer() {

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_emplr);
        setSupportActionBar(toolbar);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav_emplr.setCheckedItem(R.id.menu_home);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_employer,new employer_home_fragment()).commit();

        nav_emplr.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            Fragment temp;
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {

                switch (menuItem.getItemId())
                {
                    case R.id.menu_home :
                        temp = new employee_list();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_employer,temp).commit();
                        break;

                    case R.id.menu_post_job :
                        temp = new post_job_fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_employer,temp).commit();
                        break;

                    case R.id.menu_employer_profile :
                        temp = new employer_profile_fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_employer,temp).commit();
                        break;

                    case R.id.menu_employer_about :
                        temp = new employer_about_fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_employer,temp).commit();
                        break;

                    case R.id.menu_employer_signout :
                        AuthUI.getInstance().signOut(Dashboard_employer.this);

                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            startLoginActivity();
            return;
        }
        firebaseAuth.getCurrentUser().getIdToken(true)
                .addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                    @Override
                    public void onSuccess(GetTokenResult getTokenResult) {
                        Log.d(TAG,"onSuccess: "+ getTokenResult.getToken());
                    }
                });
    }
}