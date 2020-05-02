package com.example.workinuqac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class ProfileFragment extends Fragment {
    static int CURRENT_STUDENT_ID = 0;

    static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_profile_layout, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //TODO chercher les infos dans la BDD et mettre à jour à partir de CURRENT_STUDENT_ID

        ViewPager2 pager = (ViewPager2)view.findViewById(R.id.edtViewPager);
        //TODO parametres : tableaux des Cours du user
        pager.setAdapter(new EdtAdapter(getActivity(), null) {
        });

        Toast.makeText(getContext(), "Etudiant #" + CURRENT_STUDENT_ID, Toast.LENGTH_SHORT).show();
    }
}
