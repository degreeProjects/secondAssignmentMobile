package com.example.mykotlinapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText

class CreateStudentActivity : AppCompatActivity() {

    private lateinit var etStudentId: TextInputEditText
    private lateinit var etStudentName: TextInputEditText
    private lateinit var etStudentPhone: TextInputEditText
    private lateinit var etStudentAddress: TextInputEditText
    private lateinit var btnSaveStudent: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_student)

        // Set up toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize views
        etStudentId = findViewById(R.id.etStudentId)
        etStudentName = findViewById(R.id.etStudentName)
        etStudentPhone = findViewById(R.id.etStudentPhone)
        etStudentAddress = findViewById(R.id.etStudentAddress)
        btnSaveStudent = findViewById(R.id.btnSaveStudent)

        // Set up Save button click listener
        btnSaveStudent.setOnClickListener {
            saveStudent()
        }
    }

    private fun saveStudent() {
        // Get input values
        val studentId = etStudentId.text.toString().trim()
        val studentName = etStudentName.text.toString().trim()
        val studentPhone = etStudentPhone.text.toString().trim()
        val studentAddress = etStudentAddress.text.toString().trim()

        // Validate required fields
        var isValid = true

        if (studentId.isEmpty()) {
            etStudentId.error = "Student ID is required"
            etStudentId.requestFocus()
            isValid = false
        }

        if (studentName.isEmpty()) {
            etStudentName.error = "Name is required"
            etStudentName.requestFocus()
            isValid = false
        }

        // If validation passes, return the data
        if (isValid) {
            val resultIntent = Intent()
            resultIntent.putExtra("STUDENT_ID", studentId)
            resultIntent.putExtra("STUDENT_NAME", studentName)
            resultIntent.putExtra("STUDENT_PHONE", studentPhone.ifEmpty { null })
            resultIntent.putExtra("STUDENT_ADDRESS", studentAddress.ifEmpty { null })

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

