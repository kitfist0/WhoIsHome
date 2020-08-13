package app.athome.core.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import app.athome.core.database.CoreDatabase
import app.athome.core.database.dao.PlaceDao
import app.athome.core.database.dao.RecipientDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule {

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CoreDatabase {
        return Room.databaseBuilder(app, CoreDatabase::class.java, "core_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun providePlaceDao(db: CoreDatabase): PlaceDao {
        return db.placeDao()
    }

    @Provides
    @Singleton
    fun provideRecipientDao(db: CoreDatabase): RecipientDao {
        return db.recipientDao()
    }
}