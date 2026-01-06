package com.example.mykotlinapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinapp.models.Model
import com.example.mykotlinapp.models.Student
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    
    private lateinit var studentsRecyclerView: RecyclerView
    private lateinit var studentsAdapter: StudentsAdapter
    private val studentsList = mutableListOf<Student>()
    private val model = Model.shared

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
                    address = address,
                    phoneNumber = phone
                )

                // Insert student using Model
                model.addStudent(newStudent) {
                    // Reload students from database
                    loadStudents()
                    Toast.makeText(this@MainActivity, "Student added successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        studentsRecyclerView = findViewById(R.id.studentsRecyclerView)

        // Set up RecyclerView with LinearLayoutManager
        studentsRecyclerView.layoutManager = LinearLayoutManager(this)
        studentsAdapter = StudentsAdapter(studentsList, model) { student ->
            // Handle student item click - open StudentDetailsActivity
            val intent = Intent(this, StudentDetailsActivity::class.java).apply {
                putExtra("STUDENT_ID", student.id)
                putExtra("STUDENT_NAME", student.name)
                putExtra("STUDENT_PHONE", student.phoneNumber)
                putExtra("STUDENT_ADDRESS", student.address)
                putExtra("IS_PRESENT", student.isPresent)
            }
            startActivity(intent)
        }
        studentsRecyclerView.adapter = studentsAdapter

        // Initialize database with sample data if empty, then load students
        initializeDatabaseIfNeeded()
    }

    override fun onResume() {
        super.onResume()
        // Reload students when returning to this activity
        loadStudents()
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

    private fun initializeDatabaseIfNeeded() {
        model.getAllStudents { students ->
            // If database is empty, add sample data
            if (students.isEmpty()) {
                val sampleStudents = arrayOf(
                    Student("123456", "Leo Messi"),
                    Student("234567", "Donald Trump")
                )
                
                // Add sample students one by one
                var addedCount = 0
                sampleStudents.forEach { student ->
                    model.addStudent(student) {
                        addedCount++
                        if (addedCount == sampleStudents.size) {
                            // All sample students added, now load
                            loadStudents()
                        }
                    }
                }
            } else {
                // Database has data, just load it
                loadStudents()
            }
        }
    }

    private fun loadStudents() {
        model.getAllStudents { students ->
            studentsList.clear()
            studentsList.addAll(students)
            studentsAdapter.notifyDataSetChanged()
        }
    }
}
