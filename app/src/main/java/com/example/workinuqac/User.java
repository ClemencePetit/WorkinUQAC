package com.example.workinuqac;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class User {
    private String identifiant;
    private String name;
    private String email;
    private Bitmap photo;
    //TODO changer pour le bon format
    private ArrayList<Course> courses;

    public User(){

    }
    public User(String identifiant){
        this.identifiant=identifiant;
        this.photo= null;
        this.name="";
        this.email="";
    }

    public User(String identifiant, String name, String email){
        this.identifiant=identifiant;
        this.name=name;
        this.email=email;
        this.photo= null;
    }

    public User(String identifiant, String name, String email, Bitmap photo, ArrayList<Course> courses){
        this.identifiant=identifiant;
        this.name=name;
        this.email=email;
        this.photo= photo;
        if(courses==null){
            this.courses=null;
        }
        else {
            this.courses=new ArrayList<Course>();
            for (int counter = 0; counter < courses.size(); counter++) {
                this.courses.add((courses.get(counter).duplicate()));
            }
        }
    }

    public String getIdentifiant(){
        return identifiant != null ? identifiant : "";
    }

    public String getName(){
        return name != null ? name : "";
    }

    public String getEmail(){
        return email != null ? email : "";
    }

    public Bitmap getPhoto(){
        return photo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public void setCourses(HashMap<String,String> courses){
        this.courses=new ArrayList<Course>();
        for (Map.Entry<String, String> entry : courses.entrySet()) {
            String temp=entry.getValue();
            this.courses.add(new Course(entry.getKey(),"NaN",MyBDD.translate(temp.substring(0,2)),temp.substring(2)));
        }
        //this.courses=(ArrayList<String>) courses.clone();
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public User clone() {
        return new User(identifiant,name,email,photo,courses);
    }

    public void clear(String identifiant){
        this.identifiant=identifiant;
        name="";
        email="";
        courses=null;
        photo=null;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile(String pathImage,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathImage, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathImage, options);
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public boolean isDefined() {
        return name != null && !name.isEmpty();
    }
}
