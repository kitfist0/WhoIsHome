package app.athome.di.modules

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import app.athome.repo.AppRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRepository(application: Application): AppRepository {
        return AppRepository(application)
    }

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
}