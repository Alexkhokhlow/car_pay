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
import com.example.mycar.dataBase.car.CarDataBase
import com.example.mycar.dataBase.event.Event
import com.example.mycar.dataBase.event.EventDatabase
import com.example.mycar.databinding.FragmentMainBinding
import com.example.mycar.model.EventRecycleAdapter
import com.example.mycar.model.OnItemClickListener
import com.example.mycar.repository.Repository
import com.example.mycar.viewModel.MainViewModel
import com.example.mycar.viewModel.MainViewModelFactory

class MainFragment : Fragment(), OnItemClickListener {
    lateinit var binding: FragmentMainBinding
    lateinit var repository:Repository
    lateinit var event:MutableList<Event>
    lateinit var navController: NavController
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        navController = requireActivity().findNavController(R.id.nav_host_fragment)
        repository = Repository(CarDataBase.getInstance(requireActivity().applicationContext).carDao(),
                     EventDatabase.getInstance(requireContext().applicationContext).eventDao())
        val viewModelFactory = MainViewModelFactory(repository)
        val view = binding.root
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.getSelectCarForMainView()

        viewModel.getEventsForCar()
        viewModel.eventForCar.observe(viewLifecycleOwner,{
            event = it.toMutableList()
            binding.eventMainRecycleView.layoutManager = LinearLayoutManager(context)
            binding.eventMainRecycleView.adapter =  EventRecycleAdapter(event,this)
        })

       binding.openCarList.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.action_mainFragment_to_carListFragment)
        }
        return view
    }

    override fun onItemClicked(position: Int) {
            val items = arrayOf("edit", "delete")
            AlertDialog.Builder(context)
                    .setTitle(R.string.app_name)
                    .setItems(items) { _, which ->
                        when(which){
                            0 -> { val action = MainFragmentDirections.actionMainFragmentToAddEventFragment(
                                    event[position].eventId )
                                  navController.navigate(action)
                            }
                            1 -> {  viewModel.deleteEvent(event[position])
                                event.remove(event[position])
                                binding.eventMainRecycleView.adapter?.notifyItemRemoved(position)
                            }
                        }
                    }.show()
        }
    }


