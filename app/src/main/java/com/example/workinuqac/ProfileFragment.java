package com.example.workinuqac;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class ProfileFragment extends Fragment {
    static User CURRENT_USER = null;

    static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_profile_layout, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //TODO chercher les infos dans la BDD et mettre à jour à partir de CURRENT_USER
        TextView name = view.findViewById(R.id.textName);
        name.setText(CURRENT_USER.getName());
        TextView email = view.findViewById(R.id.textEmail);
        email.setText(CURRENT_USER.getEmail());
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LOL ça marche pas ça me saoule j'ai d'autres choses à faire
                // Toast.makeText(getContext(), "LOL", Toast.LENGTH_SHORT);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + CURRENT_USER.getEmail()));
            }
        });

        ViewPager2 pager = (ViewPager2)view.findViewById(R.id.edtViewPager);
        //TODO parametres : tableaux des Cours du user
        pager.setAdapter(new EdtAdapter(getActivity(), null) {
        });

        Toast.makeText(getContext(), "Etudiant #" + CURRENT_USER.getIdentifiant(), Toast.LENGTH_SHORT).show();
    }
}
