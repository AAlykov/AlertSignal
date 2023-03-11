package com.tomsk.alykov.alertsignal.presentation.screens

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tomsk.alykov.alertsignal.R
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.utils.Calculations.timeStampToString

class AlertSessionListAdapter(val context: Context): RecyclerView.Adapter<AlertSessionListAdapter.MyViewHolder>() {

    var alertSessionList = listOf<AlertSessionModel>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSessionCode: TextView = itemView.findViewById(R.id.textViewSessionCode)
        val textViewObjectCenter: TextView = itemView.findViewById(R.id.textViewObjectCenter)
        val textViewSignalName: TextView = itemView.findViewById(R.id.textViewSignalName)
        val textViewTimeSent: TextView = itemView.findViewById(R.id.textViewTimeSent)
        val textViewTimeRec: TextView = itemView.findViewById(R.id.textViewTimeRec)
        val textViewTimeConf: TextView = itemView.findViewById(R.id.textViewTimeConf)
        val rowCardView: MaterialCardView = itemView.findViewById(R.id.row_cardView)
        val cardViewSignalName: MaterialCardView = itemView.findViewById(R.id.cardViewSignalName)

        init {
            rowCardView.setOnClickListener {
                val position = adapterPosition
                Log.d("AADebug", "Item clicked at: $position")

                val action = Navigation.findNavController(it).navigate(ListFragmentDirections.actionListFragmentToSignalInfoFragment())
                //val action2 = ListFragmentDirections.actionListFragmentToSignalInfoFragment(alertSessionList[position])


                //val action = HabitListFragmentDirections.actionHabitListFragmentToUpdateHabitFragment((habitList[position]),habitList[position].habit_startTime )
                //itemView.findNavController().navigate(action)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.signal_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val alertSessionModel = alertSessionList[position]
        holder.textViewSessionCode.text = alertSessionModel.sessionCode
        holder.textViewObjectCenter.text = alertSessionModel.senderName
        holder.textViewSignalName.text = alertSessionModel.signalName
        holder.textViewTimeSent.text = alertSessionModel.sessionStartTime
        holder.textViewTimeRec.text = alertSessionModel.sessionGetTime
        holder.textViewTimeConf.text = alertSessionModel.sessionConfirmTime

        if (alertSessionModel.signalGrade == 1) {
            //holder.cardViewSignalName.radius = 30f
            //holder.cardViewSignalName.setCardBackgroundColor(context.getColor(R.color.red2))
            //holder.textViewSignalName.setBackgroundColor(context.getColor(R.color.colorLightRed))
            holder.textViewSignalName.setBackgroundColor(context.getColor(R.color.red2))

        } else if (alertSessionModel.signalGrade == 2) {
            //holder.cardViewSignalName.radius = 30f
            holder.textViewSignalName.setBackgroundColor(context.getColor(R.color.yellow2))

        } else if (alertSessionModel.signalGrade == 3) {
            //holder.cardViewSignalName.radius = 30f
            //holder.cardViewSignalName.setBackgroundColor(context.getColor(R.color.green2))
            holder.textViewSignalName.setBackgroundColor(context.getColor(R.color.green2))
        } else if (alertSessionModel.signalGrade == 4) {
            holder.textViewSignalName.setBackgroundColor(context.getColor(R.color.white))
            //holder.cardViewSignalName.radius = 30f
            //holder.cardViewSignalName.setBackgroundColor(context.getColor(R.color.green2))
            //holder.textViewSignalName.setBackgroundColor(context.getColor(R.color.green2))
        }


    }

    override fun getItemCount(): Int {
        return  alertSessionList.size
    }

    fun setData(alertSessionList: List<AlertSessionModel>) {
        this.alertSessionList = alertSessionList
        notifyDataSetChanged()
    }
}

