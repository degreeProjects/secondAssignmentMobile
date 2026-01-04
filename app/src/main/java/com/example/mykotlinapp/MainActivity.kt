package com.example.mykotlinapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mykotlinapp.dao.AppLocalDB
import com.example.mykotlinapp.models.Student
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    
    private lateinit var studentsListView: ListView
    private lateinit var studentsAdapter: StudentsAdapter
    private val studentsList = mutableListOf<Student>()
    private val db by lazy { AppLocalDB.db }

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

                // Create new Student object (validations already done in CreateStudentActivity)
                val newStudent = Student(
                    id = id,
                    name = name,
                    isPresent = false,
                    avatarUrlString = null,
                    address = address,
                    phoneNumber = phone
                )

                // Insert student into database
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        db.studentDao.insertStudents(newStudent)
                    }
                    
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

        studentsListView = findViewById(R.id.studentsListView)

        // Set up adapter
        studentsAdapter = StudentsAdapter(this, studentsList)
        studentsListView.adapter = studentsAdapter

        // Initialize database with sample data if empty, then load students
        lifecycleScope.launch {
            initializeDatabaseIfNeeded()
            loadStudents()
        }
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

    private suspend fun initializeDatabaseIfNeeded() {
        withContext(Dispatchers.IO) {
            val existingStudents = db.studentDao.getAllStudents()
            
            // If database is empty, add sample data
            if (existingStudents.isEmpty()) {
                val sampleStudents = arrayOf(
                    Student("123456", "Leo Messi"),
                    Student("234567", "Donald Trump"),
                )
                
                db.studentDao.insertStudents(*sampleStudents)
            }
        }
    }

    private suspend fun loadStudents() {
        val students = withContext(Dispatchers.IO) {
            db.studentDao.getAllStudents()
        }
        
        studentsList.clear()
        studentsList.addAll(students)
        studentsAdapter.notifyDataSetChanged()
    }
}
