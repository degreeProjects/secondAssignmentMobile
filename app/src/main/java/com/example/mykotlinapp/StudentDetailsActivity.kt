package com.example.mykotlinapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinapp.models.Model
import com.example.mykotlinapp.models.Student
import com.google.android.material.appbar.MaterialToolbar

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var studentDetailsPicture: ImageView
    private lateinit var studentDetailsId: TextView
    private lateinit var studentDetailsName: TextView
    private lateinit var studentDetailsPhone: TextView
    private lateinit var studentDetailsAddress: TextView
    private lateinit var studentDetailsIsPresent: CheckBox
    private lateinit var btnEdit: Button
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

                // Insert new student using Model
                model.addStudent(newStudent) {
                    Toast.makeText(this@StudentDetailsActivity, "Student added successfully", Toast.LENGTH_SHORT).show()
                    // Go back to MainActivity to see the updated list
                    finish()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        // Set up toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize views
        studentDetailsPicture = findViewById(R.id.studentDetailsPicture)
        studentDetailsId = findViewById(R.id.studentDetailsId)
        studentDetailsName = findViewById(R.id.studentDetailsName)
        studentDetailsPhone = findViewById(R.id.studentDetailsPhone)
        studentDetailsAddress = findViewById(R.id.studentDetailsAddress)
        studentDetailsIsPresent = findViewById(R.id.studentDetailsIsPresent)
        btnEdit = findViewById(R.id.btnEdit)

        // Retrieve student data from Intent extras
        val studentId = intent.getStringExtra("STUDENT_ID") ?: ""
        val studentName = intent.getStringExtra("STUDENT_NAME") ?: ""
        val studentPhone = intent.getStringExtra("STUDENT_PHONE")
        val studentAddress = intent.getStringExtra("STUDENT_ADDRESS")
        val isPresent = intent.getBooleanExtra("IS_PRESENT", false)

        // Populate UI fields with student data
        studentDetailsId.text = studentId
        studentDetailsName.text = studentName
        studentDetailsPhone.text = studentPhone ?: "N/A"
        studentDetailsAddress.text = studentAddress ?: "N/A"
        studentDetailsIsPresent.isChecked = isPresent

        // Set up Edit button click listener
        btnEdit.setOnClickListener {
            // Launch EditStudentActivity with student data
            val intent = Intent(this, EditStudentActivity::class.java).apply {
                putExtra("STUDENT_ID", studentId)
                putExtra("STUDENT_NAME", studentName)
                putExtra("STUDENT_PHONE", studentPhone)
                putExtra("STUDENT_ADDRESS", studentAddress)
                putExtra("IS_PRESENT", isPresent)
            }
            startActivity(intent)
            finish() // Close current details activity
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_student_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_student -> {
                // Launch CreateStudentActivity when plus icon is clicked
                val intent = Intent(this, CreateStudentActivity::class.java)
                createStudentLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

