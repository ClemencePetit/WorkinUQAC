package com.example.workinuqac;

import android.app.Activity;
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
    static String currentClass = "", currentSchedule = "";
    private ArrayList<String> results = new ArrayList<>();

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
        searchBar.setQuery(currentClass, false);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // reload search
                searchUsersForClassroom(currentClass = query, currentSchedule = "");
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
                // Display student
                MyBDD.queryStudentFromCode(results.get(position), new MyBDD.OnDataReadEventListener() {
                    @Override
                    public void onEvent() {
                        ProfileFragment.CURRENT_USER = MyBDD.getQueryResultStudentFromCode();
                        ((MainActivity) getActivity()).changeFragment(MainActivity.FRAGMENT.USER_PROFILE);
                    }
                });
            }
        });

        searchUsersForClassroom(currentClass, currentSchedule);
    }

    private void searchUsersForClassroom(final String classCode, final String schedule) {
        results.clear();
        resultsView.setAdapter(null);

        if (!classCode.isEmpty()) {

            MyBDD.querryStudentsFromCourse(classCode, schedule, new MyBDD.OnDataReadEventListener() {
                @Override
                public void onEvent() {
                    results = MyBDD.getQueryResultStudentsFromCourseWithSchedule();

                    if (results.isEmpty()) { // unlikely to happen
                        Toast.makeText(getContext(), "Aucun autre étudiant ne se trouve dans ce cours", Toast.LENGTH_SHORT).show();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, results);
                    resultsView.setAdapter(adapter);
                }
            });
        }
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
