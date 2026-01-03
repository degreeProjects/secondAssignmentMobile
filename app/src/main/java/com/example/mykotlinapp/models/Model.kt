package com.example.mykotlinapp.models

import android.os.Handler
import android.os.Looper
import com.idz.mykotlinapp.base.Completion
import com.idz.mykotlinapp.base.StudentCompletion
import com.idz.mykotlinapp.base.StudentsCompletion
import com.idz.mykotlinapp.dao.AppLocalDB
import com.idz.mykotlinapp.dao.AppLocalDbRepository
import java.util.concurrent.Executors

class Model private constructor() {

    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = Handler.createAsync(Looper.getMainLooper())

    private val database: AppLocalDbRepository = AppLocalDB.db

    companion object {
        val shared = Model()
    }

    fun getAllStudents(completion: StudentsCompletion) {
        executor.execute {
            val students = database.studentDao.getAllStudents()
            mainHandler.post {
                completion(students)
            }
        }
    }

    fun addStudent(student: Student, completion: Completion) {
        executor.execute {
            database.studentDao.insertStudents(student)
            mainHandler.post {
                completion()
            }
        }
    }

    fun deleteStudent(student: Student, completion: Completion) {
        executor.execute {
            database.studentDao.deleteStudent(student)
            mainHandler.post {
                completion()
            }
        }
    }

    fun getStudentById(id: String, completion: StudentCompletion) {
        executor.execute {
            val student = database.studentDao.getStudentById(id)
            mainHandler.post {
                completion(student)
            }
        }
    }
}