package app.athome.main.ui

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.athome.core.database.entity.Place
import app.athome.core.database.entity.PlaceWithRecipients
import app.athome.core.database.entity.Recipient
import app.athome.main.R
import kotlinx.android.synthetic.main.item_place.view.*

class PlaceAdapter(
    private val onEditPlaceClick: ((Long) -> Unit),
    private val onRecipientChanged: ((Recipient) -> Unit)
) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    companion object {
        val STATE_EXPAND = intArrayOf(android.R.attr.state_checked)
        val STATE_COLLAPSE = intArrayOf(-1 * android.R.attr.state_checked.inc())
    }

    private val diffCallback = object: DiffUtil.ItemCallback<PlaceWithRecipients>() {
        override fun areItemsTheSame(
            oldItem: PlaceWithRecipients,
            newItem: PlaceWithRecipients
        ): Boolean {
            return oldItem.place.id == newItem.place.id
        }
        override fun areContentsTheSame(
            oldItem: PlaceWithRecipients,
            newItem: PlaceWithRecipients
        ): Boolean {
            return oldItem.place.lat == newItem.place.lat && oldItem.place.lon == newItem.place.lon
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    private val sparseArray = SparseBooleanArray()
    private val viewPool = RecyclerView.RecycledViewPool()

    fun submitList(list: List<PlaceWithRecipients>?){
        differ.submitList(list)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_place, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ViewHolder (private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(placeWithRecipients: PlaceWithRecipients) {
            view.textItemTitle.text = placeWithRecipients.place.title
            val recipientsNum = placeWithRecipients.recipients.size
            if (recipientsNum == 1) {
                view.textItemSubTitle.setText(R.string.recipient)
            } else {
                view.textItemSubTitle.text = String
                    .format(view.context.getString(R.string.recipients), recipientsNum)
            }
            view.cardItem.setOnClickListener {
                expandMoreLess(view, placeWithRecipients.place, placeWithRecipients.recipients)
            }
        }
    }

    private fun expandMoreLess(view: View, place: Place, recipients: List<Recipient>) {
        val key = place.id.toInt()
        val alreadyExpanded = sparseArray.get(key, false)

        if (alreadyExpanded) {
            sparseArray.delete(key)
            view.imageExpand.setImageState(STATE_COLLAPSE, true)
            view.layoutWithRecycler.visibility = View.GONE
        } else {
            sparseArray.put(key, true)
            view.imageExpand.setImageState(STATE_EXPAND, true)
            view.layoutWithRecycler.visibility = View.VISIBLE

            view.recycler.apply {
                adapter = RecipientAdapter(recipients, onRecipientChanged)
                setRecycledViewPool(viewPool)
            }
            view.buttonEdit.setOnClickListener {
                onEditPlaceClick(place.id)
            }
        }
    }
}