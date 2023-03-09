package com.yazaki_groupcom.app.ui.processManage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yazaki_groupcom.app.databinding.AdapterProcessTitleBinding

//ProcessTitleAdapter
class ProcessTitleAdapter(
    var list: ArrayList<ProcessData>,
) : RecyclerView.Adapter<ProcessTitleAdapter.ViewHolder>() {

    inner class ViewHolder(binding: AdapterProcessTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvEquipment: TextView = binding.tvEquipment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterProcessTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvEquipment.text = list[position].title
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun notifyDataSetChanged(list: ArrayList<ProcessData>) {
        this.list = list
        notifyDataSetChanged()
    }
}