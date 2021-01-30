package com.example.mycar.dataBase.car

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CarDB")
data class Car(
    @PrimaryKey(autoGenerate = true)
    val carId:Long,
    var name:String,
    var mark:String,
    var model:String,
    var fuel:String,
    var mileage:String,
    var selectCar:Int
)