package com.example.mobiletestforedvora.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletestforedvora.CustomAdapter
import com.example.mobiletestforedvora.Ride
import com.example.mobiletestforedvora.databinding.FragmentMainBinding
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null
   // lateinit var itemList : ArrayList<Ride>
   // lateinit var customAdapter: CustomAdapter


    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var adapter: CustomAdapter

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root
        val recycler : RecyclerView = binding.ridesRecycleView



        adapter = CustomAdapter()
        adapter.replaceItems(getJsonDataFromAsset_Rides(context ,"data.json"))
        val layoutManager = LinearLayoutManager(context)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
        //getDateTime("1644924365")?.let { Log.d("date" , it) }

        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        val station = jsonArr.getJSONArray("station_path")
        Log.d("station" , station.toString())

        for (i in 0..rides.length() - 1) {
            var ride = rides.getJSONObject(i)

            Rides.add(
                Ride(
                    id = ride.getInt("id"),
                    originStationCode = ride.getInt("origin_station_code"),
                    destinationStationCode = ride.getInt("destination_station_code"),
                    //stationPath = ride.getJSONArray("station_path"),
                    date = ride.getInt("date"),
                    mapUrl = ride.getString("map_url"),
                    state = ride.getString("state"),
                    city = ride.getString("city")
                )
            )
        }


        return Rides
    }


}