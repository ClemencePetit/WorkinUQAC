package com.example.workinuqac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import java.io.Console;

public class MainActivity extends AppCompatActivity {
    enum FRAGMENT {
        USER_PROFILE,   // 0
        LOGIN,          // 1
        INSCRIPTION,    // 2
        PROFILE_EDIT,   // 3
        SEARCH,         // 4
        STUDENT_PROFILE // 5
    }

    private static final int PERMISSION_CODE = 1000;
    private int idUser = -1; // ID de l'utilisateur dans la base de données - -1 = pas connecté
    private FRAGMENT currentFragment = FRAGMENT.LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_main_portrait);
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_main_land);

        loadPreferences();

        // Define previous fragment(s)
        FRAGMENT fragmentToLoad = currentFragment;
        switch (currentFragment) {
            case INSCRIPTION:
                changeFragment(FRAGMENT.LOGIN);
                break;
            case PROFILE_EDIT:
            case SEARCH:
                changeFragment(FRAGMENT.USER_PROFILE);
                break;
            case STUDENT_PROFILE:
                changeFragment(FRAGMENT.SEARCH);
        }
        changeFragment(fragmentToLoad);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("idUser", idUser);
        savedInstanceState.putString("fragment", currentFragment.toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        /* check Permission READ and WRITE on external storage at runtime */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                //Permission already granted
            }
        } else {
            //Old OS
        }

    }

    public void savePreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idUser", idUser);
        editor.putString("fragment", currentFragment.name());
        editor.commit();
    }

    public void loadPreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", -1);
        currentFragment = FRAGMENT.valueOf(sharedPreferences.getString("fragment", FRAGMENT.LOGIN.name()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentFragment = FRAGMENT.valueOf(getSupportFragmentManager().findFragmentById(R.id.placeholder).getTag());
        savePreferences();
    }


    public void changeFragment(FRAGMENT fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // Argument : vers quel fragment aller par la suite
        switch (fragment) {
            case USER_PROFILE:
                ft
                        //.addToBackStack(null)
                        .replace(R.id.placeholder, ConnectedFragment.newInstance(), fragment.name())
                        .commit();
                idUser = 1;

                break;
            case LOGIN:
                ft
                        //.addToBackStack(null)
                        .replace(R.id.placeholder, ConnectionFragment.newInstance(), fragment.name())
                        .commit();
                break;
            case INSCRIPTION:
                ft
                        .addToBackStack(null)
                        .replace(R.id.placeholder, InscriptionFragment.newInstance(), fragment.name())
                        .commit();
                break;
            case PROFILE_EDIT:
                ft
                        .addToBackStack(null)
                        .replace(R.id.placeholder, EditFragment.newInstance(), fragment.name())
                        .commit();
                break;
            case SEARCH:
                ft
                        .addToBackStack(null)
                        .replace(R.id.placeholder, SearchFragment.newInstance(), fragment.name())
                        .commit();
                break;
            case STUDENT_PROFILE:
                ft
                        .addToBackStack(null)
                        .replace(R.id.placeholder, ProfileFragment.newInstance(), fragment.name())
                        .commit();
                break;
            default:
                break;
        }

        currentFragment = fragment;
    }

    public void deconnecter() {
        idUser = -1;
        changeFragment(FRAGMENT.LOGIN);
    }

    @Override
    protected void onStop() {
        savePreferences();
        super.onStop();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "Permission Granted...", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                }
        }
    }

}
