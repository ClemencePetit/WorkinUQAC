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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditFragment extends Fragment {

    private int RESULT_LOAD_IMG = 1;
    private User tempUser;

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
        if(((MainActivity)getActivity()).currentUser==null) {
            ((MainActivity)getActivity()).currentUser=new User(((MainActivity)getActivity()).idUser,getContext());
        }
        tempUser = ((MainActivity) getActivity()).currentUser.clone();

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
        TextView infoText = nameView.findViewById(R.id.informationText);

        if(((MainActivity)getActivity()).currentUser.getName().isEmpty()){
            MyBDD.readUserName(((MainActivity)getActivity()).currentUser.getIdentifiant(), new MyBDD.OnDataReadEventListener() {
                @Override
                public void onEvent() {
                    ((MainActivity)getActivity()).currentUser.setName( MyBDD.getCurrentUsername());
                    reloadName();
                }
            });
            infoText.setText("Loading...");
        }
        else
        {
            infoText.setText(((MainActivity)getActivity()).currentUser.getName());
        }

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
        infoText = mailView.findViewById(R.id.informationText);

        if(((MainActivity)getActivity()).currentUser.getEmail().isEmpty()){
            MyBDD.readUserEmail(((MainActivity)getActivity()).currentUser.getIdentifiant(), new MyBDD.OnDataReadEventListener() {
                @Override
                public void onEvent() {
                    ((MainActivity)getActivity()).currentUser.setEmail( MyBDD.getCurrentEmail());
                    reloadMail();
                }
            });
            infoText.setText("Loading...");
        }
        else
        {
            infoText.setText(((MainActivity)getActivity()).currentUser.getEmail());
        }

        mailLayout.addView(mailView);

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
        ImageView photoView=getView().findViewById(R.id.pictureEditImage);

        if(((MainActivity)getActivity()).currentUser.getPhoto()==null){
            /*MyBDD.readUserName(((MainActivity)getActivity()).currentUser.getIdentifiant(), new MyBDD.OnDataReadEventListener() {
                @Override
                public void onEvent() {
                    ((MainActivity)getActivity()).currentUser.setName( MyBDD.getCurrentUsername());
                    reloadPhoto();
                }
            });*/
            photoView.setImageBitmap(((MainActivity)getActivity()).defaultProfileImage);
        }
        else
        {
            photoView.setImageBitmap(((MainActivity)getActivity()).currentUser.getPhoto());
        }

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
                 tempUser.setPhoto(selectedImage);
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

        TextView infoText=((View)v.getParent()).findViewById(R.id.informationText);
        String info = infoText.getText().toString();

        parentLayout.removeAllViews();

        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.editing_edit_profile_layout,null);
        rowView.setTag(parentTag);

        EditText editInfo = rowView.findViewById(R.id.informationText);
        editInfo.setText(info);

        Button validateButton = rowView.findViewById(R.id.editingvalidateButton);
        validateButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                BackInformation(v,true);
            }
        });

        Button cancelButton = rowView.findViewById(R.id.editingcancelButton);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                BackInformation(v,false);
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

                    mdpBackInformation(v,true);
                }

            }
        });

        Button cancelButton = rowView.findViewById(R.id.editingcancelButton);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                mdpBackInformation(v,false);
            }
        });

        parentLayout.addView(rowView);
    }

    //passer de l'édition de l'information actuelle à l'affichage de cette dernière
    private void BackInformation(View v,boolean validateInfo){
        String parentTag=((View)v.getParent()).getTag().toString();
        LinearLayout parentLayout=getView().findViewWithTag(parentTag);

        EditText editInfo = ((View)v.getParent()).findViewById(R.id.informationText);
        String info = editInfo.getText().toString();
        if(validateInfo){
            switch (parentTag){
                case "nameLayout":
                    tempUser.setName(info);
                    break;
                case "mailLayout":
                    tempUser.setEmail(info);
                    break;
                default:
                    break;
            }
            //tempUser prend la valeur
        }else{
            switch (parentTag){
                case "nameLayout":
                    info = tempUser.getName();
                    break;
                case "mailLayout":
                    info = tempUser.getEmail();
                    break;
                default:
                    break;
            }
            //on remet la valeur de temp user
        }

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

        TextView infoText=rowView.findViewById(R.id.informationText);
        infoText.setText(info);

        parentLayout.addView(rowView);

    }
    //passer de l'édition de l'information actuelle à l'affichage de cette dernière pour le mdp
    private void mdpBackInformation(View v, boolean validateinfo){
        String parentTag=((View)((View)v.getParent()).getParent()).getTag().toString();
        LinearLayout parentLayout=getView().findViewWithTag(parentTag);

        if(validateinfo){
            //on sauve le nouveau mdp
        }//sinon on garde l'ancien

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

    //QUITTER LA PAGE EDIT = SAVE LES DATAS DANS LA BDD
    private void validateEdit(){
        //TODO ajouter enregistrement des modifications dans la BDD
        ((MainActivity)getActivity()).currentUser=tempUser.clone();
        ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.USER_PROFILE);
    }
    private void cancelEdit(){
        ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.USER_PROFILE);
    }

    //TODO chercher le bon layout puis la ligne d'info
    public void reloadName(){
        View v = getView().findViewById(R.id.nameLayout);
        if(v!=null) {
            TextView nameTxt = v.findViewById(R.id.informationText);
            nameTxt.setText(((MainActivity) getActivity()).currentUser.getName());
        }
        tempUser.setName(((MainActivity)getActivity()).currentUser.getName());

    }

    public void reloadMail(){
        View v = getView().findViewById(R.id.mailLayout);
        if(v!=null) {
            TextView mailTxt = v.findViewById(R.id.informationText);
            mailTxt.setText(((MainActivity) getActivity()).currentUser.getEmail());
        }
        tempUser.setEmail(((MainActivity)getActivity()).currentUser.getEmail());
    }

    public void reloadPhoto(){
        if(getView()!=null && ((MainActivity) getActivity()).currentUser.getPhoto()!=null) {
            ImageView photo = getView().findViewById(R.id.pictureEditImage);
            photo.setImageBitmap(((MainActivity) getActivity()).currentUser.getPhoto());
            tempUser.setPhoto(((MainActivity) getActivity()).currentUser.getPhoto());
        }
    }

}
