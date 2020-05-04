package com.example.workinuqac;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class EdtAdapter extends FragmentStateAdapter {

    //TODO creer classe Cours contenant toutes les infos a afficher d'un cours + remplacer par un tableau de Cours
    private ArrayList<String> cours;

    // 2 - Default Constructor
    public EdtAdapter(FragmentActivity mgr, ArrayList<String> cours) {
        super(mgr);
        this.cours = cours;
    }

    @Override
    public int getItemCount() {
        //TODO taille du tableau
        return(5);
    }

    @Override
    public Fragment createFragment(int position) {
        //TODO envoyer le cours present a la position donne du tableau cours de Cours
        return(EdtFragment.newInstance("8INF123", "nom du cours","jour du cours","horaires du cours"));
    }
}
