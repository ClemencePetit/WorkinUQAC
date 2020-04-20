package com.example.workinuqac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final int PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_main_portrait);
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_main_land);

        /* check Permission READ and WRITE on external storage at runtime */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_CODE);
            }else {
                //Permission already granted
            }
        } else {
            //Old OS
        }

        ConnectionFragment newFrag = ConnectionFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.placeholder, newFrag)
                .commitNow();
    }

    public void changeFragment(){
        //argument : vers quel fragment aller par la suite
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft
                .addToBackStack(null)
                .replace(R.id.placeholder, ConnectedFragment.newInstance())
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                }
        }
    }

}
