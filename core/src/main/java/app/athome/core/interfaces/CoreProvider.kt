package app.athome.core.interfaces

import android.app.Application
import android.content.SharedPreferences
import app.athome.core.repository.PlaceRepository
import app.athome.core.repository.RecipientRepository
import com.google.firebase.auth.FirebaseAuth

interface CoreProvider {

    fun provideApplication(): Application

    fun provideFirebaseAuth(): FirebaseAuth

    fun provideSharedPreferences(): SharedPreferences

    fun providePlaceRepository(): PlaceRepository

    fun provideRecipientRepository(): RecipientRepository
}
