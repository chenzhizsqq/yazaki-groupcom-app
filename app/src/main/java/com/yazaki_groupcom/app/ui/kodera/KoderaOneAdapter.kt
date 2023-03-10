package com.yazaki_groupcom.app.ui.kodera

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yazaki_groupcom.app.databinding.AdapterKoderaOneBinding


class KoderaOneAdapter : RecyclerView.Adapter<KoderaOneAdapter.ViewHolder>() {
    val TAG: String = "KoderaOneAdapter"


    private lateinit var listener: OnAdapterListener

    interface OnAdapterListener {
        fun onClick(tenantid: String)
    }


    lateinit var list: ArrayList<KoderaOneData>

    fun notifyDataSetChanged(list: ArrayList<KoderaOneData>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun notifyDataSetAdd(koderaOneData:KoderaOneData) {
        this.list.add(koderaOneData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: AdapterKoderaOneBinding) : RecyclerView.ViewHolder(binding.root) {
        val project: TextView = binding.project
        val wbs: TextView = binding.wbs
        val time: TextView = binding.time
        val memo: TextView = binding.memo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterKoderaOneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.project.text =list[position].title
        holder.wbs.text = list[position].title
        holder.time.text =  list[position].title
        holder.memo.text =  list[position].title
    }
}