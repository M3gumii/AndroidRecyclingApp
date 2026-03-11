package com.example.recyclingapp.dataClasses.database

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclingapp.R

class SearchAdapter (private var items: List<Package>,    //List of names for the button!
                     private val onClick: (Package) -> Unit
) : RecyclerView.Adapter<SearchedItemsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchedItemsViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: SearchedItemsViewHolder, position: Int) {
        val item = items[position]
        holder.button.text = item.name
        holder.button.setOnClickListener { onClick(item) }
    }

    fun updateItems(newItems: List<Package>){
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}

class SearchedItemsViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    inflater.inflate(R.layout.single_item_button, parent, false)
) {
    val button: Button = itemView.findViewById(R.id.item_to_select)
}