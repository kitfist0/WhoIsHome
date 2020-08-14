package app.athome.main.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import app.athome.core.database.entity.Place
import app.athome.core.database.entity.PlaceWithRecipients
import app.athome.main.R
import app.athome.main.util.TestApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26], application = TestApp::class)
class MainFragmentTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val testName = TestName()

    private lateinit var app: TestApp

    @Before
    fun setUp() {
        app = ApplicationProvider.getApplicationContext()
        println("setUp test: ${testName.methodName}")
        if (testName.methodName == "should populate places in recyclerview") {
            // Prepopulate database before test
            val place = Place("Google Inc., Mountain View, CA, USA", 37.419857, -122.078827)
            runBlockingTest {
                launch { app.getCoreProvider().providePlaceRepository().insertPlace(place) }
            }
        }
    }

    @Test
    fun `should populate places in recyclerview`() {
        // https://github.com/robolectric/robolectric/issues/4496#issuecomment-652846136
        val scenario =
            launchFragmentInContainer<MainFragment>(themeResId = R.style.AppTheme)

        scenario.onFragment { fragment ->

            val places: LiveData<List<PlaceWithRecipients>> = fragment.viewModel.places

            places.observe(fragment.viewLifecycleOwner, Observer {
                places.removeObservers(fragment.viewLifecycleOwner)
                val recyclerView: RecyclerView? = fragment.view?.findViewById(R.id.recyclerView)
                Assert.assertEquals(recyclerView?.adapter?.itemCount, 1)
            })
        }
    }

    @Test
    fun `should show toast when no places`() {
        // https://github.com/robolectric/robolectric/issues/4496#issuecomment-652846136
        val scenario =
            launchFragmentInContainer<MainFragment>(themeResId = R.style.AppTheme)

        scenario.onFragment { fragment ->

            val places: LiveData<List<PlaceWithRecipients>> = fragment.viewModel.places

            places.observe(fragment.viewLifecycleOwner, Observer {
                places.removeObservers(fragment.viewLifecycleOwner)
                Assert.assertEquals(ShadowToast.getTextOfLatestToast(), app.getString(R.string.no_places))
            })
        }
    }
}