package com.project.info.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.info.R
import com.project.info.databinding.ItemDetailsBinding
import com.project.info.model.Details

class DetailsAdapter(private val detailsList: ArrayList<Details>):
    RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {

    fun updateDetailsList(newDetailsList: List<Details>) {
        detailsList.clear()
        detailsList.addAll(newDetailsList)
        detailsList.removeIf { it.title.isNullOrEmpty() &&
                it.description.isNullOrEmpty() && it.imageHref.isNullOrEmpty() }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemDetailsBinding>(inflater,
            R.layout.item_details, parent, false)
        return DetailsViewHolder(view)
    }

    override fun getItemCount(): Int = detailsList.size

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.view.details = detailsList[position]

    }

    class DetailsViewHolder(var view: ItemDetailsBinding): RecyclerView.ViewHolder(view.root)

}