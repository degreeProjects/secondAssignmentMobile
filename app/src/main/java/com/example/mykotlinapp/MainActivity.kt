package com.example.mykotlinapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinapp.models.Student
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    
    private lateinit var studentsListView: ListView
    private lateinit var studentsAdapter: StudentsAdapter
    private val studentsList = mutableListOf<Student>()

    // Activity result launcher for CreateStudentActivity
    private val createStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                val id = data.getStringExtra("STUDENT_ID") ?: return@let
                val name = data.getStringExtra("STUDENT_NAME") ?: return@let
                val phone = data.getStringExtra("STUDENT_PHONE")
                val address = data.getStringExtra("STUDENT_ADDRESS")

                // Create new Student object
                val newStudent = Student(
                    id = id,
                    name = name,
                    isPresent = false,
                    avatarUrlString = null,
                    address = address,
                    phoneNumber = phone
                )

                // Add to list and notify adapter
                studentsList.add(newStudent)
                studentsAdapter.notifyDataSetChanged()
                
                Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        studentsListView = findViewById(R.id.studentsListView)

        // Initialize sample students data
        initializeStudents()

        // Set up adapter
        studentsAdapter = StudentsAdapter(this, studentsList)
        studentsListView.adapter = studentsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_student -> {
                // Launch CreateStudentActivity
                val intent = Intent(this, CreateStudentActivity::class.java)
                createStudentLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializeStudents() {
        studentsList.apply {
            add(Student("123456", "John Smith"))
            add(Student("234567", "Emma Johnson"))
            add(Student("345678", "Michael Brown"))
            add(Student("456789", "Sophia Davis"))
            add(Student("567890", "William Wilson"))
            add(Student("678901", "Olivia Martinez"))
            add(Student("789012", "James Anderson"))
            add(Student("890123", "Isabella Taylor"))
        }
    }
}
