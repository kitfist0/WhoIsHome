package app.athome.place.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import app.athome.core.interfaces.CoreProvider
import app.athome.core.interfaces.BaseFragment
import app.athome.place.R
import app.athome.place.di.PlaceComponent
import app.athome.place.nav.PlaceNavigation
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment : BaseFragment<PlaceViewModel, PlaceNavigation>(R.layout.fragment_place) {

    override fun getClassViewModel(): Class<PlaceViewModel> = PlaceViewModel::class.java
    override fun getNavInterface(): Class<PlaceNavigation> = PlaceNavigation::class.java

    override fun injectFragment(coreProvider: CoreProvider) =
        PlaceComponent.getComponent(coreProvider).inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setPlaceId(arguments?.getLong("placeId"))
    }

    override fun onViewModelCreated() {
        viewModel.placeWithRecipients.observe(viewLifecycleOwner, Observer {
            toolbar.title = it?.place?.title
        })
    }
}
