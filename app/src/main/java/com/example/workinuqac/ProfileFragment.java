package com.example.workinuqac;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileFragment extends Fragment {

    static String CURRENT_STUDENT_ID = "";
    //private User searchedUser;


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

        if(((MainActivity)getActivity()).searchedUser==null){
            ((MainActivity)getActivity()).searchedUser=new User(CURRENT_STUDENT_ID);
        }
        else
        {
            CURRENT_STUDENT_ID= ((MainActivity)getActivity()).searchedUser.getIdentifiant();
        }





        //TODO chercher la photo comme les infos précédentes

        //TODO chercher les infos dans la BDD et mettre à jour à partir de CURRENT_STUDENT_ID
        TextView nameTxt=view.findViewById(R.id.textName);
        if(((MainActivity)getActivity()).searchedUser.getName().isEmpty())
        {
            MyBDD.readUserName(CURRENT_STUDENT_ID, new MyBDD.OnDataReadEventListener() {
                @Override
                public void onEvent() {
                    ((MainActivity)getActivity()).searchedUser.setName( MyBDD.getCurrentUsername());
                    reloadName();
                }
            });
            nameTxt.setText("Loading...");
        }
        else {
            nameTxt.setText(((MainActivity) getActivity()).searchedUser.getName());
        }

        TextView mailTxt=view.findViewById(R.id.textEmail);
        if(((MainActivity)getActivity()).searchedUser.getEmail().isEmpty()){
            MyBDD.readUserEmail(CURRENT_STUDENT_ID, new MyBDD.OnDataReadEventListener() {
                @Override
                public void onEvent() {
                    ((MainActivity)getActivity()).searchedUser.setEmail(MyBDD.getCurrentEmail());
                    reloadMail();
                }
            });
            mailTxt.setText("Loading...");
        }
        else {
            mailTxt.setText(((MainActivity) getActivity()).searchedUser.getEmail());
        }
		mailTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LOL ça marche pas ça me saoule j'ai d'autres choses à faire
                // Toast.makeText(getContext(), "LOL", Toast.LENGTH_SHORT);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + ((MainActivity) getActivity()).searchedUser.getEmail()));
            }
        });

        ImageView photo=view.findViewById(R.id.profileImage);
        photo.setImageBitmap(((MainActivity)getActivity()).defaultProfileImage);



        ViewPager2 pager = (ViewPager2)view.findViewById(R.id.edtViewPager);
        //TODO parametres : tableaux des Cours du user
        pager.setAdapter(new EdtAdapter(getActivity(), new ArrayList<String>(Arrays.asList("Loading"))) {
        });

        Toast.makeText(getContext(), "Etudiant #" + ((MainActivity) getActivity()).searchedUser.getIdentifiant(), Toast.LENGTH_SHORT).show();
    }

    public void reloadName(){
        if(getView()!=null) {
            TextView nameTxt = getView().findViewById(R.id.textName);
            nameTxt.setText(((MainActivity) getActivity()).searchedUser.getName());
        }

    }

    public void reloadMail(){
        if(getView()!=null) {
            TextView mailTxt = getView().findViewById(R.id.textEmail);
            mailTxt.setText(((MainActivity) getActivity()).searchedUser.getEmail());
        }
    }

    public void reloadPhoto(){
        if (getView() != null && ((MainActivity) getActivity()).searchedUser.getPhoto()!=null){
            ImageView photo = getView().findViewById(R.id.profileImage);
            photo.setImageBitmap(((MainActivity) getActivity()).searchedUser.getPhoto());
        }
    }

    public void reloadCourses(){
        if(getView()!=null) {
            ViewPager2 pager = (ViewPager2) getView().findViewById(R.id.edtViewPager);
            //TODO parametres : tableaux des Cours du user
            pager.setAdapter(new EdtAdapter(getActivity(), new ArrayList<String>(Arrays.asList("Mon vrai cours 1",
                    "Mon vrai cours 2",
                    "Mon vrai cours 3",
                    "Mon vrai cours 4",
                    "Mon vrai cours 5",
                    "Mon vrai cours 6",
                    "Mon vrai cours 7",
                    "Mon vrai cours 8",
                    "Mon vrai cours 9",
                    "Mon vrai cours 10",
                    "Mon vrai cours 11",
                    "Mon vrai cours 12",
                    "Mon vrai cours 13",
                    "Mon vrai cours 14",
                    "Mon vrai cours 15",
                    "Mon vrai cours 16",
                    "Mon vrai cours 17",
                    "Mon vrai cours 18"))) {
            });
        }
    }


}
