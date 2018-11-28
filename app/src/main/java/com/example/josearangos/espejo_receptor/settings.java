package com.example.josearangos.espejo_receptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class settings extends Fragment {

    public Button btnsave;
    public EditText txtIP;
    public EditText txtPort;


    public settings() {

        // Required empty public constructor


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_settings, container, false);

        btnsave = (Button)view.findViewById(R.id.btnsave);
        txtIP = (EditText)view.findViewById(R.id.txtIP);
        txtPort= (EditText)view.findViewById(R.id.txtPort);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences configuraciones = getActivity().getSharedPreferences("configuraciones", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorconfiguraciones = configuraciones.edit();
                editorconfiguraciones.putString("IP", txtIP.getText().toString());
                editorconfiguraciones.putString("HOST",txtPort.getText().toString());
                editorconfiguraciones.commit();

                Toast.makeText(getActivity().getBaseContext(),"Configuración guardada con exito",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }



}
