package com.example.thibault.who_is_it_thibaultgobert.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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
import com.example.thibault.who_is_it_thibaultgobert.models.SimpsonCharacter;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Thibault on 25/01/2018.
 */

public class ToolbarFragment extends Fragment{

    @BindView(R.id.txtPlayer)
    TextView txtPlayer;
    @BindView(R.id.txtRound)
    TextView txtRound;
    @BindView(R.id.imgCharacter)
    ImageView imgCharacter;
    @BindView(R.id.btnNextRound)
    Button btnNextRound;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Game game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_toolbar, container, false);
        ButterKnife.bind(this, view);

        initializeData();

        initializeListeners();
        return view;
    }

    private void initializeData(){
        setGame();
    }
    public void setGame(){
        game = (Game) getArguments().get("game");
        imgCharacter.setTag(R.drawable.questionmark);

        imgCharacter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(game.isPlayer1Move() &&  imgCharacter.getTag().equals(R.drawable.questionmark)){
                    Picasso.with(imgCharacter.getContext()).load("file://"+game.getGameBoard1().getSecretCharacterImg().getImagePath()).resize(100, 100).centerInside().into(imgCharacter);

                    imgCharacter.setTag(game.getGameBoard1().getSecretCharacterImg().getImagePath());
                }else if (imgCharacter.getTag().equals(R.drawable.questionmark)){
                    Picasso.with(imgCharacter.getContext()).load("file://"+game.getGameBoard2().getSecretCharacterImg().getImagePath()).resize(100, 100).centerInside().into(imgCharacter);
                    imgCharacter.setTag(game.getGameBoard2().getSecretCharacterImg().getImagePath());
                }
            }
        });

        if(game.isPlayer1Move()){
            txtPlayer.setText("Player 1");
        }else{
            txtPlayer.setText("Player 2");
            toolbar.setBackgroundColor(Color.RED);
        }

        txtRound.setText("Round " + game.getRound());
    }

    private void initializeListeners(){
        btnNextRound.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                game.nextPlayerMove();
                ((MainActivity) getActivity()).showPlayerToolbar();
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
