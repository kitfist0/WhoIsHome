package app.athome.main.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.athome.core.interfaces.CoreFragment
import app.athome.core.interfaces.CoreProvider
import app.athome.main.R
import app.athome.main.di.DaggerMainComponent
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment(
    private val onPlaceClicked: ((placeId: Long) -> Unit),
    private val onAddClicked: (() -> Unit)
): CoreFragment(R.layout.fragment_main) {

    companion object {
        fun newInstance(
            onPlaceClicked: ((placeId: Long) -> Unit),
            onAddClicked: (() -> Unit)
        ) = MainFragment(onPlaceClicked, onAddClicked)
    }

    override fun injectFragment(coreProvider: CoreProvider) {
        DaggerMainComponent.builder().coreProvider(coreProvider).build().inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel
    private val placeAdapter by lazy {
        PlaceAdapter(onPlaceClicked, {
            recipient -> viewModel.updatePlaceRecipient(recipient)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = placeAdapter
        fab.setOnClickListener { viewModel.insertRandomPlaceWithRecipients() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory).get(MainViewModel::class.java)

        // Observe data
        viewModel.places.observe(viewLifecycleOwner, Observer {
            placeAdapter.submitList(it)
        })
        viewModel.emptyListEvent.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), R.string.no_places, Toast.LENGTH_LONG).show()
        })
    }
}
