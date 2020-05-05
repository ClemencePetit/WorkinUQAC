package com.example.workinuqac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class EdtEditItemFragment extends Fragment {

    // 1 - Create keys for our Bundle
    private static final String KEY_CODE="code";
    private static final String KEY_NAME="name";
    private static final String KEY_DAY="day";
    private static final String KEY_HOUR="hour";
    private static final String KEY_POSITION="position";

    private int muPosition;


    public EdtEditItemFragment() { }


    // 2 - Method that will create a new instance of PageFragment, and add data to its bundle.
    static EdtEditItemFragment newInstance(String code, String name, String day, String hour, int position) {

        // 2.1 Create new fragment
        EdtEditItemFragment EF = new EdtEditItemFragment();

        // 2.2 Create bundle and add it some data
        Bundle args = new Bundle();
        args.putString(KEY_CODE, code);
        args.putString(KEY_NAME, name);
        args.putString(KEY_DAY, day);
        args.putString(KEY_HOUR, hour);
        args.putInt(KEY_POSITION,position);
        EF.setArguments(args);

        return(EF);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.edt_item_edit_layout, container, false);

        TextView className = (TextView) result.findViewById(R.id.classNameText);
        TextView classDay = (TextView) result.findViewById(R.id.classDayText);
        TextView classHours = (TextView) result.findViewById(R.id.classHoursText);

        String codeName = getArguments().getString(KEY_CODE,"") + " " + getArguments().getString(KEY_NAME,"");
        String day = getArguments().getString(KEY_DAY,"");
        String hours = getArguments().getString(KEY_HOUR,"");

        muPosition=getArguments().getInt(KEY_POSITION,0);

        className.setText(codeName);
        classDay.setText(day);
        classHours.setText(hours);

        Button removeButton = result.findViewById(R.id.removeCourseButton);
        removeButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                removeCourse();
            }
        });

        return result;
    }

    private void removeCourse(){
        Toast.makeText(getContext(), "Remove ce cours",Toast.LENGTH_LONG).show();
        ((EdtEditFragment)getParentFragment()).removeCourse(muPosition);
    }

}
