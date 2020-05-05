package com.example.workinuqac;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class EdtAdapter extends FragmentStateAdapter {


    private ArrayList<Course> cours;

    // 2 - Default Constructor
    public EdtAdapter(FragmentActivity mgr, ArrayList<Course> cours) {
        super(mgr);
        this.cours = cours;
    }

    @Override
    public int getItemCount() {
        return(cours.size());
    }

    @Override
    public Fragment createFragment(int position) {
        return(EdtFragment.newInstance(cours.get(position).getId(), cours.get(position).getName(),cours.get(position).getDay(),cours.get(position).getHours()));

    }
}
