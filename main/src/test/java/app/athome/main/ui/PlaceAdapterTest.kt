package app.athome.main.ui

import app.athome.core.database.entity.Place
import app.athome.core.database.entity.PlaceWithRecipients
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
class PlaceAdapterTest {

    private lateinit var placeAdapter: PlaceAdapter

    @Before
    fun setUp() {
        placeAdapter = PlaceAdapter({}, {})
    }

    @Test
    fun `submit list with null`() {
        // Given
        val list: List<PlaceWithRecipients>? = null
        // When
        placeAdapter.submitList(list)
        // Then
        Assert.assertEquals(placeAdapter.itemCount, 0)
    }

    @Test
    fun `submit list with items`() {
        // Given
        val place = Place("Google Inc., Mountain View, CA, USA", 37.419857, -122.078827)
        val list: List<PlaceWithRecipients> = List(4) { PlaceWithRecipients(place, emptyList()) }
        // When
        placeAdapter.submitList(list)
        // Then
        Assert.assertEquals(placeAdapter.itemCount, list.size)
    }
}
