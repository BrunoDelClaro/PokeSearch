package com.example.pokemon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FragPokemon extends Fragment {
    private View v;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout para este fragmento
        // Recupera uma "View v" para acessar as propriedades e métodos do fragmento
        v = inflater.inflate(R.layout.fragmentpokemon, container, false);


        return v;
    }

}
