package com.example.workinuqac;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class User {
    private String identifiant;
    private String name;
    private String email;
    private Bitmap photo;
    //TODO changer pour le bon format
    private ArrayList<String> courses;

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

    public User(String identifiant, String name, String email, Bitmap photo, ArrayList<String> courses){
        this.identifiant=identifiant;
        this.name=name;
        this.email=email;
        this.photo= photo;
        if(courses==null){
            this.courses=null;
        }
        else {
            this.courses = (ArrayList<String>) courses.clone();
        }
    }

    public String getIdentifiant(){
        return identifiant;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
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

    public void setCourses(ArrayList<String> courses){
        this.courses=(ArrayList<String>) courses.clone();
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public User clone() {
        return new User(identifiant,name,email,photo,courses);
    }

    public void clear(String identifiant, Context ctx){
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

}
