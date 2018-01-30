package com.example.thibault.who_is_it_thibaultgobert.models;

import com.example.thibault.who_is_it_thibaultgobert.R;
import com.example.thibault.who_is_it_thibaultgobert.adapters.SimpsonAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Thibault on 25/01/2018.
 */

public class GameBoard implements Serializable{
    private SimpsonCharacter secretCharacter;
    private List<SimpsonCharacter> characters;

    public GameBoard(List<SimpsonCharacter> characters){
        this.characters = characters;
        Random r = new Random();
        r.nextInt();
        if(characters.size() > 0){
            secretCharacter= characters.get(r.nextInt(characters.size()-1));
        }
    }

    public SimpsonCharacter getSecretCharacterImg(){
        return this.secretCharacter;
    }

    public List<SimpsonCharacter> getCharacters(){
        return this.characters;
    }




}
