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
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tomsk.alykov.alertsignal.R
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.domain.models.AlertSignalSystemJournal
import com.tomsk.alykov.alertsignal.utils.Calculations.timeStampToString

class AlertSignalJournalAdapter(val context: Context): RecyclerView.Adapter<AlertSignalJournalAdapter.MyViewHolder>() {

    var alertSignalSystemJournalList = listOf<AlertSignalSystemJournal>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewJournalDate: TextView = itemView.findViewById(R.id.itemJournalDateTextView)
        val textViewJournalData: TextView = itemView.findViewById(R.id.itemJournalDataTextView)
        val constraintJournalItemView : View = itemView.findViewById(R.id.journalItemView)

        init {
            /*rowCardView.setOnClickListener {
                val position = adapterPosition
                Log.d("AADebug", "Item clicked at: $position")

                val action = Navigation.findNavController(it).navigate(ListFragmentDirections.actionListFragmentToSignalInfoFragment())
                //val action2 = ListFragmentDirections.actionListFragmentToSignalInfoFragment(alertSessionList[position])


                //val action = HabitListFragmentDirections.actionHabitListFragmentToUpdateHabitFragment((habitList[position]),habitList[position].habit_startTime )
                //itemView.findNavController().navigate(action)
            }*/
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.journal_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val alertSignalSystemJournal = alertSignalSystemJournalList[position]
        holder.textViewJournalDate.text = alertSignalSystemJournal.time
        holder.textViewJournalData.text = alertSignalSystemJournal.action
        if (alertSignalSystemJournal.action.contains("Problem")) {
            //holder.textViewJournalDate.setBackgroundColor(context.getColor(R.color.red2))
            //holder.textViewJournalData.setBackgroundColor(context.getColor(R.color.red2))
            holder.constraintJournalItemView.setBackgroundColor(context.getColor(R.color.red2))
        } else {
            ///holder.textViewJournalDate.setBackgroundColor(context.getColor(R.color.white))
            //holder.textViewJournalData.setBackgroundColor(context.getColor(R.color.white))
            holder.constraintJournalItemView.setBackgroundColor(context.getColor(R.color.transp))
        }
    }

    override fun getItemCount(): Int {
        return  alertSignalSystemJournalList.size
    }

    fun setData(alertSignalSystemJournalList: List<AlertSignalSystemJournal>) {
        this.alertSignalSystemJournalList = alertSignalSystemJournalList
        notifyDataSetChanged()
    }
}

