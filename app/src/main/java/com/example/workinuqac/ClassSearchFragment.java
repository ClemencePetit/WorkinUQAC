package com.example.workinuqac;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ClassSearchFragment extends Fragment {

    private ListView resultsView;


    private static class Student {
        private final String id;
        private final String name;

        Student(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }


    static String currentQuery = "";
    private ArrayList<User> results = new ArrayList<>();

    static ClassSearchFragment newInstance() {
        return new ClassSearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_layout, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final SearchView searchBar = view.findViewById(R.id.searchView);
        searchBar.setQuery(currentQuery, false);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // reload search
                searchUsersForClassroom(currentQuery = query);
                hideKeyboard(getActivity());
                searchBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        resultsView = view.findViewById(R.id.resultsView);
        resultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Display student TODO
                ((MainActivity) getActivity()).searchedUser = results.get(position);
                ((MainActivity) getActivity()).changeFragment(MainActivity.FRAGMENT.USER_PROFILE);
            }
        });

        searchUsersForClassroom(currentQuery);
    }

    private void searchUsersForClassroom(final String classCode) {
        ArrayList<String> stringResults = new ArrayList<>();
        results.clear();


        if (!classCode.isEmpty()) {

            //***** todo remplacer avec la bonne requete BDD
            // - attribuer results
            // - remplir stringResults
            results.add(new User("PETC25629800", "Clémence",""));
            results.add(new User("BOUL26619706","Laura",""));
            results.add(new User("2","Louis",""));
            results.add(new User("TEST","Yoann",""));
            results.add(new User("5","Clément Second",""));
            results.add(new User("8","Laure Rattu",""));
            results.add(new User("13","Lou Ysianne",""));
            results.add(new User("16","Yohan Malaicri",""));
            results.add(new User("21","Philippe Etchebest",""));
            results.add(new User("22","Justin Bridou",""));
            results.add(new User("23","Valérie Damidot",""));
            results.add(new User("25","Cyril Féraud",""));
            for (int i = results.size() - 1; i >= 0; --i) {
                if (!results.get(i).getName().contains(classCode))
                    results.remove(i);
                else
                    stringResults.add(0, results.get(i).getName());
            }

            //**********************************************

            for (User user : results) stringResults.add(user.getName());
        }

        if (results.isEmpty()) { // unlikely to happen
            Toast.makeText(getContext(), "Aucun résultat correspondant", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, stringResults);
        resultsView.setAdapter(adapter);
    }

    static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
