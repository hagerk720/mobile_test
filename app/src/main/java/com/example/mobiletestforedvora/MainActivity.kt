package com.example.mobiletestforedvora

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.mobiletestforedvora.ui.main.SectionsPagerAdapter
import com.example.mobiletestforedvora.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.IOException
import kotlin.collections.ArrayList
import android.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        user = getJsonDataFromAsset_User(applicationContext, "data.json")
        binding.userName.text = user.name
    }

    fun getJsonDataFromAsset_User(context: Context, fileName: String): User {
        var jsonString: String? = null
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }

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
    fun stationPath(ride : JSONObject): ArrayList<Int?>{
        var S: String = ride.getString("station_path")
        var str = S.split(",")
        var al: ArrayList<Int?> = ArrayList()
        for (j in 0..str.size) {
            al[j] = Integer.parseInt(str[j])
        }
        return al
    }

}
