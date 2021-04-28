package com.efashtech.jobforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

public class Dashboard extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    ImageView profilePhoto;

    private static final String TAG = "MainActivity";
    GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;
    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Shared preference
        SharedPreferences preferences = getSharedPreferences("PREFRENCE", MODE_PRIVATE);
        boolean FirstTime = preferences.getBoolean("FirstTimeInstall",true);

        //Check if application was opened for first time
        if (FirstTime){
            firstStartCreateResume();
        }

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {

            startLoginActivity();
        }

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getUid();

        profilePhoto = findViewById(R.id.employee_profil_picture);
        nav=(NavigationView)findViewById(R.id.navmenu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);

        navMenu();
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    private void firstStartCreateResume(){
        Intent intent = new Intent(Dashboard.this,createResume.class);
        startActivity(intent);
        SharedPreferences preferences = getSharedPreferences("PREFRENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("FirstTimeInstall",false);
        editor.apply();
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, loginRegisterEmployeeActivity.class));
        this.finish();
    }

    private void navMenu() {

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new employee_job_fragment()).commit();
//        nav.setCheckedItem(R.id.menu_profile);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            Fragment temp;
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_dashboard :

                        break;

                    case R.id.menu_profile :
                        temp = new employee_profile_fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();
                        break;

                    case R.id.menu_about :
                        temp = new employee_about_fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();
                        break;

                    case R.id.menu_signout :
                        AuthUI.getInstance().signOut(Dashboard.this);
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