package com.example.info.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.info.R
import com.example.info.model.Details
import kotlinx.android.synthetic.main.item_details.view.*

class DetailsAdapter(private val detailsList: ArrayList<Details>):
    RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {

    fun updateDetailsList(newDetailsList: List<Details>) {
        detailsList.clear()
        detailsList.addAll(newDetailsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_details, parent, false)
        return DetailsViewHolder(view)
    }

    override fun getItemCount(): Int = detailsList.size

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.view.title.text = detailsList[position].title
        holder.view.description.text = detailsList[position].description
    }

    class DetailsViewHolder(var view: View): RecyclerView.ViewHolder(view)
}