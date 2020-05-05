package com.example.workinuqac;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EdtEditFragment extends Fragment {

    private ArrayList<Course> tempCourses;
    private ArrayList<String> coursesBDD;
    private ArrayList<String> idCoursesBDD;
    private ArrayList<String> hoursCoursesBDD;



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
                    reloadCourses(true);
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
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        LinearLayout addingLayout=getView().findViewById(R.id.adding_layout);
        View addView = inflater.inflate(R.layout.edt_edit_add_layout,null);
        addView.setTag("adding_layout");
        Button validateButton = addView.findViewById(R.id.validateEditButton);
        validateButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                coursesAdded(v,true);
            }
        });

        Button cancelButton = addView.findViewById(R.id.cancelEditButton);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                coursesAdded(v,false);
            }
        });

        Spinner idSpinner = addView.findViewById(R.id.idCourseSpinner);
        ArrayAdapter<String> idCoursesAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, new ArrayList<String>());
        idSpinner.setAdapter(idCoursesAdapter);
        idSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String coursSelected=(String)parentView.getItemAtPosition(position);
                if(coursSelected.equals("Ajouter"))
                {
                    addIdCours();
                }
                else
                {
                    updateSpinnerHours(coursSelected);
                    stopAddIdCours();
                    stopAddHoursCours();
                }
            }
            @Override

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Spinner hoursSpinner = addView.findViewById(R.id.hourCourseSpinner);
        ArrayAdapter<String> hoursCoursesAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, new ArrayList<String>());
        hoursSpinner.setAdapter(hoursCoursesAdapter);
        hoursSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String hoursselected=(String)parentView.getItemAtPosition(position);
                if(hoursselected.equals("Ajouter"))
                {
                    addHoursCours();
                }
                else
                {

                    stopAddHoursCours();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        addingLayout.addView(addView);

        reloadSpinnerIdCourses();
    }

    private void coursesAdded(View v,boolean validated){
        if(validated){
            Spinner idSpinner = getView().findViewById(R.id.idCourseSpinner);
            Spinner hoursSpinner = getView().findViewById(R.id.hourCourseSpinner);

            String id = (String)idSpinner.getSelectedItem();
            String hour=(String)hoursSpinner.getSelectedItem();
            if(id.equals("Ajouter")){
                EditText idCourse=(EditText)getView().findViewById(R.id.idCourseEditText);
                id=idCourse.getText().toString();
            }
            if(hour.equals("Ajouter")){
                EditText hoursCourse=(EditText)getView().findViewById(R.id.hourCourseEditText);
                hour=hoursCourse.getText().toString();
            }
            tempCourses.add(new Course(id,"",hour.substring(0,2),hour.substring(2)));
            reloadCourses(false);

        }
        LinearLayout p=getView().findViewById(R.id.adding_layout);
        p.removeAllViews();

    }

    private void validate(){
        // TODO valider les changements
        ((MainActivity)getActivity()).currentUser.setCourses(tempCourses);
        ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.CONNECTED_PROFILE);
    }

    private void cancel(){
        ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.CONNECTED_PROFILE);
    }

    public void reloadCourses(boolean read){
        if(getView()!=null&&!((MainActivity) getActivity()).currentUser.coursesIsNull()) {
            ViewPager2 pager = (ViewPager2) getView().findViewById(R.id.edtViewPager);
            if(read) {
                tempCourses = ((MainActivity) getActivity()).currentUser.getCourses();
            }

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

    public void reloadSpinnerIdCourses(){
        MyBDD.updateCoursesLists(new MyBDD.OnDataReadEventListener() {
            @Override
            public void onEvent() {
                coursesBDD=MyBDD.getAllCoursesCodeWithSchedule();
                updateSpinnerId();
            }
        });
    }

    public void updateSpinnerId(){
        Spinner idSpinner = getView().findViewById(R.id.idCourseSpinner);
        ArrayAdapter<String> idCoursesAdapter = (ArrayAdapter<String>)idSpinner.getAdapter();
        idCoursesAdapter.clear();
        if(idCoursesBDD==null) {
            idCoursesBDD = new ArrayList<String>();
            hoursCoursesBDD = new ArrayList<String>();
        }
        else
        {
            idCoursesBDD.clear();
            hoursCoursesBDD.clear();
        }
        for (String course : coursesBDD) {
            String temp=course.substring(0,7);
            if(!idCoursesBDD.contains(temp)) {
                idCoursesAdapter.add(course.substring(0, 7));
            }
            idCoursesBDD.add(temp);
            hoursCoursesBDD.add(course.substring(8));
        }
        idCoursesAdapter.add("Ajouter");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateSpinnerHours(String coursSelected){
        Spinner hoursSpinner = getView().findViewById(R.id.hourCourseSpinner);
        ArrayAdapter<String> hoursCoursesAdapter = (ArrayAdapter<String>)hoursSpinner.getAdapter();
        hoursCoursesAdapter.clear();
        List<Integer> allIndexes =
                IntStream.range(0, idCoursesBDD.size()).boxed()
                        .filter(i -> idCoursesBDD.get(i).equals(coursSelected))
                        .collect(Collectors.toList());
        for(Integer i:allIndexes){
            hoursCoursesAdapter.add(MyBDD.translate(hoursCoursesBDD.get(i).substring(0,2))+" "+hoursCoursesBDD.get(i).substring(2));
        }
        hoursCoursesAdapter.add("Ajouter");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addIdCours(){
        updateSpinnerHours("");
        EditText idCourse=(EditText)getView().findViewById(R.id.idCourseEditText);
        idCourse.setEnabled(true);
        idCourse.setText("");
    }

    public void stopAddIdCours(){
        EditText idCourse=(EditText)getView().findViewById(R.id.idCourseEditText);
        idCourse.setEnabled(false);
        idCourse.setText("");
    }

    public void addHoursCours(){
        EditText hoursCourse=(EditText)getView().findViewById(R.id.hourCourseEditText);
        hoursCourse.setEnabled(true);
        hoursCourse.setText("");
    }

    public void stopAddHoursCours(){
        EditText hoursCourse=(EditText)getView().findViewById(R.id.hourCourseEditText);
        hoursCourse.setEnabled(false);
        hoursCourse.setText("");
    }

}
