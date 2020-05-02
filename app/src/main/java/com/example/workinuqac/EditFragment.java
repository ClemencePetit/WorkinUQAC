package com.example.workinuqac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditFragment extends Fragment {

    private int RESULT_LOAD_IMG = 1;

    //id fragment : 3
    public static EditFragment newInstance() {
        return new EditFragment();
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

        //Boutons pour quitter la page d'édition en validant/annulant les modifications
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

        //affichage des champs des données

        //nom
        LinearLayout nameLayout=getView().findViewById(R.id.nameLayout);
        View nameView = inflater.inflate(R.layout.information_edit_profile_layout,null);
        nameView.setTag("nameLayout");
        Button editButton = nameView.findViewById(R.id.infomationEditButton);
        editButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToEdit(v);
            }
        });
        nameLayout.addView(nameView);

        //mail
        LinearLayout mailLayout=getView().findViewById(R.id.mailLayout);
        View mailView = inflater.inflate(R.layout.information_edit_profile_layout,null);
        mailView.setTag("mailLayout");
        editButton = mailView.findViewById(R.id.infomationEditButton);
        editButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToEdit(v);
            }
        });
        mailLayout.addView(mailView);

        //status
        LinearLayout statusLayout=getView().findViewById(R.id.statusLayout);
        View statusView = inflater.inflate(R.layout.information_edit_profile_layout,null);
        statusView.setTag("statusLayout");
        editButton = statusView.findViewById(R.id.infomationEditButton);
        editButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToEdit(v);
            }
        });
        statusLayout.addView(statusView);

        //mdp
        LinearLayout mdpLayout=getView().findViewById(R.id.mdpLayout);
        View mdpView = inflater.inflate(R.layout.mdp_info_edit_profile_layout,null);
        mdpView.setTag("mdpLayout");
        editButton = mdpView.findViewById(R.id.infomationEditButton);
        editButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToMdpEdit(v);
            }
        });
        mdpLayout.addView(mdpView);

        //on click du bouton pour l'image
        editButton=getView().findViewById(R.id.pictureButton);
        editButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                choosePicture();
            }
        });

    }

    //choix de la nouvelle image
    private void choosePicture(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent,RESULT_LOAD_IMG);
    }

    //récupération de la nouvelle image + affichage
    // TODO Enlever l'affichage de l'image ?
    @Override
    public void onActivityResult(int reqCode, int resultCode,Intent data){
        super.onActivityResult(reqCode,resultCode,data);
        if (resultCode == Activity.RESULT_OK) {
            try {
                 Uri imageUri = data.getData();
                 InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                 Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                 ImageView selectedImg=getView().findViewById(R.id.pictureEditImage);
                 selectedImg.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Une erreur s'est produite",Toast.LENGTH_LONG).show();

            }

        }else {
            Toast.makeText(getContext(),"Vous n'avez pas choisi d'image", Toast.LENGTH_LONG).show();
        }
    }

    //passer de l'affichage de l'information actuelle à l'édition de cette dernière
    private void goToEdit(View v){
        String parentTag=((View)v.getParent()).getTag().toString();
        LinearLayout parentLayout=getView().findViewWithTag(parentTag);

        parentLayout.removeAllViews();

        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.editing_edit_profile_layout,null);
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

        parentLayout.addView(rowView);
    }

    //passer de l'affichage de l'information actuelle à l'édition de cette dernière pour le mdp
    private void goToMdpEdit(View v){
        String parentTag=((View)v.getParent()).getTag().toString();
        LinearLayout parentLayout=getView().findViewWithTag(parentTag);

        parentLayout.removeAllViews();

        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.mdp_editing_edit_profile_layout,null);
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

        parentLayout.addView(rowView);
    }

    //passer de l'édition de l'information actuelle à l'affichage de cette dernière
    private void BackInformation(View v){
        String parentTag=((View)v.getParent()).getTag().toString();
        LinearLayout parentLayout=getView().findViewWithTag(parentTag);

        parentLayout.removeAllViews();

        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.information_edit_profile_layout,null);
        rowView.setTag(parentTag);

        Button editNameButton = rowView.findViewById(R.id.infomationEditButton);
        editNameButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToEdit(v);
            }
        });

        parentLayout.addView(rowView);

    }
    //passer de l'édition de l'information actuelle à l'affichage de cette dernière pour le mdp
    private void mdpBackInformation(View v){
        String parentTag=((View)((View)v.getParent()).getParent()).getTag().toString();
        LinearLayout parentLayout=getView().findViewWithTag(parentTag);

        parentLayout.removeAllViews();

        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.mdp_info_edit_profile_layout,null);
        rowView.setTag(parentTag);

        Button editNameButton = rowView.findViewById(R.id.infomationEditButton);
        editNameButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToMdpEdit(v);
            }
        });

        parentLayout.addView(rowView);

    }

    //verification que le mot de passe est valide
    private boolean checkMdp(){
        boolean confirmed=false;
        EditText mdpTxt=getView().findViewById(R.id.mdpEditingText);
        if(!mdpTxt.getText().toString().isEmpty())
        {
            EditText mdpConfirmTxt = getView().findViewById(R.id.mdpEditingConfirmText);
            if(!mdpConfirmTxt.getText().toString().isEmpty())
            {
                if(mdpTxt.getText().toString().equals(mdpConfirmTxt.getText().toString()))
                {
                    confirmed=true;
                }
                else
                {
                    Toast.makeText(getContext(), "Les mots de passe ne sont pas identiques", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getContext(), "Confirme ton mot de passe", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getContext(), "Remplis ton mot de passe", Toast.LENGTH_SHORT).show();
        }
        return confirmed;
    }

    //FAIRE DE CETTE VALEUR LA NOUVELLE VALEUR DANS LA PAGE EDIT
    private void cancelInformation(){

    }

    //REVENIR A LA VALEUR PRESENTE DANS LA BDD DANS L'AFFICHAGE
    private void validateInformation(){

    }

    //QUITTER LA PAGE EDIT = SAVE LES DATAS DANS LA BDD
    private void validateEdit(){
        //TODO ajouter enregistrement des modifications dans la BDD
        ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.USER_PROFILE);
    }
    private void cancelEdit(){
        ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.USER_PROFILE);
    }

}
