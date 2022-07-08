package br.com.hussan.recyclerview_dialog_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val dataSet: MutableList<String>, private val callback: (String, Int) -> Unit) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(view: View, callback: (String, Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        init {
            textView = view.findViewById(R.id.txt_item)
            textView.setOnLongClickListener {
                callback(textView.text.toString(), textView.tag as Int)
                true
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_item, viewGroup, false)

        return ViewHolder(view, callback)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position]
        viewHolder.textView.tag = position
    }

    override fun getItemCount() = dataSet.size

    fun addItem(item: String) {
        dataSet.add(item)
        notifyItemInserted(dataSet.size)
    }

    fun updateItem(position: Int, editTextInput: String) {
        dataSet[position] = editTextInput
        notifyItemChanged(position)
    }

    fun deleteItem(item: String) {
        val position = dataSet.indexOf(item)
        dataSet.remove(item)
        notifyItemRemoved(position)
    }
}