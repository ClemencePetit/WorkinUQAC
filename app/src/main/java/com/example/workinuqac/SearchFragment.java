package com.example.workinuqac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    public static String currentQuery = "";
    private ArrayList<String> results = new ArrayList<>();;

    static SearchFragment newInstance() {
        return new SearchFragment();
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
        SearchView searchBar = view.findViewById(R.id.searchView);
        searchBar.setQuery(currentQuery, false);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentQuery = query;
                ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.SEARCH);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        searchUserOrClassroom(currentQuery);

        ListView resultsView = view.findViewById(R.id.resultsView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, results);
        resultsView.setAdapter(adapter);

    }

    private void searchUserOrClassroom(final String query) {
        if (!query.isEmpty()) {

            // todo remplacer avec la bonne requete BDD
            results.add("Clémence");
            results.add("Laura");
            results.add("Louis");
            results.add("Yoann");
            results.add("Clément Second");
            results.add("Laure Rattu");
            results.add("Lou Ysianne");
            results.add("Yohan Malaicri");
            results.add("Philippe Etchebest");
            results.add("Justin Bridou");
            results.add("Valérie Damidot");
            results.add("Cyril Féraud");
            for(int i = results.size() - 1; i >= 0; --i)
                if(!results.get(i).contains(query))
                    results.remove(i);
        }

        if (results.isEmpty()) {
            Toast.makeText(getContext(), "Aucun résultat correspondant", Toast.LENGTH_SHORT).show();
        }
    }
}
