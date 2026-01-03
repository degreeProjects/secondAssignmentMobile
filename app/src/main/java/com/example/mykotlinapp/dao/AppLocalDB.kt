package com.example.mykotlinapp.dao

import androidx.room.Room
import com.example.mykotlinapp.MyApplication

object AppLocalDB {

    val db: AppLocalDbRepository by lazy {

        val context = MyApplication.appContext
            ?: throw IllegalStateException("Context is null")

        Room.databaseBuilder(
            context = context,
            klass = AppLocalDbRepository::class.java,
            name = "students.db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
}