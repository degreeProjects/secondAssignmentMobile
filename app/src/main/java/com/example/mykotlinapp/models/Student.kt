package com.example.mykotlinapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(

    @PrimaryKey
    val id: String,
    
    val name: String,
    var isPresent: Boolean = false,
    val address: String? = null,
    val phoneNumber: String? = null,
)