package com.yazaki_groupcom.app.test.testCuttingWork

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yazaki_groupcom.app.databinding.AdapterCuttingWorkBinding

//运用在TestCuttingWorkActivity的
class CuttingWorkAdapter(
    var list: ArrayList<CuttingWorkData>,
) : RecyclerView.Adapter<CuttingWorkAdapter.ViewHolder>() {

    inner class ViewHolder(binding: AdapterCuttingWorkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val location: TextView = binding.location
        val ll: LinearLayout = binding.ll
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterCuttingWorkBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.location.text = list[position].location
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun notifyDataSetChanged(list: ArrayList<CuttingWorkData>) {
        this.list = list
        notifyDataSetChanged()
    }
}