package com.tomsk.alykov.alertsignal.presentation.screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tomsk.alykov.alertsignal.R
import com.tomsk.alykov.alertsignal.data.models.AlertSessionRequestAnswerModel
import com.tomsk.alykov.alertsignal.data.models.AlertSessionFBModel
import com.tomsk.alykov.alertsignal.databinding.FragmentListBinding
import com.tomsk.alykov.alertsignal.domain.models.AlertSessionModel
import com.tomsk.alykov.alertsignal.presentation.MainActivity
import com.tomsk.alykov.alertsignal.presentation.viewmodels.AlertSessionViewModel
import com.tomsk.alykov.alertsignal.utils.Calculations.timeStampToString


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var alertSessionList: List<AlertSessionModel>
    private lateinit var alertSessionViewModel: AlertSessionViewModel
    private lateinit var alertSessionListAdapter: AlertSessionListAdapter
    private lateinit var alertSignalSystemJournalAdapter: AlertSignalJournalAdapter
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

        val buttonToFragmentSignalInfo = view.findViewById<Button>(R.id.buttonTestNav)
        val buttonAddSignalRoom = view.findViewById<Button>(R.id.buttonAddSessionRoom)
        val buttonDelAllRoom = view.findViewById<Button>(R.id.buttonDelAllRoom)

        val navController = findNavController()
        buttonToFragmentSignalInfo.setOnClickListener {
            //showNotification()
            //Два варианта перехода
            //navController.navigate(R.id.signalInfoFragment)
            navController.navigate(R.id.action_listFragment_to_signalInfoFragment)
        }
        alertSessionListAdapter = AlertSessionListAdapter(requireContext())
        alertSessionRecyclerView = binding.rvAlertsessions
        alertSessionRecyclerView.adapter = alertSessionListAdapter

        alertSignalSystemJournalAdapter = AlertSignalJournalAdapter(requireContext())
        binding.rvSystemJournal.adapter = alertSignalSystemJournalAdapter

        alertSessionViewModel = ViewModelProvider(this).get(AlertSessionViewModel::class.java)

        alertSessionViewModel.getTodaySystemJournal.observe(viewLifecycleOwner, Observer {
            //Log.d("AADebug", "list: " + it.toString())
            val list = it
            alertSignalSystemJournalAdapter.setData(list)
        })

        alertSessionViewModel.alertSessionsList.observe(viewLifecycleOwner, Observer {
            //Log.d("AADebug", "list: " + it.toString())
            val list = it
            alertSessionListAdapter.setData(list)
        })

        alertSessionViewModel.alertSessionCheck.observe(viewLifecycleOwner, Observer {
            //Log.d("AADebug", "check: $it")
            val alertSessionCheckModel: AlertSessionRequestAnswerModel? = it
            alertSessionCheckModel?.let {
                if (alertSessionCheckModel.errorCheck == "") {
                    binding.textViewRequestCheck.text = alertSessionCheckModel.requestTime
                    binding.textViewAnswerCheck.text = alertSessionCheckModel.answerTime
                    binding.textViewError.visibility = View.GONE
                } else {
                    binding.textViewError.visibility = View.VISIBLE
                    binding.textViewRequestCheck.text = alertSessionCheckModel.requestTime
                    binding.textViewAnswerCheck.text = alertSessionCheckModel.answerTime
                    binding.textViewError.text = alertSessionCheckModel.errorCheck
                }
            }
            //if (alertSessionCheckModel != null) { }
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
                        //Toast.makeText(requireContext(), "AADe", Toast.LENGTH_SHORT).show()
                        //alertSessionModel.sessionConfirmTime = System.currentTimeMillis().toString()
                        val sessionConfirmTimeUnix = System.currentTimeMillis()
                        val sessionConfirmTime = timeStampToString(sessionConfirmTimeUnix)
                        val alertSessionModel = AlertSessionModel(it.id, it.sessionCode, it.senderName,it.signalName,
                            it.signalType, it.signalGrade, it.signalText, it.sessionStartTimeUnix, it.sessionStartTime,
                            it.sessionGetTimeUnix, it.sessionGetTime, sessionConfirmTimeUnix.toString(), sessionConfirmTime,
                            it.userName,it.userIdFireBase,it.sessionIdFireBase)
                        alertSessionViewModel.confirmAlertSession(alertSessionModel)
                    }
                builder.create()
                builder.show()
            }
        })

        binding.buttonAddSessionRoom.setOnClickListener {
            val rand = (1..9999).random()
            val nowTimeUnix = System.currentTimeMillis()
            val nowTime = timeStampToString(nowTimeUnix)
            val getTimeUnix = nowTimeUnix + (1..5000).random()
            val getTime = timeStampToString(getTimeUnix)

            val alertSessionModel = AlertSessionModel(0, "001/$rand", "Объект $rand",
                "$rand Техническая проверка системы оповещения",
                (1..3).random(), (1..3).random(), "$rand Текст Текст Текст Текст Текст Текст Текст Текст Текст Текст $rand",
                "$nowTimeUnix", "$nowTime", "$getTimeUnix", "$getTime", "", "")
            alertSessionViewModel.addAlertSession(alertSessionModel)
        }

        binding.buttonAddSessionFB.setOnClickListener {
            val rand = (1..9999).random()
            val nowTimeUnix = System.currentTimeMillis()
            val nowTime = timeStampToString(nowTimeUnix)
            val alertSessionFBModel = AlertSessionFBModel("001/$rand", "Объект $rand",
                "Техническая проверка системы оповещения $rand",(1..4).random(),
                (1..4).random(), "$rand Текст Текст Текст Текст Текст Текст Текст Текст Текст Текст $rand",
                "$nowTimeUnix", "$nowTime", "")

            alertSessionViewModel.addAlertSessionFB(
                alertSessionFBModel,
                {
                    Log.d("AADebug", "mViewModel.insert: RESULT OK")
                    Toast.makeText(requireContext(), "Данные добавлены", Toast.LENGTH_LONG).show()
                }, {
                    Log.d("AADebug", "mViewModel.insert: RESULT ERROR $it")
                    Toast.makeText(requireContext(), "Ошибка добавления данных $it", Toast.LENGTH_LONG).show()
            })
        }

        binding.buttonDelAllRoom.setOnClickListener {
            alertSessionViewModel.deleteAllRoomUseCase()
        }

        binding.buttonNotify.setOnClickListener {
            showNotification()
        }

    }

    private fun showNotification() {
        val notificationManager = requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    "AlertSignal",
                    "Уведомления", //категории уведомлений, можно по русски
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val intentAlertSignalApp = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(requireContext(), 0, intentAlertSignalApp, PendingIntent.FLAG_IMMUTABLE)
        val vibrate = longArrayOf(100, 500, 100, 500)
        val bitmap = BitmapFactory.decodeResource(this.resources,
            R.drawable.ic_baseline_crisis_alert_24
        )
        val notification = NotificationCompat.Builder(requireContext(), "AlertSignal") //тут убиваем два зайца = без проверок на апи 26
            .setContentTitle("AlertSignal")
            .setContentText("Text")
            .setColor(this.resources.getColor(R.color.red, null))
            .setShowWhen(true)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setVibrate(vibrate)
            .setLights(Color.GREEN, 2000, 3000)
            .setLargeIcon(bitmap)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setOnlyAlertOnce(true)
            .setSmallIcon(R.drawable.ic_baseline_crisis_alert_24)
            .setContentIntent(pendingIntent)
            //.setContentIntent(PendingIntent.getActivity(this,0, Intent(this, MainActivity::class.java),0))
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .build()

        //val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)

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