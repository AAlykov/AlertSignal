package com.tomsk.alykov.alertsignal.presentation.screens

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.tomsk.alykov.alertsignal.R
import com.tomsk.alykov.alertsignal.databinding.FragmentListBinding
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.presentation.viewmodels.AlertSessionViewModel


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var alertSessionList: List<AlertSessionModel>
    private lateinit var alertSessionViewModel: AlertSessionViewModel
    private lateinit var alertSessionListAdapter: AlertSessionListAdapter
    private lateinit var alertSessionRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        val buttonToFragmentSignalInfo = view.findViewById<Button>(R.id.buttonTest)
        val buttonAddSignalRoom = view.findViewById<Button>(R.id.buttonAddSignal)

        val navController = findNavController()
        buttonToFragmentSignalInfo.setOnClickListener {

            showNotification()

            //Два варианта перехода
            //navController.navigate(R.id.signalInfoFragment)
            navController.navigate(R.id.action_listFragment_to_signalInfoFragment)
        }
        alertSessionListAdapter = AlertSessionListAdapter()
        alertSessionRecyclerView = binding.rvAlertsessions
        alertSessionRecyclerView.adapter = alertSessionListAdapter

        alertSessionViewModel = ViewModelProvider(this).get(AlertSessionViewModel::class.java)

        /*
        alertSessionViewModel.getAllAlertSessions.observe(viewLifecycleOwner, Observer {
            Log.d("AADebug", "list: " + it.toString())
            val list = it
            alertSessionListAdapter.setData(list)
        }) */
        alertSessionViewModel.alertSessionsList.observe(viewLifecycleOwner, Observer {
            Log.d("AADebug", "list: " + it.toString())
            val list = it
            alertSessionListAdapter.setData(list)
        })

        alertSessionViewModel.notConfirmAlertSession.observe(viewLifecycleOwner, Observer {
            if (it!=null) {
                Log.d("AADebug", "NotConfirm: " + it.toString())
                val mes = it.senderName + "\n" + it.signalName + "\n" + it.sessionStartTime
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Сигнал оповещения!")
                    .setMessage(mes)
                    .setIcon(R.drawable.ic_baseline_crisis_alert_24)
                    .setPositiveButton("Подтвердить") { dialog, id ->
                        dialog.cancel()
                    }
                builder.create()
                builder.show()
            }
        })


        buttonAddSignalRoom.setOnClickListener {
            val alertSessionModel = AlertSessionModel(0, "001/1234", "Объект 178","Техническая проверка системы оповещения",
                1, 1, "Текст Текст Текст Текст Текст Текст Текст Текст Текст Текст",
                "20230117 12:40:01", "20230117 12:40:10", "", "", "", "")
            alertSessionViewModel.addAlertSession(alertSessionModel)
        }

        //super.onViewCreated(view, savedInstanceState)
    }

    private fun showNotification() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSettings -> {
                view?.let { Navigation.findNavController(it).navigate(ListFragmentDirections.actionListFragmentToSettingsFragment()) }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}