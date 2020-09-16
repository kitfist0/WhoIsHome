package app.athome.main.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.athome.core.database.entity.Recipient
import app.athome.main.R
import kotlinx.android.synthetic.main.item_recipient.view.*

class RecipientAdapter(
    private val recipients : List<Recipient>,
    private val onRecipientNotifyChanged: ((Recipient) -> Unit)
) : RecyclerView.Adapter<RecipientAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipient,parent,false))
    }

    override fun getItemCount(): Int {
        return recipients.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipients[position])
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(recipient: Recipient) {
            view.textUserName.text = recipient.name
            view.chipAccount.text = recipient.email
            view.switchNotify.isChecked = recipient.notify
            view.switchNotify.setOnCheckedChangeListener { _, checked ->
                recipient.notify = checked
                onRecipientNotifyChanged.invoke(recipient)
            }
        }
    }
}
