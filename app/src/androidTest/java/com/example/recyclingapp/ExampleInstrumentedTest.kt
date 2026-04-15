package com.example.recyclingapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recyclingapp.fragments.LoginFragment

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

/**
 * Instrumented test, which will execute on an Android device.
 *
 * RUN ON REAL ANDROID DEVICES/AN EMULATOR!
 *
 * Mainly test UI activities and navigation!
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.recyclingapp", appContext.packageName)
    }

    /**
     * Tests that logging in without a username works...
     */
    @Test
    fun skipLoginTest() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.skipButton)).perform(click())    //Click the skip button on the login screen.
        onView(withId(R.id.home_fragment)).check(matches(isDisplayed()))    //Ensure we are on the home screen now.
        onView(withId(R.id.home_fragment))
            .check(matches(hasDescendant(withText("Login to see User Stats")))) //Ensure right text is shown.
    }
}