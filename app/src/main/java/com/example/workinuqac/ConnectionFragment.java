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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ConnectionFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{

    private static final int RC_SIGN_IN = 1000;
    private GoogleApiClient mGoogleApiClient;

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .enableAutoManage(getActivity(), this)
                    .addConnectionCallbacks(this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(),"Google SignIn failed", Toast.LENGTH_SHORT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(getContext(), "Hello" + acct.getDisplayName(), Toast.LENGTH_SHORT);
            ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.USER_PROFILE);
        }
        else {
            //Connection Failed
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }
}
