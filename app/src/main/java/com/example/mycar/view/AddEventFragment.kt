package com.example.mycar.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mycar.R
import com.example.mycar.dataBase.car.CarDataBase
import com.example.mycar.dataBase.event.EventDatabase
import com.example.mycar.databinding.FragmentAddEventBinding
import com.example.mycar.repository.Repository
import com.example.mycar.viewModel.MainViewModel
import com.example.mycar.viewModel.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar


class AddEventFragment : Fragment() {
        lateinit var binding:FragmentAddEventBinding
        val args: AddEventFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentAddEventBinding.inflate(LayoutInflater.from(context),container,false)
        val view = binding.root
        binding.lifecycleOwner = this
        val repository = Repository(CarDataBase.getInstance(requireActivity().applicationContext).carDao(),
                EventDatabase.getInstance(requireContext().applicationContext).eventDao())
        val viewModelFactory = MainViewModelFactory(repository)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
        val a = args.idEvent
        if(args.idEvent != -1L){
            viewModel.updateEventView(args.idEvent)
            viewModel.initAddOpUpdate()
        }

        viewModel.wrongEvent.observe(viewLifecycleOwner,{
            if(it) {
                Snackbar.make(binding.eventAddButton, "Есть пустые стоки", Snackbar.LENGTH_SHORT)
                        .show()
            }else {
                if(args.idEvent != -1L){
                    Snackbar.make(binding.eventAddButton, "Событие успешно изменена", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(binding.eventAddButton, "Событие успешно добавлена", Snackbar.LENGTH_SHORT).show()
                }
                requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.action_addEventFragment_to_mainFragment)
            }
        })

        return view
    }


}