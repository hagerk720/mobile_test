package com.example.mobiletestforedvora

import com.google.gson.annotations.SerializedName


data class Data (

    @SerializedName("Ride" ) var Ride : ArrayList<Ride> = arrayListOf(),
    @SerializedName("user" ) var user : ArrayList<User> = arrayListOf()

)