package com.example.mykotlinapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinapp.models.Model
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText

class CreateStudentActivity : AppCompatActivity() {

    private lateinit var createStudentId: TextInputEditText
    private lateinit var createStudentName: TextInputEditText
    private lateinit var createStudentPhone: TextInputEditText
    private lateinit var createStudentAddress: TextInputEditText
    private lateinit var createStudentBtn: Button
    private val model = Model.shared

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_student)

        // Set up toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize views
        createStudentId = findViewById(R.id.createStudentId)
        createStudentName = findViewById(R.id.createStudentName)
        createStudentPhone = findViewById(R.id.createStudentPhone)
        createStudentAddress = findViewById(R.id.createStudentAddress)
        createStudentBtn = findViewById(R.id.createStudentBtn)

        // Set up Save button click listener
        createStudentBtn.setOnClickListener {
            saveStudent()
        }
    }

    private fun saveStudent() {
        // Get input values
        val studentId = createStudentId.text.toString().trim()
        val studentName = createStudentName.text.toString().trim()
        val studentPhone = createStudentPhone.text.toString().trim()
        val studentAddress = createStudentAddress.text.toString().trim()

        // Validate required fields
        var isValid = true

        //check if Id is empty
        if (studentId.isEmpty()) {
            createStudentId.error = "Student ID is required"
            createStudentId.requestFocus()
            isValid = false
        }

        //check if Name is empty
        if (studentName.isEmpty()) {
            createStudentName.error = "Name is required"
            createStudentName.requestFocus()
            isValid = false
        }

        // If validation passes, check for duplicate ID
        if (isValid) {
            model.getStudentById(studentId) { existingStudent ->
                if (existingStudent != null) {
                    // Student with this ID already exists - show inline error
                    createStudentId.error = "This ID already exists"
                    createStudentId.requestFocus()
                } else {
                    // ID is unique, return the data
                    val resultIntent = Intent()
                    resultIntent.putExtra("STUDENT_ID", studentId)
                    resultIntent.putExtra("STUDENT_NAME", studentName)
                    resultIntent.putExtra("STUDENT_PHONE", studentPhone.ifEmpty { null })
                    resultIntent.putExtra("STUDENT_ADDRESS", studentAddress.ifEmpty { null })

                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

