package com.example.workinuqac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class InscriptionFragment extends Fragment {

    //id fragment : 2
    public static InscriptionFragment newInstance() {
        InscriptionFragment IF = new InscriptionFragment();
        return IF;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inscription_layout, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button buttonCreate = (Button) view.findViewById(R.id.inscriptionButton);
        buttonCreate.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                createProfil();
            }
        });
    }

    private void createProfil(){
        //verifications
        View v=getView();
        EditText nameTxt = v.findViewById(R.id.editNameText);
        if(!nameTxt.getText().toString().isEmpty())
        {
            EditText mailTxt = v.findViewById(R.id.editMailText);
            if(!mailTxt.getText().toString().isEmpty())
            {
                EditText mdpTxt = v.findViewById(R.id.editMdpText);
                if(!mdpTxt.getText().toString().isEmpty())
                {
                    EditText mdpConfirmTxt = v.findViewById(R.id.editMdpConfirmText);
                    if(!mdpConfirmTxt.getText().toString().isEmpty())
                    {
                        if(mdpTxt.getText().toString().equals(mdpConfirmTxt.getText().toString()))
                        {
                            Toast.makeText(getContext(), "Profil créé", Toast.LENGTH_SHORT).show();
                            ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.USER_PROFILE);
                            //TODO création row dans BDD + transmettre valeurs au profil
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
            }
            else
            {
                Toast.makeText(getContext(), "Remplis ton mail", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getContext(), "Remplis ton nom", Toast.LENGTH_SHORT).show();
        }



    }

}
