package com.example.recyclingapp.dataClasses.database

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclingapp.R

class RecentItemAdapter(
    private var items: List<PreviousSearch>,    //List of names for the button!
    private val onClick: (PreviousSearch) -> Unit
) : RecyclerView.Adapter<RecentItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecentItemViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecentItemViewHolder, position: Int) {
        val item = items[position]
        holder.button.text = item.name_of_item
        holder.button.setOnClickListener { onClick(item) }
    }

    fun updateItems(newItems: List<PreviousSearch>){
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}

class RecentItemViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    inflater.inflate(R.layout.single_item_button, parent, false)
) {
    val button: Button = itemView.findViewById(R.id.item_to_select)
}