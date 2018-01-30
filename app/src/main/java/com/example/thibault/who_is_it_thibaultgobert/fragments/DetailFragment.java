package com.example.thibault.who_is_it_thibaultgobert.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.thibault.who_is_it_thibaultgobert.R;
import com.example.thibault.who_is_it_thibaultgobert.activities.MainActivity;
import com.example.thibault.who_is_it_thibaultgobert.models.SimpsonCharacter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Thibault on 25/01/2018.
 */

public class DetailFragment extends Fragment {

    @BindView(R.id.txtName)
    TextView txtName;

    @BindView(R.id.txtDescription)
    TextView txtDescription;

    @Nullable
    @BindView(R.id.btnBack)
    Button btnBack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void initData(){
        SimpsonCharacter character = (SimpsonCharacter) getArguments().get("character");
        txtName.setText(character.getName());
        txtDescription.setText(character.getDescription());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initializeListeners();
    }

    public void initializeListeners() {
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).showRecyclerView();
                }
            });
        }
    }



}
