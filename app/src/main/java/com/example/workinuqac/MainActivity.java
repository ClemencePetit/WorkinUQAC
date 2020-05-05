package com.example.workinuqac;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.workinuqac.User.decodeSampledBitmapFromResource;

public class MainActivity extends AppCompatActivity {

    enum FRAGMENT {
        CONNECTED_PROFILE,
        LOGIN,
        AUTHENTICATION,
        INSCRIPTION,
        PROFILE_EDIT,
        CLASS_SEARCH,
        USER_PROFILE,
        CODE
    }

    private static final int PERMISSION_CODE = 1000;
    public String idUser = ""; // ID de l'utilisateur dans la base de données - vide = pas connecté
    public User currentUser;
    public User searchedUser;
    private FRAGMENT currentFragment = FRAGMENT.LOGIN;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 1000;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignIn;
    private boolean googleConnexion = false;

    public Bitmap defaultProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_main_portrait);
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_main_land);

        loadPreferences();

        mAuth = FirebaseAuth.getInstance();

        defaultProfileImage= decodeSampledBitmapFromResource(getApplicationContext().getResources(),R.drawable.profile_picture_default, 250, 250);

        if(savedInstanceState==null) {
            // Define previous fragment(s)
            FRAGMENT fragmentToLoad = currentFragment;
            switch (currentFragment) {
                case INSCRIPTION:
                    changeFragment(FRAGMENT.LOGIN);
                    break;
                case PROFILE_EDIT:
                case CLASS_SEARCH:
                    changeFragment(FRAGMENT.CONNECTED_PROFILE);
                    break;

                case USER_PROFILE:
					changeFragment(FRAGMENT.CONNECTED_PROFILE);
                    changeFragment(FRAGMENT.CLASS_SEARCH);
					break;
                
            }
            changeFragment(fragmentToLoad);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("idUser", idUser);
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
        //on était déjà identifié quand on a fermé l'appli
        if(!idUser.isEmpty()){
            connection();
        }

        //Sign In with Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignIn = GoogleSignIn.getClient(this,gso);
    }

    public void savePreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idUser", idUser);
        editor.putString("fragment", currentFragment.name());
        editor.commit();
    }

    public void loadPreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        idUser = sharedPreferences.getString("idUser", "");
        currentFragment = FRAGMENT.valueOf(sharedPreferences.getString("fragment", FRAGMENT.LOGIN.name()));
        Log.e("Id User", idUser);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentFragment = FRAGMENT.valueOf(getSupportFragmentManager().findFragmentById(R.id.placeholder).getTag());
        savePreferences();
    }

    public void connection(){
        currentUser=new User(idUser);
    }

    public void changeFragment(FRAGMENT fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // Argument : vers quel fragment aller par la suite
        switch (fragment) {
            case CONNECTED_PROFILE:
                ft
                        //.addToBackStack(null)
                        .replace(R.id.placeholder, ConnectedFragment.newInstance(), fragment.name())
                        .commit();

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
            case CLASS_SEARCH:
                ft
                        .addToBackStack(null)
                        .replace(R.id.placeholder, ClassSearchFragment.newInstance(), fragment.name())
                        .commit();
                break;
            case USER_PROFILE:
                ft
                        .addToBackStack(null)
                        .replace(R.id.placeholder, ProfileFragment.newInstance(), fragment.name())
                        .commit();
                break;
            case AUTHENTICATION:
                ft
                        .addToBackStack(null);
                DialogFragment dialogFragment = new DialogFragmentAuthentification();
                dialogFragment.show(ft, "dialog");
                break;

            case CODE:
                ft
                        .addToBackStack(null);
                DialogFragment dialogFragmentCode = new DialogFragmentCodePermanent();
                dialogFragmentCode.show(ft, "dialog");
                break;
            default:
                break;
        }

        currentFragment = fragment;
    }

    public void googleSignIn() {
        googleConnexion = true;
        Intent signInIntent = mGoogleSignIn.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            changeFragment(MainActivity.FRAGMENT.CODE);
        }
        catch (ApiException e) {
            Log.w("Google Sign in", "Sign in result failed : error code= " + e.getStatusCode());
        }
    }

    public void signOut() {
        idUser = "";
        currentUser=null;
        changeFragment(FRAGMENT.LOGIN);
        if (googleConnexion) {
            mGoogleSignIn.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.e("Google", "Sign out");
                }
            });
            googleConnexion = false;
        }
        mAuth.signOut();
    }

    @Override
    protected void onStop() {
        savePreferences();
        super.onStop();
    }

    public void createProfileBDD(){
        MyBDD.writeNewUser(currentUser.getIdentifiant(),currentUser.getEmail(), currentUser.getName(),null);
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
