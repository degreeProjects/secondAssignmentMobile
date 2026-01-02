package com.example.mykotlinapp

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

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
            add(Student("123456", "John Smith", android.R.drawable.ic_menu_gallery, false))
            add(Student("234567", "Emma Johnson", android.R.drawable.ic_menu_gallery, false))
            add(Student("345678", "Michael Brown", android.R.drawable.ic_menu_gallery, false))
            add(Student("456789", "Sophia Davis", android.R.drawable.ic_menu_gallery, false))
            add(Student("567890", "William Wilson", android.R.drawable.ic_menu_gallery, false))
            add(Student("678901", "Olivia Martinez", android.R.drawable.ic_menu_gallery, false))
            add(Student("789012", "James Anderson", android.R.drawable.ic_menu_gallery, false))
            add(Student("890123", "Isabella Taylor", android.R.drawable.ic_menu_gallery, false))
            add(Student("901234", "Benjamin Thomas", android.R.drawable.ic_menu_gallery, false))
            add(Student("012345", "Mia Garcia", android.R.drawable.ic_menu_gallery, false))
            add(Student("112233", "Daniel Rodriguez", android.R.drawable.ic_menu_gallery, false))
            add(Student("223344", "Ava Hernandez", android.R.drawable.ic_menu_gallery, false))
            add(Student("334455", "Matthew Moore", android.R.drawable.ic_menu_gallery, false))
            add(Student("445566", "Charlotte Martin", android.R.drawable.ic_menu_gallery, false))
            add(Student("556677", "David Lee", android.R.drawable.ic_menu_gallery, false))
            add(Student("667788", "Amelia White", android.R.drawable.ic_menu_gallery, false))
            add(Student("778899", "Joseph Harris", android.R.drawable.ic_menu_gallery, false))
            add(Student("889900", "Emily Clark", android.R.drawable.ic_menu_gallery, false))
            add(Student("990011", "Andrew Lewis", android.R.drawable.ic_menu_gallery, false))
            add(Student("101112", "Abigail Walker", android.R.drawable.ic_menu_gallery, false))
        }
    }
}
