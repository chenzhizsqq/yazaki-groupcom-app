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


class KomaxOneAdapter(val context: Context,private val viewModel: KomaxViewModel) : RecyclerView.Adapter<KomaxOneAdapter.ViewHolder>() {
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
        val btCheck: BaseButton = binding.btCheck
        val btCheckRet: BaseButton = binding.btCheckRet

        val facilityRequirements: TextView = binding.facilityRequirements
        val amount: TextView = binding.amount
        val cuttingAmount: TextView = binding.cuttingAmount
        val cuttingDate: TextView = binding.cuttingDate

        val group_number_1 :TextView = binding.groupNumber1
        val cereal_number_1 :TextView = binding.cerealNumber1
        val variety_1 :TextView = binding.variety1
        val size_1 :TextView = binding.size1
        val color_1 :TextView = binding.color1
        val cutting_line_length_1 :TextView = binding.cuttingLineLength1

        val group_number_2 :TextView = binding.groupNumber2
        val cereal_number_2 :TextView = binding.cerealNumber2
        val variety_2 :TextView = binding.variety2
        val size_2 :TextView = binding.size2
        val color_2 :TextView = binding.color2
        val cutting_line_length_2 :TextView = binding.cuttingLineLength2

        val terminal_number_1 :TextView = binding.terminalNumber1
        val skin_size_1 :TextView = binding.skinSize1
        val applicator_1 :TextView = binding.applicator1
        val parts_number_1 :TextView = binding.partsNumber1

        val terminal_number_2 :TextView = binding.terminalNumber1
        val skin_size_2 :TextView = binding.skinSize2
        val applicator_2 :TextView = binding.applicator2
        val parts_number_2 :TextView = binding.partsNumber2
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
        holder.cuttingDate.text = list[position].title
        holder.btCheck.setOnClickListener {
            listener.onClick(position)

            //放送数据给 KomaxViewModel
            val twoData = KomaxTwoData(
                holder.terminal_number_1.text.toString(),
                holder.skin_size_1.text.toString(),
                holder.applicator_1.text.toString(),
                holder.parts_number_1.text.toString(),

                holder.terminal_number_2.text.toString(),
                holder.skin_size_2.text.toString(),
                holder.applicator_2.text.toString(),
                holder.parts_number_2.text.toString(),
            )
            viewModel.komaxTwoData.postValue(twoData)
        }
        holder.btCheckRet.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.resources.getString(R.string.cut_off_title))
            builder.setMessage(context.resources.getString(R.string.cut_off_message))
            builder.setPositiveButton(context.resources.getString(R.string.cut_off_ok)) { dialog, which ->

                //写入文件内容
                var fileContent = ""
                val csvStringBuilder = StringBuilder()

                val facilityRequirements = holder.facilityRequirements.text
                csvStringBuilder.append("$facilityRequirements\n")
                val amount = holder.amount.text
                csvStringBuilder.append("$amount\n")
                val cuttingAmount = holder.cuttingAmount.text
                csvStringBuilder.append("$cuttingAmount\n")
                val cuttingDate = holder.cuttingDate.text
                csvStringBuilder.append("$cuttingDate\n")

                //用文字内容写成csv
                fileContent = csvStringBuilder.toString()
                Tools.makeCsv(fileContent)

                //change state
                holder.btCheck.changeColorByState(3)
                holder.btCheckRet.changeColorByState(3)

                //change color
                holder.facilityRequirements.setBackgroundResource(R.drawable.bg_layout_black)
                holder.amount.setBackgroundResource(R.drawable.bg_layout_black)
                holder.cuttingAmount.setBackgroundResource(R.drawable.bg_layout_black)
                holder.cuttingDate.setBackgroundResource(R.drawable.bg_layout_black)


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