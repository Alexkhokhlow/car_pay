package com.example.mycar.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycar.R
import com.example.mycar.dataBase.car.Car
import com.example.mycar.dataBase.car.CarDataBase
import com.example.mycar.dataBase.event.EventDatabase
import com.example.mycar.databinding.FragmentCarListBinding
import com.example.mycar.model.AutoRecycleAdapter
import com.example.mycar.model.OnItemClickListener
import com.example.mycar.repository.Repository
import com.example.mycar.viewModel.MainViewModel
import com.example.mycar.viewModel.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar


class CarListFragment : Fragment(),OnItemClickListener {
    lateinit var binding: FragmentCarListBinding
    lateinit var repository:Repository
    lateinit var cars:MutableList<Car>
    lateinit var navController: NavController
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarListBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        navController = requireActivity().findNavController(R.id.nav_host_fragment)
        repository = Repository(CarDataBase.getInstance(requireActivity().applicationContext).carDao(),
                    EventDatabase.getInstance(requireContext().applicationContext).eventDao())
        val viewModelFactory = MainViewModelFactory(repository)
        val view = binding.root
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
        binding.myViewModel = viewModel

        viewModel.getCars()
        viewModel.cars.observe(viewLifecycleOwner,{
            cars = it.toMutableList()
            binding.listCarRecycleView.layoutManager = LinearLayoutManager(context)
            binding.listCarRecycleView.adapter =  AutoRecycleAdapter(cars,this)
        })

        binding.listCarAddCar.setOnClickListener {
            navController.navigate(R.id.action_carListFragment_to_addCarFragment)
        }
        return view
    }

    override fun onItemClicked(position: Int) {
        val items = arrayOf("edit", "delete","select")
        AlertDialog.Builder(context)
                .setTitle(R.string.app_name)
                .setItems(items) { _, which ->
                    when(which){
                        0 -> { val action = CarListFragmentDirections.actionCarListFragmentToAddCarFragment(
                               cars[position].carId                        )
                               navController.navigate(action)
                        }
                        1 -> {  viewModel.deleteCar(cars[position])
                                cars.remove(cars[position])
                                binding.listCarRecycleView.adapter?.notifyItemRemoved(position)
                            }
                        2 -> {
                                viewModel.selectCar(cars[position].carId)
                                Snackbar.make(binding.listCarAddCar,"Вы успешно выбрали автомобиль", Snackbar.LENGTH_SHORT).show()
                                navController.navigate(R.id.action_carListFragment_to_mainFragment)
                        }
                    }
                }.show()
    }
}