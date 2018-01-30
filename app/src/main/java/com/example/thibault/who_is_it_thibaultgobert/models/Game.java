package com.example.thibault.who_is_it_thibaultgobert.models;

import com.example.thibault.who_is_it_thibaultgobert.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Thibault on 25/01/2018.
 */

public class Game implements Serializable{
    public static List<SimpsonCharacter> characters = generateData();

    private GameBoard gameBoard1;
    private GameBoard gameBoard2;
    private boolean player1Move;
    private boolean hasGuessed;
    private int round;
    private boolean guessedCorrect;
    private boolean onGoing;


    public Game(){
        gameBoard1 = new GameBoard(new ArrayList<SimpsonCharacter>());
        gameBoard2 = new GameBoard(new ArrayList<SimpsonCharacter>());
        player1Move = true;
        round = 1;
    }

    public void setCharacters(List<SimpsonCharacter> characters) {
        gameBoard1 = new GameBoard(characters);
        List<SimpsonCharacter> characters1 = new ArrayList<>();
        characters1.addAll(characters);
        gameBoard2 = new GameBoard(characters1);
    }

    public boolean isOnGoing() {
        return onGoing;
    }

    public void setOnGoing(boolean onGoing) {
        this.onGoing = onGoing;
    }

    public boolean IsGuessedCorrect(){
        return guessedCorrect;
    }

    public void setGuessedCorrect(boolean guessedCorrect){
        this.guessedCorrect = guessedCorrect;
    }

    public boolean guess(String name){
        boolean result;
        if(player1Move){
            result = gameBoard2.getSecretCharacterImg().getName() == name;
            hasGuessed = true;
        }else{
            result =  gameBoard1.getSecretCharacterImg().getName() == name;
            hasGuessed = true;
        }
        return result;
    }

    public void nextPlayerMove(){
        if(player1Move == false){
            round++;
        }
        player1Move = !player1Move;

    }

    private static List<SimpsonCharacter> generateData(){
        List<SimpsonCharacter> characters = new ArrayList<>();

        SimpsonCharacter homer = new SimpsonCharacter(R.drawable.homer, "Homer Simpson", "Nearly bald.\nWeighs more than average.\nWears a white shirt.\nLoves food and beer.");

        SimpsonCharacter marge = new SimpsonCharacter(R.drawable.marge, "Marge Simpson", "Very long hair.\nWears some accessories.\nHappy wife.");
        SimpsonCharacter bart = new SimpsonCharacter(R.drawable.bart, "Bart Simpson", "Mischievous & rebellious.\nEldest child.\nNickname: Cosmo");
        SimpsonCharacter lisa = new SimpsonCharacter(R.drawable.lisa, "Lisa Simpson", "Charismatic.\nVery high intelligence.\nCan play some instruments.");
        SimpsonCharacter maggie = new SimpsonCharacter(R.drawable.maggie, "Maggie Simpson", "Youngest child.\nRarely talks.\nTrips over her clothing.");
        SimpsonCharacter apu = new SimpsonCharacter(R.drawable.apu, "Apu Nahasapeemapetilon", "Speaks Hindi.\nOperator of a market.\nLittle facial and chest hair.");
        SimpsonCharacter barney = new SimpsonCharacter(R.drawable.barney, "Barney Gumble", "Drinks a lot.\nFrequent customer in the local bar.\nHas a little bit chest hair.");
        SimpsonCharacter burns = new SimpsonCharacter(R.drawable.burns, "Charles Burns", "Has a pointy nose.\nVery rich person.\nPowerful citizen.\nLikes to wear suits.");
        SimpsonCharacter milhouse = new SimpsonCharacter(R.drawable.milhouse, "Milhouse Van Houten", "Wears glasses.\nBart's best friend.\nBig nose.\nBlue hair.");
        SimpsonCharacter moe = new SimpsonCharacter(R.drawable.moe, "Moe Szyslak", "Wears a bowtie.\nOwns the local bar.\nIrritable and rude person.");
        SimpsonCharacter ned = new SimpsonCharacter(R.drawable.ned, "Ned Flanders", "Family man.\nHonest and sincere person.\nChristian.");

        characters.add(homer);
        characters.add(marge);
        characters.add(bart);
        characters.add(lisa);
        characters.add(maggie);
        characters.add(apu);
        characters.add(barney);
        characters.add(burns);
        characters.add(milhouse);
        characters.add(moe);
        characters.add(ned);
        return characters;
    }


    public int getRound(){
        return round;
    }

    public boolean getHasGuessed(){
        return hasGuessed;
    }

    public boolean isPlayer1Move(){
        return player1Move;
    }

    public GameBoard getGameBoard1(){
        return gameBoard1;
    }
    public GameBoard getGameBoard2(){
        return gameBoard2;
    }

}
