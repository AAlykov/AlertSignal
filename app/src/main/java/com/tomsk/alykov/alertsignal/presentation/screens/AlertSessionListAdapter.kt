package com.tomsk.alykov.alertsignal.presentation.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tomsk.alykov.alertsignal.R
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel

class AlertSessionListAdapter: RecyclerView.Adapter<AlertSessionListAdapter.MyViewHolder>() {

    private var alertSessionList = listOf<AlertSessionModel>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewObjectCenter: TextView = itemView.findViewById(R.id.textViewObjectCenter)
        val textViewSignalName: TextView = itemView.findViewById(R.id.textViewSignalName)
        val textViewTimeSent: TextView = itemView.findViewById(R.id.textViewTimeSent)
        val textViewTimeRec: TextView = itemView.findViewById(R.id.textViewTimeRec)
        val textViewTimeConf: TextView = itemView.findViewById(R.id.textViewTimeConf)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.signal_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewObjectCenter.text = alertSessionList[position].senderName
        holder.textViewSignalName.text = alertSessionList[position].signalName
        holder.textViewTimeSent.text = alertSessionList[position].sessionStartTime
        holder.textViewTimeRec.text = alertSessionList[position].sessionGetTime
        holder.textViewTimeConf.text = alertSessionList[position].sessionConfirmTime
    }

    override fun getItemCount(): Int {
        return  alertSessionList.size
    }

    fun setData(alertSessionList: List<AlertSessionModel>) {
        this.alertSessionList = alertSessionList
        notifyDataSetChanged()
    }
}

