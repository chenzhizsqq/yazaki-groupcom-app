package com.yazaki_groupcom.app.ui.komax

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.Tools
import com.yazaki_groupcom.app.base.BaseButton
import com.yazaki_groupcom.app.databinding.AdapterKomaxOneBinding


class KomaxOneAdapter(val context: Context) : RecyclerView.Adapter<KomaxOneAdapter.ViewHolder>() {
    val TAG: String = "KomaxOneAdapter"


    lateinit var list: ArrayList<KomaxOneData>

    fun notifyDataSetChanged(list: ArrayList<KomaxOneData>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun notifyDataSetAdd(koderaOneData: KomaxOneData) {
        this.list.add(koderaOneData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: AdapterKomaxOneBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val type: TextView = binding.type
        val size: TextView = binding.size
        val color: TextView = binding.color
        val longSize: TextView = binding.longSize
        val btCheck: BaseButton = binding.btCheck
        val btCheckRet: BaseButton = binding.btCheckRet
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterKomaxOneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.longSize.text = list[position].title
        holder.btCheck.setOnClickListener {
            listener.onClick(position)

            Tools.sharedPrePut("KoderaOneAdapter_type", holder.type.text.toString())
            Tools.sharedPrePut("KoderaOneAdapter_size", holder.size.text.toString())
            Tools.sharedPrePut("KoderaOneAdapter_color", holder.color.text.toString())
            Tools.sharedPrePut("KoderaOneAdapter_longSize", holder.longSize.text.toString())
        }
        holder.btCheckRet.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.resources.getString(R.string.cut_off_title))
            builder.setMessage(context.resources.getString(R.string.cut_off_message))
            builder.setPositiveButton(context.resources.getString(R.string.cut_off_ok)) { dialog, which ->

                //写入文件内容
                var fileContent = ""
                val csvStringBuilder = StringBuilder()
                csvStringBuilder.append("Name, Age, Gender\n")
                csvStringBuilder.append("John, 30, Male\n")
                csvStringBuilder.append("Jane, 25, Female\n")
                csvStringBuilder.append("Bob, 40, Male\n")
                fileContent = csvStringBuilder.toString()

                //用文字内容写成csv
                Tools.makeCsv(fileContent)

                //change state
                holder.btCheck.changeColorByState(3)
                holder.btCheckRet.changeColorByState(3)

                //change color
                holder.type.setBackgroundResource(R.drawable.bg_layout_black)
                holder.size.setBackgroundResource(R.drawable.bg_layout_black)
                holder.color.setBackgroundResource(R.drawable.bg_layout_black)
                holder.longSize.setBackgroundResource(R.drawable.bg_layout_black)


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