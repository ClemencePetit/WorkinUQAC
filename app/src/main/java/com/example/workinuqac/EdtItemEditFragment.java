package com.example.workinuqac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class EdtItemEditFragment extends Fragment {

        // 1 - Create keys for our Bundle
        private static final String KEY_CODE="code";
        private static final String KEY_NAME="name";
        private static final String KEY_DAY="day";
        private static final String KEY_HOUR="hour";


        public EdtItemEditFragment() { }


        // 2 - Method that will create a new instance of PageFragment, and add data to its bundle.
        static EdtItemEditFragment newInstance(String code, String name, String day, String hour) {

            // 2.1 Create new fragment
            EdtItemEditFragment EF = new EdtItemEditFragment();

            /* TODO définir arguments à transmettre pour les listes déroulantes
            // 2.2 Create bundle and add it some data
            Bundle args = new Bundle();
            args.putString(KEY_CODE, code);
            args.putString(KEY_NAME, name);
            args.putString(KEY_DAY, day);
            args.putString(KEY_HOUR, hour);
            EF.setArguments(args);
             */

            return(EF);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View result = inflater.inflate(R.layout.edt_item_edit_layout, container, false);

            Button removeButton = result.findViewById(R.id.removeClassButton);
            removeButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    removeClass();
                }
            });

            Spinner classChoice = result.findViewById(R.id.classSpinner);
            Spinner classSchedule = result.findViewById(R.id.scheduleSpinner);

            // TODO récupérer les paramètres et initialiser les 2 menus déroulants
            // Params: contenu de listes + valeur sélectionnées


            return result;
        }

        private void removeClass(){
            // TODO remove class from parent
        }
}
