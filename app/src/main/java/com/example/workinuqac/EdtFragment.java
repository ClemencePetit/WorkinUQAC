package com.example.workinuqac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class EdtFragment extends Fragment {

        // 1 - Create keys for our Bundle
        private static final String KEY_NAME="name";
        private static final String KEY_DAY="day";
        private static final String KEY_HOUR="hour";


        public EdtFragment() { }


        // 2 - Method that will create a new instance of PageFragment, and add data to its bundle.
        public static EdtFragment newInstance(String name, String day, String hour) {

            // 2.1 Create new fragment
            EdtFragment EF = new EdtFragment();

            // 2.2 Create bundle and add it some data
            Bundle args = new Bundle();
            args.putString(KEY_NAME, name);
            args.putString(KEY_DAY, day);
            args.putString(KEY_HOUR, hour);
            EF.setArguments(args);

            return(EF);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View result = inflater.inflate(R.layout.edt_layout, container, false);

            TextView className= (TextView) result.findViewById(R.id.classNameText);
            TextView classDay= (TextView) result.findViewById(R.id.classDayText);
            TextView classHours= (TextView) result.findViewById(R.id.classHoursText);

            String name= getArguments().getString(KEY_NAME,"");
            String day= getArguments().getString(KEY_DAY,"");
            String hours= getArguments().getString(KEY_HOUR,"");

            className.setText(name);
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

        public void accessClassStudents(){
            Toast.makeText(getContext(), "Liste des eleves du cours",Toast.LENGTH_LONG).show();

        }


}
