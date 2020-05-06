package com.example.workinuqac;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileFragment extends Fragment {

    static User CURRENT_USER = null;
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

        TextView nameTxt=view.findViewById(R.id.textName);
        if(CURRENT_USER.getName().isEmpty())
        {
            MyBDD.readUserName(CURRENT_USER.getIdentifiant(), new MyBDD.OnDataReadEventListener() {
                @Override
                public void onEvent() {
                    CURRENT_USER.setName( MyBDD.getCurrentUsername());
                    reloadName();
                }
            });
            nameTxt.setText("Loading...");
        }
        else {
            nameTxt.setText(CURRENT_USER.getName());
        }

        TextView mailTxt=view.findViewById(R.id.textEmail);
        if(CURRENT_USER.getEmail().isEmpty()){
            MyBDD.readUserEmail(CURRENT_USER.getIdentifiant(), new MyBDD.OnDataReadEventListener() {
                @Override
                public void onEvent() {
                    CURRENT_USER.setEmail(MyBDD.getCurrentEmail());
                    reloadMail();
                }
            });
            mailTxt.setText("Loading...");
        }
        else {
            mailTxt.setText(CURRENT_USER.getEmail());
        }
		mailTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + CURRENT_USER.getEmail()));
            }
        });

        ImageView photo=view.findViewById(R.id.profileImage);


        if(CURRENT_USER.getPhoto()==null){
            MyBDD.readImage(CURRENT_USER.getIdentifiant(), new MyBDD.OnDataReadEventListener() {
                @Override
                public void onEvent() {
                    CURRENT_USER.setPhoto( MyBDD.getQueryResultImage());
                    reloadPhoto();
                }
            });
            photo.setImageBitmap(((MainActivity)getActivity()).defaultProfileImage);
        }
        else
        {
            photo.setImageBitmap(CURRENT_USER.getPhoto());
        }





        ViewPager2 pager = (ViewPager2)view.findViewById(R.id.edtViewPager);
        if(!CURRENT_USER.coursesIsNull())
        {
            pager.setAdapter(new EdtAdapter(getActivity(), CURRENT_USER.getCourses()) {
            });
        }
        else
        {
            pager.setAdapter(new EdtAdapter(getActivity(), new ArrayList<Course>(Arrays.asList(new Course()))) {
            });
            MyBDD.readUserCourses(CURRENT_USER.getIdentifiant(), new MyBDD.OnDataReadEventListener() {
                @Override
                public void onEvent() {
                    CURRENT_USER.setCourses(MyBDD.getCurrentUserCoursesList());
                    reloadCourses();
                }
            });
        }
    }

    public void reloadName(){
        if(getView()!=null) {
            TextView nameTxt = getView().findViewById(R.id.textName);
            nameTxt.setText(CURRENT_USER.getName());
        }

    }

    public void reloadMail(){
        if(getView()!=null) {
            TextView mailTxt = getView().findViewById(R.id.textEmail);
            mailTxt.setText(CURRENT_USER.getEmail());
        }
    }

    public void reloadPhoto(){
        if (getView() != null && CURRENT_USER.getPhoto()!=null){
            ImageView photo = getView().findViewById(R.id.profileImage);
            photo.setImageBitmap(CURRENT_USER.getPhoto());
        }
    }

    public void reloadCourses(){
        if(getView()!=null&&!CURRENT_USER.coursesIsNull()) {
            ViewPager2 pager = (ViewPager2) getView().findViewById(R.id.edtViewPager);
            pager.setAdapter(new EdtAdapter(getActivity(), CURRENT_USER.getCourses()) {
            });
        }
    }


}
