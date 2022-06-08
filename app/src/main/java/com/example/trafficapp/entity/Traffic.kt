package com.example.trafficapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Traffic(
    var image: String,
    var name: String,
    var description: String,
    var type: String,
    var like: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
