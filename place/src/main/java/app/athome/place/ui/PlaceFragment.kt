package app.athome.place.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import app.athome.core.interfaces.BaseFragment
import app.athome.place.R
import app.athome.place.di.PlaceComponent
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment : BaseFragment<PlaceViewModel>(R.layout.fragment_place) {

    override fun getClassViewModel(): Class<PlaceViewModel> = PlaceViewModel::class.java

    override fun injectFragment() =
        PlaceComponent.getComponent(getCoreProvider()).inject(this)

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
