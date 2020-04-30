package com.example.workinuqac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class ConnectedFragment extends Fragment {

    //id fragment : 0
    public static ConnectedFragment newInstance() {
        ConnectedFragment CF = new ConnectedFragment();
        return CF;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_layout, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //TODO chercher les infos dans la BDD et mettre à jour
        Button buttonEdt = (Button) view.findViewById(R.id.edtButton);
        buttonEdt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                editEdt();
            }
        });
        Button buttonpicture = (Button) view.findViewById(R.id.pictureButton);
        buttonpicture.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                editProfile();
            }
        });
        Button buttonDeconnexion = (Button) view.findViewById(R.id.deconnectionButton);
        buttonDeconnexion.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                deconnecter();
            }
        });


        ViewPager2 pager = (ViewPager2)view.findViewById(R.id.edtViewPager);
        //TODO parametres : tableaux des Cours du user
        pager.setAdapter(new EdtAdapter(getActivity(), null) {
        });
        Toast.makeText(getContext(), "creation faite", Toast.LENGTH_SHORT).show();

    }

    private void searchUser(){
        Toast.makeText(getContext(), "Search User", Toast.LENGTH_SHORT).show();
    }

    private void editEdt(){
        Toast.makeText(getContext(), "Edit EDT", Toast.LENGTH_SHORT).show();

    }
    private void editProfile(){
        ((MainActivity)getActivity()).changeFragment(3);

    }

    private void deconnecter(){
        ((MainActivity)getActivity()).deconnecter();
    }
}
