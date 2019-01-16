package com.infosys.androidassignment;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.infosys.androidassignment.adapters.FactsRecyclerAdapter;
import com.infosys.androidassignment.base.AbstractTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest extends AbstractTest {

    private static final int ITEM_BELOW_THE_FOLD = 4;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRecyclerWithAdapter() throws InterruptedException {

        // Check that list adapter is set and views populated
        RecyclerView recyclerView = (RecyclerView) mActivityRule.getActivity().findViewById(R.id.facts_recycler_view);
        //One improvement would be not to rely on the real network query, but mock the response (Mockito etc...) to avoid depending on network related stuff.
        waitForCondition(() -> recyclerView != null && recyclerView.getAdapter() != null, 3000);
        assertNotNull(recyclerView);
        assertNotNull(recyclerView.getAdapter());
        assertNotSame(0, recyclerView.getAdapter().getItemCount());
    }


    @Test
    public void scrollToItemBelowFold_checkItsText() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.facts_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_BELOW_THE_FOLD, click()));

        // Match the text in an item below the fold and check that it's displayed.
        String itemElementText = mActivityRule.getActivity().getResources().getString(
                R.string.item_element_text);
        onView(withText(itemElementText)).check(matches(isDisplayed()));
    }
}

