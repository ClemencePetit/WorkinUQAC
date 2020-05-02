package com.example.workinuqac;

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

    static public void writeNewUser(String email,String name, List<String> cours){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        //on rajoute ou update le nom associé à l'email
        myRef.child(email).child("name").setValue(name);
        myRef.child(email).child("courses").setValue(cours);
    }

    static public void readUser(String email){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference nameRef = database.getReference("users/"+email+"/name");
        DatabaseReference coursRef = database.getReference("users/"+email+"courses");
        //Adding Listener on name
        ValueEventListener nameListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                Log.d("BDD","Username : " + name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("BDD", "loadName:onCancelled",databaseError.toException());
            }
        };
        nameRef.addListenerForSingleValueEvent(nameListener);
        //Adding Listener on courses
        coursRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    // TODO
                    Log.d("BDD","Username cours " + postSnapshot.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("BDD","loadCourses:onCancelled",databaseError.toException());
            }
        });
    }
}
