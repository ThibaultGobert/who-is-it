package com.example.thibault.who_is_it_thibaultgobert.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.thibault.who_is_it_thibaultgobert.R;
import com.example.thibault.who_is_it_thibaultgobert.persistence.CharacterContract;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Thibault on 26/01/2018.
 */

public class MenuActivity extends Activity {

    @BindView(R.id.btnStartGame)
    Button btnStartGame;

    @BindView(R.id.btnCreateCharacter)
    Button btnCreateCharacter;

    @BindView(R.id.btnUpdateCharacter)
    Button btnUpdateCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startmenu);

        ButterKnife.bind(this);
        initializeListeners();
    }

    private void initializeListeners() {
        final Intent intent = new Intent(this, MainActivity.class);

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });


    }

    @OnClick(R.id.btnUpdateCharacter)
    public void showUpdateCharacters() {
        Intent intent = new Intent(this, UpdateCharactersActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnCreateCharacter)
    public void showCreateCharacter() {
        Intent intent = new Intent(this, CreateCharacterActivity.class);
        startActivity(intent);

    }
}