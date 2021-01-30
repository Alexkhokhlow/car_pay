package com.example.mycar.viewModel

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycar.dataBase.car.Car
import com.example.mycar.dataBase.event.Event
import com.example.mycar.repository.Repository
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class MainViewModel(private val repository: Repository): ViewModel(), Observable {

    val cars = MutableLiveData<List<Car>>()
    var car = MutableLiveData<Car>()
    val wrong = MutableLiveData<Boolean>()
    val wrongEvent = MutableLiveData<Boolean>()
    lateinit var updateCar: Car
    lateinit var updateEvent: Event
    var isUpdate = false
    val eventForCar = MutableLiveData<List<Event>>()

    @Bindable
    val wrongVisible = MutableLiveData<Int>(View.INVISIBLE)
    @Bindable
    val carName = MutableLiveData<String>()
    @Bindable
    val carModel = MutableLiveData<String>()
    @Bindable
    val carMark = MutableLiveData<String>()
    @Bindable
    val carFuel = MutableLiveData<String>()
    @Bindable
    val carMileage = MutableLiveData<String>()
    @Bindable
    val buttonAddOrUpdate = MutableLiveData<String>("ADD CAR")


    @Bindable
    val eventName = MutableLiveData<String>()
    @Bindable
    val eventType = MutableLiveData<String>()
    @Bindable
    val eventWorkType = MutableLiveData<String>()
    @Bindable
    val eventPrice = MutableLiveData<String>()
    @Bindable
    val eventMileage = MutableLiveData<String>()
    @Bindable
    val eventComment = MutableLiveData<String>()


    fun insertOrUpdateCar() {
        try {
            viewModelScope.launch {
                if(isUpdate){
                    repository.updateCar(Car(updateCar.carId,name = carName.value!!, mark = carMark.value!!,
                            model = carModel.value!!, fuel = carFuel.value!!, mileage = carMileage.value!!,0))
                    wrong.value = false
                } else {
                    repository.insertCar(Car(Math.random().toLong(),name = carName.value!!, mark = carMark.value!!,
                            model = carModel.value!!, fuel = carFuel.value!!, mileage = carMileage.value!!,0))
                    wrong.value = false
                }
            }
        } catch (e:NullPointerException){
            wrong.value = true
        }
    }


    fun insertOrUpdateEvent(){
        try {
            viewModelScope.launch {
                if (isUpdate) {
                    repository.updateEvent(Event(carId = updateEvent.carId, eventId = updateEvent.eventId, eventName.value!!, eventType.value!!,
                            eventWorkType.value!!, eventPrice.value!!, eventMileage.value!!, eventComment.value!!))
                    wrongEvent.value = false
                } else {
                    repository.insertEvent(Event(carId = repository.getSelectCarId(), eventId = Math.random().toLong(), eventName.value!!, eventType.value!!,
                            eventWorkType.value!!, eventPrice.value!!, eventMileage.value!!, eventComment.value!!))
                    wrongEvent.value = false
                }
            }
        }catch (e:NullPointerException){
                wrongEvent.value = true
        }
    }

    fun getEventsForCar(){
        viewModelScope.launch {
            try {
                val carId = repository.getSelectCarId()
                val listEvent = repository.getEvents(carId)
                eventForCar.value = listEvent
            }catch (e:NullPointerException){

            }
        }
    }

    fun updateCarView(id: Long){
        viewModelScope.launch {
            updateCar = repository.getCar(id)
            carName.value = updateCar.name
            carModel.value = updateCar.model
            carMark.value = updateCar.mark
            carFuel.value = updateCar.fuel
            carMileage.value = updateCar.mileage
        }
    }

    fun updateEventView(id: Long){
        viewModelScope.launch {
            updateEvent = repository.getEvent(id)
            eventName.value = updateEvent.eventName
            eventType.value = updateEvent.eventType
            eventWorkType.value = updateEvent.eventWorkType
            eventPrice.value = updateEvent.eventPrise
            eventMileage.value = updateEvent.eventMileage
            eventComment.value = updateEvent.eventComment
        }
    }

    fun initAddOpUpdate(){
        isUpdate = true
        buttonAddOrUpdate.value = "Update"
    }

    fun deleteCar(car: Car){
        viewModelScope.launch{
            repository.deleteCar(car)
            repository.deleteEventByCar(car.carId)
        }
    }

    fun delete(){
        viewModelScope.launch{
            repository.delete()
        }
    }

    fun updateCarView(car: Car){
        viewModelScope.launch {
            repository.updateCar(car)
        }
    }

    fun getCars(){
        viewModelScope.launch {
            val car = repository.getCars()
            cars.value = car
        }
    }

    fun getCar(id:Long){
        viewModelScope.launch {
            val getCar = repository.getCar(id)
            car.value = getCar

        }
    }

    fun getSelectCarForMainView(){
        viewModelScope.launch {
            try {
                val selectCarView = repository.getSelectCar()
                carName.value = selectCarView.name
                carModel.value = selectCarView.model
                carMark.value = selectCarView.mark
                carFuel.value = selectCarView.fuel
                carMileage.value = selectCarView.mileage
            } catch (e: NullPointerException) {
                wrongVisible.value = View.VISIBLE
            }
        }
    }

    fun deleteEvent(event: Event){
        viewModelScope.launch {
            repository.deleteEvent(event)
        }
    }

    fun selectCar(id: Long){
        viewModelScope.launch {
            repository.selectCar(id)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}