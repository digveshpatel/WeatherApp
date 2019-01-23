package com.android.weathertask.presantation.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.weathertask.R
import com.android.weathertask.presantation.model.LocationModel

class LocationAdapter(locations: List<LocationModel>?, private val onLocationItemClickedListener : OnLocationItemSelectedListener) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

     var locations: List<LocationModel>? = locations
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_recycler_location,
                parent,
                false
            )
        ,onLocationItemClickedListener)


    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(locations?.get(position))
    }

    //this method is giving the size of the list
    override fun getItemCount() = locations?.size ?: 0

    //the class is hodling the list view
    inner class ViewHolder(
        itemView: View,
        val selectedListenerListner: OnLocationItemSelectedListener
    ) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        override fun onClick(v: View?) {
            selectedListenerListner.onLocationItemSelected(locations?.get(adapterPosition))
        }

        val tvLocationName = itemView.findViewById<TextView>(R.id.tv_location_name)

        fun bind(location: LocationModel?) {
            tvLocationName.text = location?.locationName
            tvLocationName.setOnClickListener (this)
        }
    }
}