package com.yazaki_groupcom.app.ui.kodera

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.Tools
import com.yazaki_groupcom.app.base.BaseButton
import com.yazaki_groupcom.app.databinding.AdapterKoderaOneBinding
import com.yazaki_groupcom.app.enum.Equipment
import com.yazaki_groupcom.app.enum.ShareKey


class KoderaOneAdapter(val context: Context,var list: ArrayList<KoderaEachData>) : RecyclerView.Adapter<KoderaOneAdapter.ViewHolder>() {
    val TAG: String = "KoderaOneAdapter"

    inner class ViewHolder(binding: AdapterKoderaOneBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val facility: TextView = binding.facility
        val amount: TextView = binding.amount
        val cuttingAmount: TextView = binding.cuttingAmount
        val cuttingDate: TextView = binding.cuttingDate
        val groupNumber1: TextView = binding.groupNumber1
        val cerealNumber1: TextView = binding.cerealNumber1

        val variety1: TextView = binding.variety1
        val size1: TextView = binding.size1
        val color1: TextView = binding.color1
        val cuttingLineLength1: TextView = binding.cuttingLineLength1
        val dimensions:TextView = binding.dimensions

        val btCheck: BaseButton = binding.btCheck
        val btCheckRet: BaseButton = binding.btCheckRet
    }


    fun notifyDataSetChanged(list: ArrayList<KoderaEachData>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun notifyDataSetAdd(koderaEachData: KoderaEachData) {
        this.list.add(koderaEachData)
        Log.e(TAG, "notifyDataSetAdd: $list", )
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterKoderaOneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)

        //最後に選択したプロセス
        val  lastSelectedProcessName = Tools.sharedPreGetString(ShareKey.LastSelectedProcessName.key)
        if (lastSelectedProcessName.length>=4){
            if (lastSelectedProcessName.substring(0,4)== Equipment.C373.code){
                binding.duanzi1.text = Equipment.C373.explain
            }
            if (lastSelectedProcessName.substring(0,4)== Equipment.C385.code){
                binding.duanzi1.text = Equipment.C385.explain
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        holder.facility.text = list[position].facility
        holder.amount.text = list[position].amount
        holder.cuttingAmount.text = list[position].cuttingAmount
        holder.cuttingDate.text = list[position].cuttingDate

        holder.groupNumber1.text = list[position].groupNumber1
        holder.cerealNumber1.text = list[position].cerealNumber1
        holder.variety1.text = list[position].variety1
        holder.size1.text = list[position].size1
        holder.color1.text = list[position].color1
        holder.cuttingLineLength1.text = list[position].cuttingLineLength1

        holder.dimensions.text = list[position].dimensions

        changeColorByState(this.list[position].amountState, holder.amount)
        changeColorByState(this.list[position].variety1State, holder.variety1)
        changeColorByState(this.list[position].size1State, holder.size1)
        changeColorByState(this.list[position].color1State, holder.color1)
        changeColorByState(this.list[position].cuttingLineLength1State, holder.cuttingLineLength1)

        holder.btCheck.setOnClickListener {
            listener.onClick(position)
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
                holder.btCheckRet.changeColorByState(3)
                //holder.btCheckRet.changeColorByState(3)

                //change color
                holder.amount.setBackgroundResource(R.drawable.bg_layout_black)
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

    private fun changeColorByState(
        state: Int,
        textView: TextView
    ) {
        when (state) {
            1 -> {
                textView.setBackgroundResource(R.drawable.bg_layout_black_red)
            }
            2 -> {
                textView.setBackgroundResource(R.drawable.bg_layout_black_yellow)
            }
            else -> {
                textView.setBackgroundResource(R.drawable.bg_layout_black)
            }
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