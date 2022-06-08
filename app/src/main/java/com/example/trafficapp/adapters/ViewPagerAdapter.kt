package com.example.trafficapp.adapters

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.trafficapp.ViewPagerFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        val list = arrayListOf("Ogohlantiruvchi", "Imtiyozli", "Ta'qiqlovchi", "Buyuruvchi")
        val bundle = Bundle().apply {
            putString("type", list[position])
        }
        val viewPagerFragment = ViewPagerFragment()
        viewPagerFragment.arguments = bundle

        return viewPagerFragment
    }
}