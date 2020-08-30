package app.athome

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFlowTest {

    @get:Rule
    var activityRule: ActivityTestRule<AppActivity> = ActivityTestRule(AppActivity::class.java)

    lateinit var navController: NavController

    @Before
    fun setUp() {
        navController = activityRule.activity.findNavController(R.id.fragmentContainerView)
    }

    @Test
    fun userLogin() {
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(ViewActions.click())
        Assert.assertEquals(navController.currentDestination?.id, R.id.mainFragment)
    }
}