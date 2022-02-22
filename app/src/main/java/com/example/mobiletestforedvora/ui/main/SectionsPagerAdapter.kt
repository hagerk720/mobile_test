package com.example.mobiletestforedvora.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mobiletestforedvora.FragmentTabs.NearestFragment
import com.example.mobiletestforedvora.FragmentTabs.PastFragment
import com.example.mobiletestforedvora.FragmentTabs.UpComingFragment
import com.example.mobiletestforedvora.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
    R.string.tab_text_4
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        var fragment :Fragment = NearestFragment()
        when (position) {
            0 -> fragment = NearestFragment()
            1 -> fragment = UpComingFragment()
            2 -> fragment = PastFragment()

            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 4
    }
}