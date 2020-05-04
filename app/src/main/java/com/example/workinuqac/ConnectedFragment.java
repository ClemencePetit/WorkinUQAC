package com.example.workinuqac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
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
        return inflater.inflate(R.layout.connected_profile_layout, parent, false);
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
                signOut();
            }
        });

        TextView nameTxt=view.findViewById(R.id.textName);
        nameTxt.setText(((MainActivity)getActivity()).currentUser.getName());

        ImageView photo=view.findViewById(R.id.profileImage);
        photo.setImageBitmap(((MainActivity)getActivity()).currentUser.getPhoto());

        final SearchView userSearch = view.findViewById(R.id.searchView);
        userSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ClassSearchFragment.hideKeyboard(getActivity());
                searchUser(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });


        ViewPager2 pager = (ViewPager2)view.findViewById(R.id.edtViewPager);
        //TODO parametres : tableaux des Cours du user
        pager.setAdapter(new EdtAdapter(getActivity(), null) {
        });
        //Toast.makeText(getContext(), "creation faite", Toast.LENGTH_SHORT).show();
    }

    private void searchUser(String codePermanent){
        /* TODO requete de recherche user par code permanent. Au callback, executer:

                if(resultat != null) {
                    userSearch.setQuery("", false);
                    ProfileFragment.CURRENT_USER = resultat;
                    ((MainActivity) getActivity()).changeFragment(MainActivity.FRAGMENT.USER_PROFILE);
                } else {
                    Toast.makeText(getContext(), "Utilisateur introuvable", Toast.LENGTH_SHORT);
                }

        */
    }

    private void editEdt(){
        Toast.makeText(getContext(), "Edit EDT", Toast.LENGTH_SHORT).show();

    }
    private void editProfile(){
        ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.PROFILE_EDIT);

    }

    private void signOut(){
        ((MainActivity)getActivity()).signOut();
    }
}
