package com.example.workinuqac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class EdtFragment extends Fragment {

    // 1 - Create keys for our Bundle
    private static final String KEY_CODE = "code";
    private static final String KEY_NAME = "name";
    private static final String KEY_DAY = "day";
    private static final String KEY_HOUR = "hour";


    public EdtFragment() {
    }


    // 2 - Method that will create a new instance of PageFragment, and add data to its bundle.
    static EdtFragment newInstance(String code, String name, String day, String hour) {

        // 2.1 Create new fragment
        EdtFragment EF = new EdtFragment();

        // 2.2 Create bundle and add it some data
        Bundle args = new Bundle();
        args.putString(KEY_CODE, code);
        args.putString(KEY_NAME, name);
        args.putString(KEY_DAY, day);
        args.putString(KEY_HOUR, hour);
        EF.setArguments(args);

        return (EF);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.edt_layout, container, false);

        TextView className = (TextView) result.findViewById(R.id.classNameText);
        TextView classDay = (TextView) result.findViewById(R.id.classDayText);
        TextView classHours = (TextView) result.findViewById(R.id.classHoursText);

        String codeName = getArguments().getString(KEY_CODE, "") + " " + getArguments().getString(KEY_NAME, "");
        String day = getArguments().getString(KEY_DAY, "");
        String hours = getArguments().getString(KEY_HOUR, "");

        className.setText(codeName);
        classDay.setText(day);
        classHours.setText(hours);

        Button studentsButton = result.findViewById(R.id.classStudentsButton);
        studentsButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                accessClassStudents();
            }
        });

        return result;
    }

    private void accessClassStudents() {
        Toast.makeText(getContext(), "Liste des eleves du cours", Toast.LENGTH_LONG).show();

        ClassSearchFragment.currentClass = getArguments().getString(KEY_CODE, "");
        ClassSearchFragment.currentSchedule = getArguments().getString(KEY_DAY, "")
                + getArguments().getString(KEY_HOUR, "");

        ((MainActivity) getActivity()).changeFragment(MainActivity.FRAGMENT.CLASS_SEARCH);
    }
}
