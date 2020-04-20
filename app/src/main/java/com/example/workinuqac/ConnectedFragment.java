package com.example.workinuqac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ConnectedFragment extends Fragment {


    public static ConnectedFragment newInstance() {
        ConnectedFragment CF = new ConnectedFragment();
        return CF;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_layout, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button buttonEdt = (Button) view.findViewById(R.id.edtButton);
        buttonEdt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                accessEdt();
            }
        });
        Button buttonpicture = (Button) view.findViewById(R.id.pictureButton);
        buttonpicture.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                changePicture();
            }
        });

    }

    private void accessEdt(){
        Toast.makeText(getContext(), "AccessEDT", Toast.LENGTH_SHORT).show();

    }
    private void changePicture(){
        Toast.makeText(getContext(), "change Picture", Toast.LENGTH_SHORT).show();

    }
}
