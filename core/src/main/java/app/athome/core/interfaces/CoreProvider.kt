package app.athome.core.interfaces

import android.content.SharedPreferences
import app.athome.core.repository.PlaceRepository
import app.athome.core.repository.RecipientRepository

interface CoreProvider {

    fun provideSharedPreferences(): SharedPreferences

    fun providePlaceRepository(): PlaceRepository

    fun provideRecipientRepository(): RecipientRepository
}
