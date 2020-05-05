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

import java.util.ArrayList;
import java.util.Arrays;

public class EdtEditFragment extends Fragment {

    private ArrayList<Course> tempCourses;

    public static EdtEditFragment newInstance() {
        EdtEditFragment EEF = new EdtEditFragment();
        return EEF;
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

        if(((MainActivity)getActivity()).currentUser==null) {
            ((MainActivity)getActivity()).currentUser=new User(((MainActivity)getActivity()).idUser);
        }
        if(!((MainActivity) getActivity()).currentUser.coursesIsNull()){
            tempCourses = ((MainActivity) getActivity()).currentUser.getCourses();
        }
        else{
            MyBDD.readUserCourses(((MainActivity)getActivity()).currentUser.getIdentifiant(), new MyBDD.OnDataReadEventListener() {
                @Override
                public void onEvent() {
                    ((MainActivity)getActivity()).currentUser.setCourses(MyBDD.getCurrentUserCoursesList());
                    reloadCourses();
                }
            });
        }



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
        ViewPager2 pager = (ViewPager2)view.findViewById(R.id.edtViewPager);
        //TODO parametres : tableaux des Cours du user
        if(tempCourses!=null)
        {
            pager.setAdapter(new EdtEditAdapter(this, tempCourses) {
            });
        }
        else
        {
            pager.setAdapter(new EdtEditAdapter(this, new ArrayList<Course>(Arrays.asList(new Course()))) {
            });
        }
    }

    private void addClass(){
        // TODO ajout d'un ViewPager2 vide au LinearLayout
    }

    private void validate(){
        // TODO valider les changements
        ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.CONNECTED_PROFILE);
    }

    private void cancel(){
        // TODO quitter
        ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.CONNECTED_PROFILE);
    }

    public void reloadCourses(){
        if(getView()!=null&&!((MainActivity) getActivity()).currentUser.coursesIsNull()) {
            ViewPager2 pager = (ViewPager2) getView().findViewById(R.id.edtViewPager);
            tempCourses=((MainActivity) getActivity()).currentUser.getCourses();
            //TODO parametres : tableaux des Cours du user
            pager.setAdapter(new EdtEditAdapter(this, tempCourses) {
            });

        }
    }

    public void removeCourse(int position){
        tempCourses.remove(position);
        ViewPager2 pager = (ViewPager2) getView().findViewById(R.id.edtViewPager);
        pager.setAdapter(new EdtEditAdapter(this, tempCourses) {
        });
        Toast.makeText(getContext(), "creation faite", Toast.LENGTH_SHORT).show();
    }

}
