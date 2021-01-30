package com.example.mycar.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycar.dataBase.car.Car
import com.example.mycar.dataBase.event.Event
import com.example.mycar.databinding.EventListItemBinding
import com.example.mycar.databinding.FragmentMainBinding
import com.example.mycar.databinding.MainEventListItemBinding

class EventRecycleAdapter(private val events:List<Event>, private val onItemClickListener: OnItemClickListener):RecyclerView.Adapter<EventRecycleAdapter.ViewHolder>() {
    lateinit var binding: MainEventListItemBinding

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(event: Event, clickListener: OnItemClickListener)
        {
            binding.eventMainName.text = event.eventName
            binding.eventMainItemPrice.text = event.eventPrise
            binding.eventMainItemData.text = event.eventPrise

            itemView.setOnClickListener {
                clickListener.onItemClicked(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = MainEventListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val view = binding.root
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events[position],onItemClickListener)
    }

    override fun getItemCount() = events.size

}


