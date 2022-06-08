package com.example.trafficapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trafficapp.adapters.RvAdapter
import com.example.trafficapp.database.TrafficDatabase
import com.example.trafficapp.databinding.FragmentViewPagerBinding
import com.example.trafficapp.entity.Traffic
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ViewPagerFragment : Fragment() {
    lateinit var adapter: RvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        val bind = FragmentViewPagerBinding.bind(view)
        val type = arguments?.getString("type")
        val trafficDatabase = TrafficDatabase.getInstance(requireContext())


        adapter = RvAdapter(object : RvAdapter.MyClick {
            override fun myItemRootClick(position: Int, traffic: Traffic) {

            }

            override fun itemRemove(traffic: Traffic, position: Int) {
                trafficDatabase.trafficDao().deleteTraffic(traffic)
            }

            override fun itemUpdate(traffic: Traffic, position: Int) {

            }

            override fun itemLiked(traffic: Traffic, position: Int) {
                trafficDatabase.trafficDao().updateTraffic(traffic)
            }
        })

        trafficDatabase.trafficDao().getAllTraffic(type!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter.submitList(it as ArrayList<Traffic>)
            }
        bind.rv.adapter = adapter

        return view
    }
}