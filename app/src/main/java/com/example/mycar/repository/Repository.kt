package com.example.mycar.repository

import com.example.mycar.dataBase.car.Car
import com.example.mycar.dataBase.car.CarDao
import com.example.mycar.dataBase.event.Event
import com.example.mycar.dataBase.event.EventDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class Repository (private val dao: CarDao, private val daoEvent: EventDao) {

    suspend fun insertCar(car: Car){
            dao.insertCar(car)
    }

    suspend fun deleteCar(car: Car){
        dao.deleteCar(car)
    }

    suspend fun delete(){
        dao.delete()
    }

    suspend fun updateCar(car: Car){
        dao.updateCar(car)
    }

    suspend fun getCars() = dao.getCars()


    suspend fun getCar(id:Long) = dao.getCar(id)


    suspend fun getSelectCarId() = dao.getSelectCarId()

    suspend fun getSelectCar() = dao.getSelectCar()

    suspend fun selectCar(id:Long){
        withContext(Dispatchers.Default) {
         val a = async {    dao.clearSelectCar() }
          a.await()
          dao.selectCar(id)
        }
    }

    suspend fun insertEvent(event: Event){
        daoEvent.insert(event)
    }

    suspend fun deleteEvent(event: Event){
        daoEvent.delete(event)
    }

    suspend fun updateEvent(event: Event){
        daoEvent.update(event)
    }

    suspend fun getEvents(id: Long) = daoEvent.getEvents(id)


    suspend fun getEvent(id: Long) = daoEvent.getEvent(id)

    suspend fun deleteEventByCar(id: Long){
        daoEvent.deleteEventsForCar(id)
    }

}