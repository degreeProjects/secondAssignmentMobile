package com.example.mykotlinapp

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinapp.models.Student

class MainActivity : AppCompatActivity() {
    
    private lateinit var studentsListView: ListView
    private lateinit var studentsAdapter: StudentsAdapter
    private val studentsList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentsListView = findViewById(R.id.studentsListView)

        // Initialize sample students data
        initializeStudents()

        // Set up adapter
        studentsAdapter = StudentsAdapter(this, studentsList)
        studentsListView.adapter = studentsAdapter
    }

    private fun initializeStudents() {
        studentsList.apply {
            add(Student("123456", "John Smith", false, null))
            add(Student("234567", "Emma Johnson", false, null))
            add(Student("345678", "Michael Brown", false, null))
            add(Student("456789", "Sophia Davis", false, null))
            add(Student("567890", "William Wilson", false, null))
            add(Student("678901", "Olivia Martinez", false, null))
            add(Student("789012", "James Anderson", false, null))
            add(Student("890123", "Isabella Taylor", false, null))
            add(Student("901234", "Benjamin Thomas", false, null))
            add(Student("012345", "Mia Garcia", false, null))
            add(Student("112233", "Daniel Rodriguez", false, null))
            add(Student("223344", "Ava Hernandez", false, null))
            add(Student("334455", "Matthew Moore", false, null))
            add(Student("445566", "Charlotte Martin", false, null))
            add(Student("556677", "David Lee", false, null))
            add(Student("667788", "Amelia White", false, null))
            add(Student("778899", "Joseph Harris", false, null))
            add(Student("889900", "Emily Clark", false, null))
            add(Student("990011", "Andrew Lewis", false, null))
            add(Student("101112", "Abigail Walker", false, null))
        }
    }
}
