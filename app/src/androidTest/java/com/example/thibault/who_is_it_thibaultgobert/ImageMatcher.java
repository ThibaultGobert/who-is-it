package com.example.thibault.who_is_it_thibaultgobert;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Created by Thibault on 30/01/2018.
 */


public class ImageMatcher {

    static Matcher<View> hasBeenChangedAfterClick(final Integer oldTag){
        return new BoundedMatcher<View, ImageView>(ImageView.class) {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(ImageView item) {
                return !item.getTag().equals(oldTag);
            }
        };
    }
}
