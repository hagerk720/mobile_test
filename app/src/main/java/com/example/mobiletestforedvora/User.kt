package com.example.mobiletestforedvora

import com.google.gson.annotations.SerializedName


data class User (

    @SerializedName("station_code" ) var stationCode : Int?    = null,
    @SerializedName("name"         ) var name        : String? = null,
    @SerializedName("profile_key"  ) var profileKey  : String? = null

)