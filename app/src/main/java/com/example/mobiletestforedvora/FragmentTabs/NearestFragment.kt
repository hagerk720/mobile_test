package com.example.mobiletestforedvora.FragmentTabs

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletestforedvora.CustomAdapter
import com.example.mobiletestforedvora.R
import com.example.mobiletestforedvora.Ride
import com.example.mobiletestforedvora.User
import com.example.mobiletestforedvora.databinding.FragmentMainBinding
import com.example.mobiletestforedvora.databinding.FragmentNearestBinding
import org.json.JSONObject
import java.io.IOException
import kotlin.math.absoluteValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NearestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NearestFragment : Fragment() {

    private lateinit var adapter: CustomAdapter
    private var _binding: FragmentNearestBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNearestBinding.inflate(inflater, container, false)
        val root = binding.root
        // Inflate the layout for this fragment
        val recycler : RecyclerView = binding.nearestRidesRecycleView
        adapter = CustomAdapter()
        var user_code = getJsonDataFromAsset_User(context, "data.json").stationCode
        adapter.replaceItems(arrangeRidesList(user_code,getJsonDataFromAsset_Rides(context ,"data.json")) )
        val layoutManager = LinearLayoutManager(context)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter



        return root

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NearestFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
    fun getJsonDataFromAsset_User(context: Context?, fileName: String): User {
        var jsonString: String? = null
        try {
            jsonString = context?.assets?.open(fileName)?.bufferedReader().use { it?.readText() }

            print(jsonString)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

        var jsonArr = JSONObject(jsonString)
        val user = jsonArr.getJSONObject("user")

        return User(
            name = user.getString("name"),
            profileKey = user.getString("profile_key"),
            stationCode = user.getInt("station_code")
        )
    }
    fun getJsonDataFromAsset_Rides(context: Context?, fileName: String): ArrayList<Ride> {
        var jsonString: String? = null
        val Rides: ArrayList<Ride> = java.util.ArrayList()

        try {
            jsonString = getContext()?.getAssets()?.open(fileName)?.bufferedReader().use { it?.readText() }

        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

        var jsonArr = JSONObject(jsonString)
        val rides = jsonArr.getJSONArray("Ride")
        for (i in 0..rides.length() - 1) {
            var ride = rides.getJSONObject(i)

            Rides.add(
                Ride(
                    id = ride.getInt("id"),
                    originStationCode = ride.getInt("origin_station_code"),
                    destinationStationCode = ride.getInt("destination_station_code"),
                    stationPath = stationPath(ride),
                    date = ride.getInt("date"),
                    mapUrl = ride.getString("map_url"),
                    state = ride.getString("state"),
                    city = ride.getString("city")
                )
            )

        }


        return Rides
    }
    fun stationPath(ride :JSONObject): ArrayList<Int>{
        var strStation : String = ""
        strStation =  ride.getString("station_path").replace("[" ,  "")
        strStation = strStation.replace("]" ,"").trim()
        var S: String = strStation
        var str = S.split(",")
        Log.d("s" , str.size.toString())
        var al: ArrayList<Int> = ArrayList(str.size)
        for (j in 0..str.size-1) {
            if (str[j] == ""){
                continue
            }
            else{
                al.add(Integer.parseInt(str[j]))
            }
        }
        return al
    }
    fun arrangeRidesList(user_code : Int? , Rides :ArrayList<Ride>):ArrayList<Ride>{
        var index :Int = 0
        var minDistance  : Int
        var simi_arranged_list :ArrayList<Ride> = ArrayList()
        var distanceArr : ArrayList<Int> = ArrayList()
        var indexArr : ArrayList<Int> = ArrayList()
        var arranged_list :ArrayList<Ride> = ArrayList()

        for (i in 0..Rides.size-1){
            var ride = Rides[i]
            var distance : Int = ride.stationPath.maxOrNull()!!
            for (j in 0..ride.stationPath.size-1){
                    if (user_code != null) {
                        if((user_code - ride.stationPath.get(j) ) .absoluteValue < distance ){
                            distance  = (user_code - ride.stationPath.get(j)).absoluteValue
                        }
                    }

            }
            distanceArr.add(distance)

            Log.d("distance"  , indexArr.toString())
            arranged_list.add(Ride(ride.id ,
                ride.originStationCode ,
                ride.stationPath ,
                distance ,
                ride.date ,
                ride.mapUrl ,
                ride.state ,
                ride.city))

        }
        var minDis : Int = distanceArr.minOrNull()!!

        return arranged_list
    }
}