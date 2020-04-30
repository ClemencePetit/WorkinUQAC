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

        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout nameLayout=getView().findViewById(R.id.nameLayout);
        final View nameView = inflater.inflate(R.layout.information_edit_profile_layout,null);
        nameView.setTag("nameLayout");
        Button editButton = nameView.findViewById(R.id.infomationEditButton);
        editButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToEdit(v);
            }
        });
        nameLayout.addView(nameView);

        LinearLayout mailLayout=getView().findViewById(R.id.mailLayout);
        final View mailView = inflater.inflate(R.layout.information_edit_profile_layout,null);
        mailView.setTag("mailLayout");
        editButton = mailView.findViewById(R.id.infomationEditButton);
        editButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToEdit(v);
            }
        });
        mailLayout.addView(mailView);

        LinearLayout statusLayout=getView().findViewById(R.id.statusLayout);
        final View statusView = inflater.inflate(R.layout.information_edit_profile_layout,null);
        statusView.setTag("statusLayout");
        editButton = statusView.findViewById(R.id.infomationEditButton);
        editButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToEdit(v);
            }
        });
        statusLayout.addView(statusView);

        LinearLayout mdpLayout=getView().findViewById(R.id.mdpLayout);
        final View mdpView = inflater.inflate(R.layout.mdp_info_edit_profile_layout,null);
        mdpView.setTag("mdpLayout");
        editButton = mdpView.findViewById(R.id.infomationEditButton);
        editButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToMdpEdit(v);
            }
        });
        mdpLayout.addView(mdpView);



    }

    private void goToEdit(View v){
        Toast.makeText(getContext(), "editer cette information "+v.getTag()+" "+getId(), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getContext(), parentlayout.getId()+" id parent", Toast.LENGTH_SHORT).show();
    }
    private void goToMdpEdit(View v){
        Toast.makeText(getContext(), "editer cette information "+v.getTag()+" "+getId(), Toast.LENGTH_SHORT).show();
        String parentTag=((View)v.getParent()).getTag().toString();
        LinearLayout parentlayout=getView().findViewWithTag(parentTag);
        parentlayout.removeAllViews();
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.mdp_editing_edit_profile_layout,null);
        rowView.setTag(parentTag);
        Button validateButton = rowView.findViewById(R.id.editingvalidateButton);
        validateButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(checkMdp()){
                    validateInformation();
                    mdpBackInformation(v);
                }

            }
        });
        Button cancelButton = rowView.findViewById(R.id.editingcancelButton);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                cancelInformation();
                mdpBackInformation(v);
            }
        });
        parentlayout.addView(rowView);
        Toast.makeText(getContext(), parentlayout.getId()+" id parent", Toast.LENGTH_SHORT).show();
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
    private void mdpBackInformation(View v){
        //Toast.makeText(getContext(), "back information", Toast.LENGTH_SHORT).show();
        String parentTag=((View)((View)v.getParent()).getParent()).getTag().toString();
        Toast.makeText(getContext(), parentTag, Toast.LENGTH_SHORT).show();
        LinearLayout parentlayout=getView().findViewWithTag(parentTag);
        parentlayout.removeAllViews();
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.mdp_info_edit_profile_layout,null);
        rowView.setTag(parentTag);
        Button editNameButton = rowView.findViewById(R.id.infomationEditButton);
        editNameButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToMdpEdit(v);
            }
        });
        parentlayout.addView(rowView);

    }

    private boolean checkMdp(){
        return false;
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
