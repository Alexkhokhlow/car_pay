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
import com.example.mycar.dataBase.car.Car
import com.example.mycar.dataBase.car.CarDataBase
import com.example.mycar.dataBase.event.EventDatabase
import com.example.mycar.databinding.FragmentAddCarBinding
import com.example.mycar.repository.Repository
import com.example.mycar.viewModel.MainViewModel
import com.example.mycar.viewModel.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar

class AddCarFragment : Fragment() {
    lateinit var binding: FragmentAddCarBinding
    lateinit var argument: String
    lateinit var car: Car
    val args: AddCarFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCarBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.lifecycleOwner = this

        val repository = Repository(CarDataBase.getInstance(requireActivity().applicationContext).carDao(),
                            EventDatabase.getInstance(requireContext().applicationContext).eventDao())
        val viewModelFactory = MainViewModelFactory(repository)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
        if(args.idCar != -1L){
            viewModel.updateCarView(args.idCar)
            viewModel.initAddOpUpdate()
        }

        viewModel.wrong.observe(viewLifecycleOwner,{
            if(it) {
                Snackbar.make(binding.addCarButton, "Есть пустые стоки", Snackbar.LENGTH_SHORT)
                    .show()
            }else {
                if(args.idCar != -1L){
                    Snackbar.make(binding.addCarButton, "Машина успешно изменена", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(binding.addCarButton, "Машина успешно добавлена", Snackbar.LENGTH_SHORT).show()
                }
            requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.action_addCarFragment_to_carListFragment)
        }
        })

        return view
    }


}