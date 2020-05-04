package com.example.workinuqac;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class User {
    private String identifiant;
    private String name;
    private String email;
    private Bitmap photo;

    public User(){

    }
    public User(String identifiant, Context ctx){
        this.identifiant=identifiant;
        this.photo= photo=decodeSampledBitmapFromResource(ctx.getResources(),R.drawable.profile_picture_default, 250, 250);
    }

    public User(String identifiant, String name, String email, Context ctx){
        this.identifiant=identifiant;
        this.name=name;
        this.email=email;
        this.photo= photo=decodeSampledBitmapFromResource(ctx.getResources(),R.drawable.profile_picture_default, 250, 250);
    }

    public User(String identifiant, String name, String email, Bitmap photo){
        this.identifiant=identifiant;
        this.name=name;
        this.email=email;
        this.photo= photo;
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


    public User clone() {
        return new User(identifiant,name,email,photo);
    }

    public void clear(String identifiant, Context ctx){
        this.identifiant=identifiant;
        name="";
        email="";
        photo=(BitmapFactory.decodeResource(ctx.getResources(),
                R.drawable.profile_picture_default));
        photo=decodeSampledBitmapFromResource(ctx.getResources(),R.drawable.profile_picture_default, 250, 250);
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
