package com.example.mobiletestforedvora

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class CustomAdapter: RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    private var itemsList = listOf<Ride>()


    class MyViewHolder (view: View):  RecyclerView.ViewHolder(view){
        var cityName : TextView = view.findViewById(R.id.city_name_tv)
        var stateName : TextView = view.findViewById(R.id.state_name_tv)
        var rideId : TextView = view.findViewById(R.id.rideID_value_tv)
        var originStation : TextView = view.findViewById(R.id.OriginStation_value_tv)
        var stationPath: TextView = view.findViewById(R.id.stationPath_value_tv)
        var date : TextView = view.findViewById(R.id.Date_value_tv)
        var distance : TextView = view.findViewById(R.id.Distance_value_tv)
        var map :ImageView = view.findViewById(R.id.ride_img)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_component, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomAdapter.MyViewHolder, position: Int) {
        val ride = itemsList[position]
        holder.cityName.text = ride.city
        holder.stateName.text = ride.state
        if (ride.mapUrl == null ||ride.mapUrl ==  "url"){}
        else{holder.map.setImageURI(Uri.parse(ride.mapUrl)) }
        holder.rideId.text = (ride.id).toString()
        holder.originStation.text = (ride.originStationCode).toString()
        holder.stationPath.text = (ride.stationPath).toString()
        holder.date.text = getDateTime(ride.date.toString())
        holder.distance.text = (ride.destinationStationCode).toString()
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun replaceItems(items: List<Ride>) {
        this.itemsList = items
        notifyDataSetChanged()
    }
    private fun getDateTime(s: String?): String? {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy , HH:mm")
            val netDate = (s?.toLong())?.times(1000)?.let { Date(it) }
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}
