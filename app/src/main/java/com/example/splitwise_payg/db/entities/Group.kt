package com.example.splitwise_payg.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
