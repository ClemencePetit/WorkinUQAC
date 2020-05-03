package com.example.workinuqac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogFragmentAuthentification extends DialogFragment {

    private Button ValidateButton;
    private Button CancelButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_authentification, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ValidateButton = (Button) view.findViewById(R.id.DialogValidateButton);
        CancelButton = (Button) view.findViewById(R.id.DialogCancelButton);

        ValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo : Comparer le mot de passe dans la BDD par rapport au nom entré
                //Todo : mettre le bon id user : pour le moment, juste pour tester avec une valeur existante
                ((MainActivity)getActivity()).idUser="BOUL26619706";
                ((MainActivity)getActivity()).connection();
                ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.USER_PROFILE);

                dismiss();
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ((MainActivity)getActivity()).changeFragment(MainActivity.FRAGMENT.LOGIN);
            }
        });
    }
}
