package app.athome.core.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import androidx.work.Configuration
import app.athome.core.R
import app.athome.core.database.CoreDatabase
import app.athome.core.database.dao.LocationDao
import app.athome.core.database.dao.PlaceDao
import app.athome.core.database.dao.RecipientDao
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule {

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(application)

    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun provideCredential(app: Application): GoogleAccountCredential =
        GoogleAccountCredential.usingOAuth2(app, listOf(GmailScopes.GMAIL_SEND))
            .apply {
                backOff = ExponentialBackOff()
                selectedAccount = GoogleSignIn.getLastSignedInAccount(app)?.account
            }

    @Provides
    fun provideGmail(app: Application, credential: GoogleAccountCredential): Gmail =
        Gmail.Builder(NetHttpTransport(), GsonFactory(), credential)
            .setApplicationName(app.getString(R.string.app_name))
            .build()

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CoreDatabase =
        Room.databaseBuilder(app, CoreDatabase::class.java, "core_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Provides
    @Singleton
    fun providePlaceDao(db: CoreDatabase): PlaceDao = db.placeDao()

    @Provides
    @Singleton
    fun provideRecipientDao(db: CoreDatabase): RecipientDao = db.recipientDao()

    @Provides
    @Singleton
    fun provideLocationDao(db: CoreDatabase): LocationDao = db.locationDao()

    @Provides
    @Singleton
    fun provideWorkManagerConfiguration(coreWorkerFactory: CoreWorkerFactory): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(coreWorkerFactory)
            .build()
}
