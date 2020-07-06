package app.athome.main.ui

import app.athome.core.database.entity.Place
import app.athome.core.database.entity.PlaceWithRecipients
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class PlaceAdapterTest {

    private lateinit var placeAdapter: PlaceAdapter

    @Before
    fun setUp() {
        placeAdapter = PlaceAdapter({}, {})
    }

    @Test
    fun submitListWithNull() {
        // When
        val list: List<PlaceWithRecipients>? = null
        // Given
        placeAdapter.submitList(list)
        // Then
        Assert.assertEquals(placeAdapter.itemCount, 0)
    }

    @Test
    fun submitList() {
        // When
        val list: MutableList<PlaceWithRecipients> = mutableListOf()
        for (i in 1..10) {
            val place = Place("place$i", 0.0, 0.0)
            list.add(PlaceWithRecipients(place, emptyList()))
        }
        // Given
        placeAdapter.submitList(list)
        // Then
        Assert.assertEquals(placeAdapter.itemCount, list.size)
    }
}