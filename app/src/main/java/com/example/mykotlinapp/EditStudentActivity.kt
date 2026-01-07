package com.example.mykotlinapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinapp.models.Model
import com.example.mykotlinapp.models.Student
import com.google.android.material.appbar.MaterialToolbar

class EditStudentActivity : AppCompatActivity() {

    private lateinit var editStudentPicture: ImageView
    private lateinit var editStudentId: EditText
    private lateinit var editStudentName: EditText
    private lateinit var editStudentPhone: EditText
    private lateinit var editStudentAddress: EditText
    private lateinit var editStudentIsPresent: CheckBox
    private lateinit var editStudentBtnCancel: Button
    private lateinit var editStudentBtnDelete: Button
    private lateinit var editStudentBtnSave: Button
    private val model = Model.shared

    private var originalStudentId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        // Set up toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize views
        editStudentPicture = findViewById(R.id.editStudentPicture)
        editStudentId = findViewById(R.id.editStudentId)
        editStudentName = findViewById(R.id.editStudentName)
        editStudentPhone = findViewById(R.id.editStudentPhone)
        editStudentAddress = findViewById(R.id.editStudentAddress)
        editStudentIsPresent = findViewById(R.id.editStudentIsPresent)
        editStudentBtnCancel = findViewById(R.id.editStudentBtnCancel)
        editStudentBtnDelete = findViewById(R.id.editStudentBtnDelete)
        editStudentBtnSave = findViewById(R.id.editStudentBtnSave)

        // Retrieve student data from Intent extras and store original ID
        originalStudentId = intent.getStringExtra("STUDENT_ID") ?: ""
        val studentName = intent.getStringExtra("STUDENT_NAME") ?: ""
        val studentPhone = intent.getStringExtra("STUDENT_PHONE")
        val studentAddress = intent.getStringExtra("STUDENT_ADDRESS")
        val isPresent = intent.getBooleanExtra("IS_PRESENT", false)

        // Populate UI fields with student data
        editStudentId.setText(originalStudentId)
        editStudentName.setText(studentName)
        editStudentPhone.setText(studentPhone ?: "")
        editStudentAddress.setText(studentAddress ?: "")
        editStudentIsPresent.isChecked = isPresent

        // Set up button click listeners

        //Set up cancel button click listener
        editStudentBtnCancel.setOnClickListener {
            finish()
        }

        editStudentBtnDelete.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        editStudentBtnSave.setOnClickListener {
            saveStudent()
        }
    }

    private fun saveStudent() {
        // Get updated values
        val newId = editStudentId.text.toString().trim()
        val name = editStudentName.text.toString().trim()
        val phone = editStudentPhone.text.toString().trim()
        val address = editStudentAddress.text.toString().trim()
        val isPresent = editStudentIsPresent.isChecked

        // Validate required fields
        if (newId.isEmpty()) {
            editStudentId.error = "Student ID is required"
            editStudentId.requestFocus()
            return
        }

        if (name.isEmpty()) {
            editStudentName.error = "Name is required"
            editStudentName.requestFocus()
            return
        }

        // Check if ID was changed
        if (newId != originalStudentId) {
            // ID changed - need to check if new ID already exists
            model.getStudentById(newId) { existingStudent ->
                if (existingStudent != null) {
                    // New ID already exists
                    editStudentId.error = "This ID already exists"
                    editStudentId.requestFocus()
                } else {
                    // New ID is available - delete old student and create new one
                    updateStudentWithNewId(newId, name, phone, address, isPresent)
                }
            }
        } else {
            // ID unchanged - just update the student
            updateStudentInPlace(newId, name, phone, address, isPresent)
        }
    }

    private fun updateStudentWithNewId(newId: String, name: String, phone: String, address: String, isPresent: Boolean) {
        // Delete the old student first
        model.getStudentById(originalStudentId) { oldStudent ->
            if (oldStudent != null) {
                model.deleteStudent(oldStudent) {
                    // Now create new student with new ID
                    val newStudent = Student(
                        id = newId,
                        name = name,
                        isPresent = isPresent,
                        address = address.ifEmpty { null },
                        phoneNumber = phone.ifEmpty { null }
                    )
                    
                    model.addStudent(newStudent) {
                        Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show()
                        navigateToStudentDetails(newStudent)
                    }
                }
            }
        }
    }

    private fun updateStudentInPlace(id: String, name: String, phone: String, address: String, isPresent: Boolean) {
        val updatedStudent = Student(
            id = id,
            name = name,
            isPresent = isPresent,
            address = address.ifEmpty { null },
            phoneNumber = phone.ifEmpty { null }
        )

        model.updateStudent(updatedStudent) {
            Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show()
            navigateToStudentDetails(updatedStudent)
        }
    }

    private fun navigateToStudentDetails(student: Student) {
        val intent = Intent(this, StudentDetailsActivity::class.java).apply {
            putExtra("STUDENT_ID", student.id)
            putExtra("STUDENT_NAME", student.name)
            putExtra("STUDENT_PHONE", student.phoneNumber)
            putExtra("STUDENT_ADDRESS", student.address)
            putExtra("IS_PRESENT", student.isPresent)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        finish()
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Delete") { _, _ ->
                deleteStudent()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteStudent() {
        // Always delete using the ORIGINAL ID that was passed to this activity
        model.getStudentById(originalStudentId) { student ->
            if (student != null) {
                model.deleteStudent(student) {
                    Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show()
                    
                    // Navigate to MainActivity and clear backstack
                    val intent = Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, "Error: Student not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

