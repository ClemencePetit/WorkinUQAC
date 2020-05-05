package com.example.workinuqac;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class EdtEditAdapter extends FragmentStateAdapter {
    private ArrayList<Course> cours;

    // 2 - Default Constructor
    public EdtEditAdapter(FragmentActivity mgr, ArrayList<Course> cours) {
        super(mgr);
        this.cours = cours;
    }

    @Override
    public int getItemCount() {
        //TODO taille du tableau
        return(cours.size());
    }

    @Override
    public Fragment createFragment(int position) {
        //TODO envoyer le cours present a la position donne du tableau cours de Cours

        return(EdtEditItemFragment.newInstance(cours.get(position).getId(), cours.get(position).getName(),cours.get(position).getDay(),cours.get(position).getHours()));

    }
}