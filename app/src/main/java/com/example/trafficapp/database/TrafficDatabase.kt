package com.example.trafficapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trafficapp.dao.TrafficDao
import com.example.trafficapp.entity.Traffic

@Database(entities = [Traffic::class], version = 1, exportSchema = false)
abstract class TrafficDatabase : RoomDatabase() {
    abstract fun trafficDao(): TrafficDao

    companion object {
        private var instance: TrafficDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TrafficDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, TrafficDatabase::class.java, "traffic_table")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
            }

            return instance!!
        }
    }
}