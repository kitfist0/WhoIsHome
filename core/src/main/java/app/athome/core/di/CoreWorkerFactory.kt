package app.athome.core.di

import androidx.work.DelegatingWorkerFactory
import app.athome.core.repository.LocationRepository
import app.athome.core.work.LocationWorkerFactory
import app.athome.core.work.PostalWorkerFactory
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.gmail.Gmail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoreWorkerFactory @Inject constructor(
    locationRepository: LocationRepository,
    credential: GoogleAccountCredential,
    gmail: Gmail
) : DelegatingWorkerFactory() {
    init {
        addFactory(LocationWorkerFactory(locationRepository))
        addFactory(PostalWorkerFactory(credential, gmail))
    }
}
