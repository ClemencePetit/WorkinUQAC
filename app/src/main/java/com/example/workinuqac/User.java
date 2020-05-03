package com.example.workinuqac;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class User {
    private String identifiant;
    private String name;
    private String email;
    private String status;
    private Bitmap photo;

    public User(){

    }
    public User(String identifiant, Context ctx){
        this.status="vient de s\'inscrire";
        this.photo= BitmapFactory.decodeResource(ctx.getResources(),
                R.drawable.profile_picture_default);
    }

    public User(String identifiant, String name, String email, Context ctx){
        this.identifiant=identifiant;
        this.name=name;
        this.email=email;
        this.status="vient de s\'inscrire";
        this.photo= BitmapFactory.decodeResource(ctx.getResources(),
                R.drawable.profile_picture_default);
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

    public String getStatus(){
        return status;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
