package app.athome.main.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.athome.core.database.entity.Place
import app.athome.core.database.entity.PlaceWithRecipients
import app.athome.core.repository.PlaceRepository
import app.athome.core.repository.RecipientRepository
import app.athome.main.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var placeRepository: PlaceRepository
    @Mock
    lateinit var recipientRepository: RecipientRepository
    @Mock
    private lateinit var placesObserver: Observer<List<PlaceWithRecipients>>
    @Mock
    private lateinit var emptyListEventObserver: Observer<Boolean>

    private lateinit var viewModel: MainViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(placeRepository, recipientRepository)
    }

    @Test
    fun places() {
        val list: MutableList<PlaceWithRecipients> = mutableListOf()
        for (i in 1..10) {
            val place = Place("place$i", 0.0, 0.0)
            list.add(PlaceWithRecipients(place, emptyList()))
        }
        val flow: Flow<List<PlaceWithRecipients>> = flow {
            emit(list)
        }

        doReturn(flow)
            .`when`(placeRepository)
            .getPlacesWithRecipients()

        testCoroutineRule.runBlockingTest {
            viewModel.places.observeForever(placesObserver)

            verify(placeRepository).getPlacesWithRecipients()

            verify(placesObserver).onChanged(list)
            viewModel.places.removeObserver(placesObserver)
        }
    }

    @Test
    fun emptyListEvent() {
        val flow: Flow<List<PlaceWithRecipients>> = flow {
            emit(emptyList())
        }

        doReturn(flow)
            .`when`(placeRepository)
            .getPlacesWithRecipients()

        testCoroutineRule.runBlockingTest {
            viewModel.places.observeForever(placesObserver)
            viewModel.emptyListEvent.observeForever(emptyListEventObserver)

            verify(placeRepository).getPlacesWithRecipients()

            verify(placesObserver).onChanged(emptyList())
            verify(emptyListEventObserver).onChanged(true)
            viewModel.places.removeObserver(placesObserver)
            viewModel.emptyListEvent.removeObserver(emptyListEventObserver)
        }
    }
}