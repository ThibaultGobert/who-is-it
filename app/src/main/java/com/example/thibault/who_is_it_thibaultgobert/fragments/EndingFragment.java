package com.example.thibault.who_is_it_thibaultgobert.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thibault.who_is_it_thibaultgobert.MemoryLeakApplication;
import com.example.thibault.who_is_it_thibaultgobert.R;
import com.example.thibault.who_is_it_thibaultgobert.activities.MainActivity;
import com.example.thibault.who_is_it_thibaultgobert.models.Game;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Thibault on 26/01/2018.
 */

public class EndingFragment extends Fragment {

    @BindView(R.id.txtNameEnding)
    TextView txtNameEnding;
    @BindView(R.id.imgCharacterEnding)
    ImageView imgCharacterEnding;
    @BindView(R.id.txtRoundsEnding)
    TextView txtRoundsEnding;

    @BindView(R.id.btnMainMenu)
    Button btnMainMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ending, container, false);
        ButterKnife.bind(this, view);

       initializeData();

        initializeListeners();
        return view;
    }

    private void initializeData(){
        Game game = (Game) getArguments().get("game");
        if(game.IsGuessedCorrect()){
            txtRoundsEnding.setText("Proficiat, je bent gewonnen!\nJe had " + game.getRound()+ " ronde(s) nodig.");
        }else{
            txtRoundsEnding.setText("Jammer, je bent verloren...");
        }
        txtNameEnding.setText("Dit was het character dat je moest raden: ");
        if(game.isPlayer1Move()){

            Picasso.with(imgCharacterEnding.getContext()).load("file://"+game.getGameBoard2().getSecretCharacterImg().getImagePath()).resize(300, 300).centerInside().into(imgCharacterEnding);
        }else{
            Picasso.with(imgCharacterEnding.getContext()).load("file://"+game.getGameBoard1().getSecretCharacterImg().getImagePath()).resize(300, 300).centerInside().into(imgCharacterEnding);

        }
    }

    private void initializeListeners(){
        btnMainMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity) getActivity()).showMenu();
            }
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        RefWatcher refWatcher = MemoryLeakApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
