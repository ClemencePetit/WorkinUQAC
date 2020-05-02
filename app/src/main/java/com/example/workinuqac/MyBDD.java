package com.example.workinuqac;

import android.media.browse.MediaBrowser;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyBDD {

    static private String currentEmail;
    static private String currentUsername;
    static private ArrayList<String> currentCoursesList;

    //GETTERS
    static public String getCurrentEmail(){
        return currentEmail;
    }

    static public String getCurrentUsername(){
        return currentUsername;
    }

    static public ArrayList<String> getCurrentCoursesList(){
        return currentCoursesList;
    }

    //WRITING IN DB METHODS
    static public void writeNewUser(String codePermanent, String email,String name, List<String> cours){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        //on rajoute ou update le nom associé au code permanent

        myRef.child(codePermanent).child("name").setValue(name);
        myRef.child(codePermanent).child("courses").setValue(cours);
        myRef.child(codePermanent).child("email").setValue(email);

        Log.d("BDD","User updated");
    }

    static public void updateUserName(String codePermanent, String Username){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        //on rajoute ou update le nom associé au code permanent

        myRef.child(codePermanent).child("name").setValue(Username);

        Log.d("BDD","User updated");
    }

    static public void updateEmail(String codePermanent, String Email){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        //on rajoute ou update le nom associé au code permanent
        myRef.child(codePermanent).child("email").setValue(Email);

        Log.d("BDD","User updated");
    }

    static public void updateCourses(String codePermanent, List<String> cours){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        //on rajoute ou update le nom associé au code permanent
        myRef.child(codePermanent).child("courses").setValue(cours);

        Log.d("BDD","User updated");
    }

    //READING DB METHODS
    public interface OnDataReadEventListener{
        public void onEvent();
    }

    //read the username of the user with codePermanent. Result stored in currentUsername;Execute oc when data loaded
    static public void readUserName(String codePermanent, final OnDataReadEventListener oc){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference nameRef = database.getReference("users/"+codePermanent+"/name");//Adding Listener on name
        ValueEventListener nameListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               currentUsername = dataSnapshot.getValue(String.class);
                oc.onEvent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("BDD", "loadName:onCancelled",databaseError.toException());
            }
        };
        nameRef.addListenerForSingleValueEvent(nameListener);
    }

    //read the email of the user with codePermanent. Result stored in currentEmail. Execute oc when data loaded
    static public void readUserEmail(String codePermanent, final OnDataReadEventListener oc){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference emailRef = database.getReference("users/"+codePermanent+"/email");
        //Adding Listener on email
        ValueEventListener emailListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentEmail = dataSnapshot.getValue(String.class);
                oc.onEvent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("BDD", "loadName:onCancelled",databaseError.toException());
            }
        };
        emailRef.addListenerForSingleValueEvent(emailListener);
    }

    //read the courses of the user with codePermanent. Result stored in currentCoursesList. Execute oc when data loaded
    static public void readUserCourses(String codePermanent, final OnDataReadEventListener oc){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference coursRef = database.getReference("users/"+codePermanent+"/courses");
        //Adding Listener on courses
        coursRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentCoursesList = new ArrayList<String>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    currentCoursesList.add(postSnapshot.getValue().toString());
                }
                oc.onEvent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("BDD","loadCourses:onCancelled",databaseError.toException());
            }
        });
    }

    //TODO
    /*
    PARTIE FIREBASE
    -créer la table de cours
    -sortir de la phase de tests (règles d'authentification, cf tuto)

    PARTIE USER
    -supprimer un user
    -ajouter un cours à un user
    -supprimer un cours

    PARTIE BDD
    -lire la liste des étudiants inscrits à un cours
    -lire la liste des cours?
    -rajouter un cours (unused normally)
    -supprimer un cours (unused normally)
    -ajouter un élève à un cours
    -supprimer un élève d'un cours
    -Gérer les profs & sessions
     */
}
