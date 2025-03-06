package com.example.splitwise_payg.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val fullName: String,
    val emailAddress: String,
    val password: String,
    val phoneNumber: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
