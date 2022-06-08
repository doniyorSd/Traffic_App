package com.example.trafficapp

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.trafficapp.adapters.ViewPagerAdapter
import com.example.trafficapp.databinding.FragmentHomeBinding
import com.example.trafficapp.databinding.TabItemBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val bind = FragmentHomeBinding.bind(view)
        bind.imgAdd.setOnClickListener {
            findNavController().navigate(R.id.addTrafficFragment)
        }
        val viewPagerAdapter = ViewPagerAdapter(requireActivity())
        bind.viewPager.adapter = viewPagerAdapter

        val tabList = arrayListOf("Ogohlantiruvchi", "Imtiyozli", "Ta'qiqlovchi", "Buyuruvchi")

        TabLayoutMediator(
            bind.tabLayout, bind.viewPager
        ) { tab, position ->
            val inflate =
                LayoutInflater.from(requireContext()).inflate(R.layout.tab_item, null, false)
            tab.customView = inflate
            val bind1 = TabItemBinding.bind(inflate)
            if (position == 0) {
                bind1.tv.setTextColor(Color.parseColor("#005CA1"))
                bind1.line.setCardBackgroundColor(Color.parseColor("#ffffff"))
            } else {
                bind1.tv.setTextColor(Color.parseColor("#ffffff"))
                bind1.line.setCardBackgroundColor(Color.parseColor("#005CA1"))
            }
            bind1.tv.text = tabList[position]
        }.attach()

        bind.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                val bind1 = TabItemBinding.bind(customView!!)
                bind1.tv.setTextColor(Color.parseColor("#005CA1"))
                bind1.tv.setBackgroundColor(Color.parseColor("#ffffff"))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                val bind1 = TabItemBinding.bind(customView!!)
                bind1.tv.setTextColor(Color.parseColor("#ffffff"))
                bind1.tv.setBackgroundColor(Color.parseColor("#005CA1"))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        return view
    }
}