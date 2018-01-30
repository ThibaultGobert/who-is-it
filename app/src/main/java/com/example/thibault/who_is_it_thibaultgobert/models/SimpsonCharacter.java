package com.example.thibault.who_is_it_thibaultgobert.models;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by Thibault on 1/01/2018.
 */

public class SimpsonCharacter implements Serializable {
    private int characterImage;
    private String imagePath;
    private int id;
    private String name, description;

    public SimpsonCharacter(int characterImage, String name, String description ){
        this.characterImage = characterImage;
        this.name = name;
        this.description = description;
    }
    public SimpsonCharacter(String name, String description){
        this.name = name;
        this.description = description;
    }

    public SimpsonCharacter(String imagePath, String name, String description){
        this.imagePath = imagePath;
        this.name = name;
        this.description = description;
    }

    public void setImagePath(String path){
        this.imagePath = path;
    }
    public String getImagePath(){
        return this.imagePath;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=  id;
    }

    public int getCharacterImage() {
        return characterImage;
    }

    public void setCharacterImage(int characterImage) {
        this.characterImage = characterImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
