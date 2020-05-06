package com.example.workinuqac;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.browse.MediaBrowser;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBDD {

    static private String currentEmail;
    static private String currentUsername;
    static private String currentCodePermanent;
    static private HashMap<String,String> currentUserCoursesList;
    static private ArrayList<String> allCoursesCodeWithSchedule;
    static private ArrayList<String> allCoursesCode;
    static private ArrayList<String> queryResultStudentsFromCourse;
    static private ArrayList<String> queryResultStudentsFromCourseWithSchedule;
    static private User queryResultStudentFromCode;
    static private Bitmap queryResultImage;

    //GETTERS
    static public String getCurrentEmail(){
        return currentEmail;
    }

    static public String getCurrentUsername(){
        return currentUsername;
    }

    static public String getCurrentCodePermanent(){
        return currentCodePermanent;
    }

    static public HashMap<String,String> getCurrentUserCoursesList(){
        return currentUserCoursesList;
    }

    static public ArrayList<String> getAllCoursesCode(){
        return allCoursesCode;
    }

    static public ArrayList<String> getAllCoursesCodeWithSchedule() {return allCoursesCodeWithSchedule;}

    static public ArrayList<String> getQueryResultStudentsFromCourse(){return queryResultStudentsFromCourse;}

    static public ArrayList<String> getQueryResultStudentsFromCourseWithSchedule() {return queryResultStudentsFromCourseWithSchedule;}

    static public User getQueryResultStudentFromCode() {return queryResultStudentFromCode;}

    static public Bitmap getQueryResultImage() {return queryResultImage;}

    static public String translate(String scheduleCode){
        String translation = scheduleCode.replace("MO","Monday ")
        .replace("TU","Tuesday ")
        .replace("WE","Wednesday ")
        .replace("TH","Thursday ")
        .replace("FR","Friday ")
        .replace("SA","Saturday ")
        .replace("SU","Sunday ");
        return translation;
    }

    //WRITING IN DB METHODS

    //USERS
    static public void writeNewUser(String codePermanent, String email,String name, List<String> cours){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        DatabaseReference emailRef = database.getReference("emailUsers");
        //on rajoute ou update le nom associé au code permanent

        myRef.child(codePermanent).child("name").setValue(name);
        myRef.child(codePermanent).child("courses").setValue(cours);
        myRef.child(codePermanent).child("email").setValue(email);
        emailRef.child(email.replace(".",",")).setValue(codePermanent);

        Log.d("BDD","User updated");
    }

    static public void updateUserName(String codePermanent, String Username){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        //on rajoute ou update le nom associé au code permanent

        myRef.child(codePermanent).child("name").setValue(Username);

        Log.d("BDD","User updated");
    }

    /*
    deprecated
    static public void updateUserEmail(String codePermanent, String Email){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        //on rajoute ou update le nom associé au code permanent
        myRef.child(codePermanent).child("email").setValue(Email);

        Log.d("BDD","User updated");
    }*/

    static public void updateUserCourses(String codePermanent, HashMap<String,String> coursSchedule){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");
        DatabaseReference coursesRef = database.getReference("courses");
        //on rajoute ou update le nom associé au code permanent
        usersRef.child(codePermanent).child("courses").setValue(coursSchedule);

        Log.d("BDD","User updated");

        //On inscrit l'étudiant aux cours
        for(Map.Entry<String,String> entry : coursSchedule.entrySet()){
            String codeCours = entry.getKey();
            String scheduleCours = entry.getValue();
            coursesRef.child(codeCours).child(scheduleCours).child(codePermanent).setValue(true);
        }

        Log.d("BDD","Courses updated");

    }

    static public void addUserCourse(String codePermanent, String codeCours, String schedule){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");
        DatabaseReference coursesRef = database.getReference("courses");
        //on rajoute ou update le nom associé au code permanent
        usersRef.child(codePermanent).child("courses").child(codeCours).setValue(schedule);
        coursesRef.child(codeCours).child(schedule).child(codePermanent).setValue(true);
    }

    static public void removeUserCourse(final String codePermanent, final String codeCours, final OnDataReadEventListener oc){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference("users/"+codePermanent+"/courses/"+codeCours);
        ValueEventListener nameListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                database.getReference("courses/"+codeCours+"/"+dataSnapshot.getValue()+"/"+codePermanent).removeValue();
                userRef.removeValue();
                oc.onEvent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("BDD", "loadName:onCancelled",databaseError.toException());
            }
        };
        userRef.addListenerForSingleValueEvent(nameListener);
    }

    static public void storeImage(String codePermanent, Bitmap bitmap, OnFailureListener OFL, OnSuccessListener OSL){
        //Getting references
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imgRef = storage.getReference();
        StorageReference codeRef = imgRef.child(codePermanent+".png");

        //storing image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = codeRef.putBytes(data);
        uploadTask.addOnFailureListener(OFL).addOnSuccessListener(OSL);
    }

    //READING DB METHODS
    public interface OnDataReadEventListener{
        public void onEvent();
    }

    static public void readImage(String codePermanent, final OnDataReadEventListener oc){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(codePermanent + ".png");
        final long ONE_MEGABYTE = 1024 * 1024 * 1024;

        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                queryResultImage = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                oc.onEvent();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.d("BDD", "error download : " + exception.getMessage());
            }
        });
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
                currentUserCoursesList = new HashMap<String,String>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    currentUserCoursesList.put(postSnapshot.getKey().toString(),postSnapshot.getValue().toString());
                }
                oc.onEvent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("BDD","loadCourses:onCancelled",databaseError.toException());
            }
        });
    }

    //read all the courses in BDD. Result stored in allCoursesCode and allCoursesCodeWithSchedule. Execute oc when data loaded
    static public void updateCoursesLists(final OnDataReadEventListener oc){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference coursRef = database.getReference("courses");
        //Adding Listener on courses
        coursRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allCoursesCode = new ArrayList<String>();
                allCoursesCodeWithSchedule = new ArrayList<String>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    allCoursesCode.add(postSnapshot.getKey());
                    for(DataSnapshot postSnapshotChild : postSnapshot.getChildren() ){
                        if(!postSnapshotChild.getKey().equals("title"))
                            allCoursesCodeWithSchedule.add(postSnapshot.getKey() + "," + postSnapshotChild.getKey());
                    }
                }
                oc.onEvent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("BDD","loadCourses:onCancelled",databaseError.toException());
            }
        });
    }

    static public void querryStudentsFromCourse(String codeCours, String schedule, final OnDataReadEventListener oc){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference coursesRef = database.getReference("courses/"+codeCours+"/"+schedule);
        //Adding Listener on students list
        Log.d("BDD","Starting request");
        ValueEventListener studentListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                queryResultStudentsFromCourseWithSchedule = new ArrayList<String>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    queryResultStudentsFromCourseWithSchedule.add(postSnapshot.getKey());
                }
                oc.onEvent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("BDD", "loadName:onCancelled",databaseError.toException());
            }
        };
        coursesRef.addListenerForSingleValueEvent(studentListener);
    }

    static public void querryStudentsFromCourse(String codeCours, final OnDataReadEventListener oc){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference coursesRef = database.getReference("courses/"+codeCours);
        //Adding Listener on students list
        Log.d("BDD","Starting request");
        ValueEventListener studentListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                queryResultStudentsFromCourse = new ArrayList<String>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if(!postSnapshot.getKey().equals("title")){
                        for(DataSnapshot postSnapshotChild : postSnapshot.getChildren()){
                            queryResultStudentsFromCourse.add(postSnapshotChild.getKey());
                        }
                    }
                }
                oc.onEvent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("BDD", "loadName:onCancelled",databaseError.toException());
            }
        };
        coursesRef.addListenerForSingleValueEvent(studentListener);
    }

    static public void querryCodeFromEmail(String rawEmail, final OnDataReadEventListener oc){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference codesRef = database.getReference("emailUsers/"+rawEmail.replace(".",","));
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentCodePermanent = dataSnapshot.getValue(String.class);
                Log.d("BDD","Value : " + dataSnapshot.getValue(String.class));
                oc.onEvent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("BDD", "loadCodePermanent:onCancelled",databaseError.toException());
            }
        };
        codesRef.addListenerForSingleValueEvent(listener);
    }

    static public void queryStudentFromCode(final String codePermanent, final OnDataReadEventListener oc){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference nameRef = database.getReference("users/"+codePermanent);
        ValueEventListener nameListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = "";
                String email = "";

                queryResultStudentFromCode = new User(codePermanent,name,email);
                queryResultStudentFromCode.setName(dataSnapshot.child("name").getValue(String.class));
                queryResultStudentFromCode.setName(dataSnapshot.child("email").getValue(String.class));
                oc.onEvent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("BDD", "loadName:onCancelled",databaseError.toException());
            }
        };
        nameRef.addListenerForSingleValueEvent(nameListener);
    }

    //TODO
    /*

    PARTIE USER
    -supprimer un user

     */
}
