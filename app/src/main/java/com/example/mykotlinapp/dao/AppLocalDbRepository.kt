package com.example.mykotlinapp.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.idz.mykotlinapp.models.Student

@Database(entities = [Student::class], version = 2)
abstract class AppLocalDbRepository: RoomDatabase() {
    abstract val studentDao: StudentDao
}