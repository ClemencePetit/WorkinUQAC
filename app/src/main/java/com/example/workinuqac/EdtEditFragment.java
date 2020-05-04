package com.example.workinuqac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class EdtEditFragment extends Fragment {

    //id fragment : 0
    public static EdtEditFragment newInstance() {
        EdtEditFragment CF = new EdtEditFragment();
        return CF;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edt_edit_layout, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button addClassButton = view.findViewById(R.id.addClassButton);
        addClassButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                addClass();
            }
        });
        Button validateButton = view.findViewById(R.id.validateEditButton);
        validateButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                validate();
            }
        });
        Button cancelButton = view.findViewById(R.id.cancelEditButton);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                cancel();
            }
        });


        LinearLayout listOfCourses = view.findViewById(R.id.edtItemsContainer);
        // TODO faire requete des cours
        // TODO pour chaque cours, créer un ViewPager2 comme le suivant, à ajou
        /*
        ViewPager2 pager; // il faut l'init
        pager.setAdapter(new EdtItemEditAdapter(getActivity(), LE_COURS) {
        });
        listOfCourses.addView(pager);
        */
    }

    private void addClass(){
        // TODO ajout d'un ViewPager2 vide au LinearLayout
    }

    private void validate(){
        // TODO valider les changements et quitter
    }

    private void cancel(){
        // TODO quitter
    }
}
