package com.example.codenames;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

public class TestGameOperative
{
    @Rule
    public ActivityScenarioRule<SpymasterGameActivity> mActivityScenarioRule = new ActivityScenarioRule<>(SpymasterGameActivity.class);



    ViewInteraction button = onView(
            allOf(withId(R.id.button_card2), withText("OPERATIVE GUESSES CARD"),
                    withParent(withParent(withId(android.R.id.content))),
                    isDisplayed()));
        button.check(matches(isDisplayed()));

    ViewInteraction button2 = onView(
            allOf(withId(R.id.button_card18), withText("OPERATIVE GUESSES ANOTHER CARD"),
                    withParent(withParent(withId(android.R.id.content))),
                    isDisplayed()));
        button2.check(matches(isDisplayed()));

    ViewInteraction button3 = onView(
            allOf(withId(R.id.button_endturn), withText("OPERATIVE ENDS THE TURN"),
                    withParent(withParent(withId(android.R.id.content))),
                    isDisplayed()));
        button3.check(matches(isDisplayed()));



    private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position)
    {
        return new TypeSafeMatcher<View>()
        {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
