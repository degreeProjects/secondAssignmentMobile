package com.example.mykotlinapp

data class Student(
    val id: String,
    val name: String,
    val avatarResId: Int,
    var isChecked: Boolean = false
)


