package com.example.thibault.who_is_it_thibaultgobert;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.thibault.who_is_it_thibaultgobert.activities.CreateCharacterActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by Thibault on 30/01/2018.
 */

@RunWith(AndroidJUnit4.class)
public class CreateCharacterTest {

    @Rule
    public ActivityTestRule<CreateCharacterActivity>   characterCreateActivityTestRule = new ActivityTestRule<CreateCharacterActivity>(CreateCharacterActivity.class);

    @Before
    public void onBefore(){
        onView(withId(R.id.txtDescription)).perform(typeText("TestDescription"));
    }

    @Test
    public void clickAndTypeName(){
        onView(withId(R.id.txtName)).perform(click()).perform(typeTextIntoFocusedView("TestName")).check(matches(withText("TestName")));
    }
}
