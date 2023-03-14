package com.yazaki_groupcom.app.ui.processManage

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yazaki_groupcom.app.Config
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.Tools
import com.yazaki_groupcom.app.databinding.AdapterProcessTitleBinding
import com.yazaki_groupcom.app.enum.ShareKey


class ProcessTitleAdapter(val context: Context) : RecyclerView.Adapter<ProcessTitleAdapter.ViewHolder>() {
    val TAG: String = "ProcessTitleAdapter"


    var listTitle = ArrayList<String>()
    var selectIndex = -1

    fun notifyDataSetAdd(title: String) {
        this.listTitle.add(title)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: AdapterProcessTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvEquipment: TextView = binding.tvEquipment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterProcessTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return listTitle.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvEquipment.text = listTitle[position]
        if (listTitle.size == 1){
            holder.tvEquipment.setBackgroundResource(R.drawable.ic_round_button_orange)
            holder.tvEquipment.setTextColor(Color.WHITE)
            Tools.sharedPrePut(ShareKey.LastSelectedProcessName.key, holder.tvEquipment.text.toString())
        }
        holder.tvEquipment.setOnClickListener {
            holder.tvEquipment.setBackgroundResource(R.drawable.bg_layout)
            holder.tvEquipment.setTextColor(Color.BLACK)
            listener.onClick(position)
            selectIndex = position
            Tools.sharedPrePut(ShareKey.LastSelectedProcessName.key,holder.tvEquipment.text.toString())

        }
    }

    //listen
    private lateinit var listener: OnAdapterListener

    interface OnAdapterListener {
        fun onClick(index: Int)
    }

    fun setOnAdapterListener(adapterListener: OnAdapterListener) {
        this.listener = adapterListener
    }
}