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

//ProcessTitleAdapter
class ProcessTitleAdapter(
    var list: ArrayList<ProcessData>,val context: Context
) : RecyclerView.Adapter<ProcessTitleAdapter.ViewHolder>() {

    private lateinit var listener: OnAdapterListener

    fun notifyDataSetChanged(list: ArrayList<ProcessData>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: AdapterProcessTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvEquipment: TextView = binding.tvEquipment
    }

    interface OnAdapterListener {
        fun onClick(selectName: String,dateTime :String , position: Int)
    }

    fun setOnAdapterListener(adapterListener: OnAdapterListener) {
        this.listener = adapterListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterProcessTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvEquipment.text = list[position].title

        //android:background="@drawable/bg_layout"
        holder.tvEquipment.setBackgroundResource(R.drawable.bg_layout)

        //android:textColor="@color/black"
        holder.tvEquipment.setTextColor(Color.BLACK)

        val selectName = Tools.sharedPreGetString(Config.lastSelectedProcessName)

        if (selectName == holder.tvEquipment.text){
            holder.tvEquipment.setBackgroundResource(R.drawable.ic_round_button_orange)
            holder.tvEquipment.setTextColor(Color.WHITE)
            Tools.sharedPrePut(Config.lastSelectedProcessName,selectName)
        }

        holder.tvEquipment.setOnClickListener {
            holder.tvEquipment.setBackgroundResource(R.drawable.ic_round_button_orange)
            holder.tvEquipment.setTextColor(Color.WHITE)
            Tools.sharedPrePut(Config.lastSelectedProcessName,selectName)

            listener.onClick(holder.tvEquipment.text.toString(),list[position].data, position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}