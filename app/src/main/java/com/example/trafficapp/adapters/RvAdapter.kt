package com.example.trafficapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trafficapp.R
import com.example.trafficapp.databinding.ItemRvBinding
import com.example.trafficapp.entity.Traffic


class RvAdapter(var myClick: MyClick) : ListAdapter<Traffic, RvAdapter.MyViewHolder>(MyDiffUtil()) {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(traffic: Traffic, position: Int) {
            val bind = ItemRvBinding.bind(itemView)

            if (traffic.like) {
                bind.iv.setImageResource(R.drawable.ic_like)
            } else {
                bind.iv.setImageResource(R.drawable.ic_unlike)
            }
            bind.ivLike.setOnClickListener {
                traffic.like = !traffic.like
                myClick.itemLiked(traffic, position)
            }
            bind.iv.setImageURI(Uri.parse(traffic.image))
            bind.tvName.text = traffic.name

            bind.root.setOnClickListener {
                myClick.myItemRootClick(position, traffic)
            }
            bind.btnRemove.setOnClickListener {
                myClick.itemRemove(traffic, position)
            }
            bind.btnEdit.setOnClickListener {
                myClick.itemUpdate(traffic, position)
            }
        }
    }

    class MyDiffUtil : DiffUtil.ItemCallback<Traffic>() {
        override fun areItemsTheSame(oldItem: Traffic, newItem: Traffic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Traffic, newItem: Traffic): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    interface MyClick {
        fun myItemRootClick(position: Int, traffic: Traffic)
        fun itemRemove(traffic: Traffic, position: Int)
        fun itemUpdate(traffic: Traffic, position: Int)
        fun itemLiked(traffic: Traffic, position: Int)
    }

}