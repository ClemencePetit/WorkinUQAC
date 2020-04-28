package com.example.workinuqac;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class EditFragment extends Fragment {


    //id fragment : 3
    public static EditFragment newInstance() {
        EditFragment EF = new EditFragment();
        return EF;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_profile_layout, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Button buttonValidate = (Button) view.findViewById(R.id.validateEditButton);
        buttonValidate.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                validateEdit();
            }
        });
        Button buttonCancel = (Button) view.findViewById(R.id.cancelEditButton);
        buttonCancel.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                cancelEdit();
            }
        });

        LinearLayout nameLayout=getView().findViewById(R.id.nameLayout);
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.information_edit_profile_layout,null);
        rowView.setTag("nameLayout");
        Button editNameButton = rowView.findViewById(R.id.infomationEditButton);
        editNameButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToEdit(v);
            }
        });
        nameLayout.addView(rowView);



    }

    private void goToEdit(View v){
        Toast.makeText(getContext(), "editer cette information", Toast.LENGTH_SHORT).show();
        String parentTag=((View)v.getParent()).getTag().toString();
        LinearLayout parentlayout=getView().findViewWithTag(parentTag);
        parentlayout.removeAllViews();
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.editing_edit_profile_layout,null);
        rowView.setTag(parentTag);
        Button validateButton = rowView.findViewById(R.id.editingvalidateButton);
        validateButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                validateInformation();
                BackInformation(v);
            }
        });
        Button cancelButton = rowView.findViewById(R.id.editingcancelButton);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                cancelInformation();
                BackInformation(v);
            }
        });
        parentlayout.addView(rowView);
    }

    private void BackInformation(View v){
        Toast.makeText(getContext(), "back information", Toast.LENGTH_SHORT).show();
        String parentTag=((View)v.getParent()).getTag().toString();
        LinearLayout parentlayout=getView().findViewWithTag(parentTag);
        parentlayout.removeAllViews();
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.information_edit_profile_layout,null);
        rowView.setTag(parentTag);
        Button editNameButton = rowView.findViewById(R.id.infomationEditButton);
        editNameButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToEdit(v);
            }
        });
        parentlayout.addView(rowView);

    }

    private void cancelInformation(){

    }

    private void validateInformation(){

    }

    private void validateEdit(){
        //TODO ajouter enregistrement des modifications dans la BDD
        ((MainActivity)getActivity()).changeFragment(0);
    }
    private void cancelEdit(){
        ((MainActivity)getActivity()).changeFragment(0);
    }

}
