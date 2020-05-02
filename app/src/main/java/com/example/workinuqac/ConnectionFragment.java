package com.example.workinuqac;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;

import androidx.fragment.app.Fragment;

public class ConnectionFragment extends Fragment {

    private static final int RC_SIGN_IN = 1000;

    //id fragment : 1
    public static ConnectionFragment newInstance() {
        ConnectionFragment CF = new ConnectionFragment();
        return CF;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.connection_layout, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button buttonCreate = (Button) view.findViewById(R.id.createProfileButton);
        buttonCreate.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                createProfil();
            }
        });
        Button buttonIdentify = (Button) view.findViewById(R.id.identifyProfileButton);
        buttonIdentify.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                identifyProfil();
            }
        });

        SignInButton GoogleButton = (SignInButton) view.findViewById(R.id.GoogleSignInButton);
        GoogleButton.setOnClickListener(new SignInButton.OnClickListener() {
            public void onClick(View v) {
                googleSignIn();
            }
        });
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void createProfil(){
        Toast.makeText(getContext(), "Creation profil", Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.INSCRIPTION);

    }

    private void identifyProfil(){
        Toast.makeText(getContext(), "Identification profil", Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.USER_PROFILE);
    }
}
