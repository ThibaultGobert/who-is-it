package com.example.thibault.who_is_it_thibaultgobert.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by Thibault on 28/01/2018.
 */

public class CreateCharacterActivity extends AppCompatActivity {

    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.imgCharacter)
    ImageView imgCharacter;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnCreateCharacter)
    Button btnCreateCharacter;
    @BindView(R.id.txtDescriptionCreate)
    EditText txtDescription;
    @BindView(R.id.txtNameCreate)
    EditText txtName;

    private Bitmap image;


    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        Picasso.with(getApplicationContext()).load(R.drawable.camera).resize(75,75).centerCrop().into(btnCamera);

        if(savedInstanceState ==null) {
            Picasso.with(getApplicationContext()).load(R.drawable.questionmark).resize(200, 300).centerCrop().into(imgCharacter);
        }else{
            image = savedInstanceState.getParcelable("BitmapImage");
            imgCharacter.setImageBitmap(image);
        }
        imgCharacter.setTag(R.drawable.questionmark);

        initListeners();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("BitmapImage", image);
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

        btnCreateCharacter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //delete all
                getContentResolver().delete(CharacterContract.CharacterEntry.CONTENT_URI, null, null);
                //delete from internal storage
                File[] files = getApplicationContext().getFilesDir().listFiles();
                if (files != null)
                    for (File file : files) {
                        file.delete();
                    }

                //originals
                List<SimpsonCharacter> characters = new ArrayList<>();
                SimpsonCharacter character;

                for (int i = 0; i < Game.characters.size(); i++) {
                    character = new SimpsonCharacter(Game.characters.get(i).getName(), Game.characters.get(i).getDescription());
                    character.setImagePath(Converter.saveOriginalToInternalStorage(getResources().getDrawable(Game.characters.get(i).getCharacterImage()), getApplicationContext(), Game.characters.get(i).getName()));
                    characters.add(character);
                }
                ContentValues contentValues = new ContentValues();

                for (SimpsonCharacter original : characters) {
                    contentValues = new ContentValues();
                    contentValues.put(CharacterContract.CharacterEntry.COLUMN_NAME, original.getName());
                    contentValues.put(CharacterContract.CharacterEntry.COLUMN_DESCRIPTION, original.getDescription());
                    contentValues.put(CharacterContract.CharacterEntry.COLUMN_IMAGE, original.getImagePath());
                    getContentResolver().insert(CharacterContract.CharacterEntry.CONTENT_URI, contentValues);
                }
                return true;
            }
        });


        btnCreateCharacter.setOnClickListener(new View.OnClickListener(){
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
                    Uri uri = getContentResolver().insert(CharacterContract.CharacterEntry.CONTENT_URI, contentValues);
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
