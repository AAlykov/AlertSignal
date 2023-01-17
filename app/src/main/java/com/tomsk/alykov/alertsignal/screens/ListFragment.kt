package com.tomsk.alykov.alertsignal.screens

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.tomsk.alykov.alertsignal.R


class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        val buttonToFragmentSignalInfo = view.findViewById<Button>(R.id.buttonTest)
        val navController = findNavController()
        buttonToFragmentSignalInfo.setOnClickListener {
            //Два варианта перехода
            //navController.navigate(R.id.signalInfoFragment)
            navController.navigate(R.id.action_listFragment_to_signalInfoFragment)
        }

        super.onViewCreated(view, savedInstanceState)
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