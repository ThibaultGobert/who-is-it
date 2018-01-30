package com.example.thibault.who_is_it_thibaultgobert;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.thibault.who_is_it_thibaultgobert.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.espresso.contrib.RecyclerViewActions;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

import static android.support.test.espresso.Espresso.onView;

/**
 * Created by Thibault on 30/01/2018.
 */



@RunWith(AndroidJUnit4.class)
public class GameplayTest {
    @Rule
    public ActivityTestRule<MainActivity> gameActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Test
    public void showSecretCharacter(){
        onView(withId(R.id.imgCharacter)).perform(click()).check(matches(ImageMatcher.hasBeenChangedAfterClick(R.drawable.questionmark)));
    }

    @Test
    public void playRounds(){
        //2 times for 1 round because 2 players
        //round 1
        onView(withId(R.id.btnNextRound)).perform(click());
        onView(withId(R.id.btnNextRound)).perform(click());
        //round2
        onView(withId(R.id.btnNextRound)).perform(click());
        onView(withId(R.id.btnNextRound)).perform(click());
        //round3
        onView(withId(R.id.btnNextRound)).perform(click());
        onView(withId(R.id.btnNextRound)).perform(click());
        //round4
        onView(withId(R.id.txtRound)).check(matches(withText("Round 4")));
    }

    //issues with gradle sync
    @Test
    public void clickItemRecyclerView(){
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.txtName)).check(matches(isDisplayed()));
    }

    @Test
    public void guessCharacterAndNewGame(){
        gameActivityTestRule.getActivity().showEnding();

        onView(withId(R.id.btnMainMenu)).check(matches(isDisplayed()));
        onView(withId(R.id.btnMainMenu)).perform(click());
        onView(withId(R.id.btnStartGame)).check(matches(isDisplayed()));
        onView(withId(R.id.btnStartGame)).perform(click());
        onView(withId(R.id.btnNextRound)).perform(click());
        onView(withId(R.id.btnNextRound)).perform(click());
        //round2
        onView(withId(R.id.txtRound)).check(matches(withText("Round 2")));

    }








}
