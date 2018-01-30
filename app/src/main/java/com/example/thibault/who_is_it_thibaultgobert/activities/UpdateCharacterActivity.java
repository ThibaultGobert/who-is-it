package com.example.thibault.who_is_it_thibaultgobert.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.thibault.who_is_it_thibaultgobert.R;
import com.example.thibault.who_is_it_thibaultgobert.models.Game;
import com.example.thibault.who_is_it_thibaultgobert.models.SimpsonCharacter;
import com.example.thibault.who_is_it_thibaultgobert.persistence.CharacterContract;
import com.example.thibault.who_is_it_thibaultgobert.util.Converter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Thibault on 29/01/2018.
 */

public class UpdateCharacterActivity extends AppCompatActivity{

    @BindView(R.id.btnCameraUpdate)
    ImageButton btnCamera;
    @BindView(R.id.imgCharacterUpdate)
    ImageView imgCharacter;
    @BindView(R.id.btnCancelUpdate)
    Button btnCancel;
    @BindView(R.id.btnUpdateCharacterUpdate)
    Button btnUpdateCharacter;
    @BindView(R.id.txtDescriptionUpdate)
    EditText txtDescription;
    @BindView(R.id.txtNameUpdate)
    EditText txtName;

    private SimpsonCharacter character;
    private Bitmap image;

    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_character);
        ButterKnife.bind(this);
        character = (SimpsonCharacter) getIntent().getSerializableExtra("character");


        if(savedInstanceState ==null) {
            initData();
        }else{
            image = savedInstanceState.getParcelable("BitmapImage");
            imgCharacter.setImageBitmap(image);
        }
        initImages();
        initListeners();
    }

    private void initData(){
        txtName.setText(character.getName());
        txtDescription.setText(character.getDescription());
        Picasso.with(getApplicationContext()).load("file://"+character.getImagePath()).resize(200, 200).centerInside().into(imgCharacter);

    }

    private void initImages(){
        Picasso.with(getApplicationContext()).load(R.drawable.camera).resize(75,75).centerCrop().into(btnCamera);
    }

    private void initListeners(){
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        final View.OnFocusChangeListener fcl = new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        };
        txtName.setOnFocusChangeListener(fcl);
        txtDescription.setOnFocusChangeListener(fcl);

        btnUpdateCharacter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                ContentValues contentValues = new ContentValues();
                String description = txtDescription.getText().toString();
                String name = "";
                String imagePath = Converter.saveToInternalStorage(imgCharacter.getDrawable(),getApplicationContext());
                if (txtName.getText() != null && !txtName.getText().toString().equals(getResources().getString(R.string.name))) {
                    name = txtName.getText().toString();
                }
                if (!description.equals("") && !name.equals("")) {
                    contentValues.put(CharacterContract.CharacterEntry.COLUMN_NAME, name);
                    contentValues.put(CharacterContract.CharacterEntry.COLUMN_DESCRIPTION, description);
                    contentValues.put(CharacterContract.CharacterEntry.COLUMN_IMAGE, imagePath);
                    String selection = CharacterContract.CharacterEntry._ID + " = ?";
                    String[] selectionArgs = {String.valueOf(character.getId())};
                    int rowsUpdated = getContentResolver().update(CharacterContract.CharacterEntry.CONTENT_URI, contentValues, selection, selectionArgs);
                    setResult(RESULT_OK);
                    finish();
                }


            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, 2);
                }
            }
        });
        imgCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, 2);
                }
            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("BitmapImage", image);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image = imageBitmap;
            imgCharacter.setImageBitmap(imageBitmap);
            imgCharacter.setTag(RESULT_OK);
        }
    }
}
