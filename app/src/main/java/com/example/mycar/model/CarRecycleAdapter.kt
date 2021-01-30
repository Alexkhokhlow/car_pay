package com.example.mycar.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycar.dataBase.car.Car
import com.example.mycar.databinding.CarListItemBinding

class AutoRecycleAdapter(private val cars: List<Car>, private val itemClickListener: OnItemClickListener): RecyclerView.Adapter<AutoRecycleAdapter.ViewHolder>() {
lateinit var binding: CarListItemBinding
   inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

       fun bind(car: Car, clickListener: OnItemClickListener)
       {
           binding.listItemCarName.text = car.name
           binding.listItemCarMark.text = car.mark
           binding.listItemCarModel.text = car.model
           binding.listItemCarMileage.text = car.mileage
           binding.listItemCarFuel.text = car.carId.toString()

           itemView.setOnClickListener {
               clickListener.onItemClicked(adapterPosition)
           }
       }

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CarListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val view = binding.root
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val a = cars[position]
        holder.bind(a,itemClickListener)
    }

    override fun getItemCount() = cars.size

}

interface OnItemClickListener{
    fun onItemClicked(position: Int)
}