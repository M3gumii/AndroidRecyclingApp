package com.example.recyclingapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recyclingapp.fragments.HomeFragment
import com.example.recyclingapp.fragments.LoginFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith


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

    @Test
    fun testOpenToLoginPage() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        scenario.onActivity { activity ->
            val fragment = activity.supportFragmentManager
                .findFragmentById(R.id.fragment_container)

            assertTrue(fragment is LoginFragment)
        }
    }

    @Test
    fun homeClickButtonTest() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        scenario.onActivity { activity ->
            val bottomNav = activity.findViewById<BottomNavigationView>(R.id.bottom_selection_options)
            bottomNav.selectedItemId = R.id.home_select_button
        }   //Select the home button

        //check that the user is sent to the home screen!
        scenario.onActivity { activity ->
            val fragment = activity.supportFragmentManager
                .findFragmentById(R.id.fragment_container)

            assertTrue(fragment is HomeFragment)
        }
    }

    @Test
    fun clickSkipLogin() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)    //Startup the app

        // User clicks the skip button
        onView(withId(R.id.skipButton))
            .perform(click())

        // Now check we're on the home page!
        scenario.onActivity { activity ->
            val fragment = activity.supportFragmentManager
                .findFragmentById(R.id.fragment_container)

            assertTrue(fragment is HomeFragment)
        }
    }

}