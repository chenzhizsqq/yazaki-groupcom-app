package com.yazaki_groupcom.app.ui.kodera

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.Tools
import com.yazaki_groupcom.app.base.BaseButton
import com.yazaki_groupcom.app.databinding.AdapterKoderaOneBinding


class KoderaOneAdapter(val context: Context) : RecyclerView.Adapter<KoderaOneAdapter.ViewHolder>() {
    val TAG: String = "KoderaOneAdapter"


    lateinit var list: ArrayList<KoderaOneData>

    fun notifyDataSetChanged(list: ArrayList<KoderaOneData>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun notifyDataSetAdd(koderaOneData: KoderaOneData) {
        this.list.add(koderaOneData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: AdapterKoderaOneBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val variety1: TextView = binding.variety1
        val size1: TextView = binding.size1
        val color1: TextView = binding.color1
        val cuttingLineLength1: TextView = binding.cuttingLineLength1
        val btCheck: BaseButton = binding.btCheck
        val btCheckRet: BaseButton = binding.btCheckRet
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
        holder.btCheck.setOnClickListener {
            listener.onClick(position)

            Tools.sharedPrePut("KoderaOneAdapter_type", holder.variety1.text.toString())
            Tools.sharedPrePut("KoderaOneAdapter_size", holder.size1.text.toString())
            Tools.sharedPrePut("KoderaOneAdapter_color", holder.color1.text.toString())
            Tools.sharedPrePut("KoderaOneAdapter_longSize", holder.cuttingLineLength1.text.toString())
        }
        holder.btCheckRet.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.resources.getString(R.string.cut_off_title))
            builder.setMessage(context.resources.getString(R.string.cut_off_message))
            builder.setPositiveButton(context.resources.getString(R.string.cut_off_ok)) { dialog, which ->

//                //写入文件内容
//                var fileContent = ""
//                val csvStringBuilder = StringBuilder()
//
//                val type = holder.type.text
//                csvStringBuilder.append("$type\n")
//                val size = holder.size.text
//                csvStringBuilder.append("$size\n")
//                val color = holder.color.text
//                csvStringBuilder.append("$color\n")
//                val longSize = holder.longSize.text
//                csvStringBuilder.append("$longSize\n")
//
//                //用文字内容写成csv
//                fileContent = csvStringBuilder.toString()
//                Tools.makeCsv(fileContent)

                //change state
                holder.btCheck.changeColorByState(3)
                //holder.btCheckRet.changeColorByState(3)

                //change color
                holder.variety1.setBackgroundResource(R.drawable.bg_layout_black)
                holder.size1.setBackgroundResource(R.drawable.bg_layout_black)
                holder.color1.setBackgroundResource(R.drawable.bg_layout_black)
                holder.cuttingLineLength1.setBackgroundResource(R.drawable.bg_layout_black)


            }
            builder.setNegativeButton(context.resources.getString(R.string.cut_off_cancel)) { dialog, which ->
                // 点击 Cancel 按钮的回调
            }
            val dialog = builder.create()
            dialog.show()
            listener.onCheck(position)
        }
    }

    //listen
    private lateinit var listener: OnAdapterListener

    interface OnAdapterListener {
        fun onClick(id: Int)
        fun onCheck(id: Int)
    }

    fun setOnAdapterListener(adapterListener: OnAdapterListener) {
        this.listener = adapterListener
    }
}