package com.tomsk.alykov.alertsignal.presentation.screens

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tomsk.alykov.alertsignal.R
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.utils.Calculations.timeStampToString

class AlertSessionListAdapter: RecyclerView.Adapter<AlertSessionListAdapter.MyViewHolder>() {

    private var alertSessionList = listOf<AlertSessionModel>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSessionCode: TextView = itemView.findViewById(R.id.textViewSessionCode)
        val textViewObjectCenter: TextView = itemView.findViewById(R.id.textViewObjectCenter)
        val textViewSignalName: TextView = itemView.findViewById(R.id.textViewSignalName)
        val textViewTimeSent: TextView = itemView.findViewById(R.id.textViewTimeSent)
        val textViewTimeRec: TextView = itemView.findViewById(R.id.textViewTimeRec)
        val textViewTimeConf: TextView = itemView.findViewById(R.id.textViewTimeConf)
        val rowCardView: MaterialCardView = itemView.findViewById(R.id.row_cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.signal_item, parent, false)
        return MyViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val alertSessionModel = alertSessionList[position]
        holder.textViewSessionCode.text = alertSessionModel.sessionCode
        holder.textViewObjectCenter.text = alertSessionModel.senderName
        holder.textViewSignalName.text = alertSessionModel.signalName
        holder.textViewTimeSent.text = alertSessionModel.sessionStartTime
        holder.textViewTimeRec.text = alertSessionModel.sessionGetTime
        holder.textViewTimeConf.text = alertSessionModel.sessionConfirmTime

        if (position == 0) {
            //holder.rowCardView.cardElevation = 200f
            //val aaa = holder.rowCardView.cardBackgroundColor
            //Log.d("AADebug", "onBindViewHolder: $aaa")
            //holder.rowCardView.outlineSpotShadowColor = Color.YELLOW

        }
        else
            holder.rowCardView.maxCardElevation = 0f
    }

    override fun getItemCount(): Int {
        return  alertSessionList.size
    }

    fun setData(alertSessionList: List<AlertSessionModel>) {
        this.alertSessionList = alertSessionList
        notifyDataSetChanged()
    }
}

