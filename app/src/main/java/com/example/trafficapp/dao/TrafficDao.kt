package com.example.trafficapp.dao

import androidx.room.*
import com.example.trafficapp.entity.Traffic
import io.reactivex.Flowable

@Dao
interface TrafficDao {
    @Insert
    fun insertTraffic(traffic: Traffic)

    @Update
    fun updateTraffic(traffic: Traffic)

    @Delete
    fun deleteTraffic(traffic: Traffic)

    @Query("select * from traffic WHERE type = :myType")
    fun getAllTraffic(myType: String): Flowable<List<Traffic>>

    @Query("select * from traffic WHERE `like` = 1")
    fun getAllLikeTraffic(): Flowable<List<Traffic>>
}