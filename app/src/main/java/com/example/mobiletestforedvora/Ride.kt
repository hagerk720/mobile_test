package com.example.mobiletestforedvora

import com.google.gson.annotations.SerializedName


data class Ride(

    @SerializedName("id"                       ) var id: Int?           = null,
    @SerializedName("origin_station_code"      ) var originStationCode: Int?           = null,
    @SerializedName("station_path"             ) var stationPath: ArrayList<Int> = arrayListOf(),
    @SerializedName("destination_station_code" ) var destinationStationCode: Int?           = null,
    @SerializedName("date"                     ) var date: Int?           = null,
    @SerializedName("map_url"                  ) var mapUrl: String?        = null,
    @SerializedName("state"                    ) var state: String?        = null,
    @SerializedName("city"                     ) var city: String?        = null

)