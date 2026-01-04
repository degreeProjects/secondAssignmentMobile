package com.example.mykotlinapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinapp.models.Student
import com.example.mykotlinapp.dao.AppLocalDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentsAdapter(
    private val students: List<Student>
) : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    private val db by lazy { AppLocalDB.db }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int = students.size

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.studentImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.studentNameTextView)
        private val idTextView: TextView = itemView.findViewById(R.id.studentIdTextView)
        private val checkBox: CheckBox = itemView.findViewById(R.id.studentCheckBox)

        fun bind(student: Student) {
            // Use default gallery icon as placeholder
            imageView.setImageResource(android.R.drawable.ic_menu_gallery)
            nameTextView.text = student.name
            idTextView.text = "ID: ${student.id}"
            
            // Remove previous listener to avoid conflicts
            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = student.isPresent

            // Set new listener
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                student.isPresent = isChecked
                
                // Update student in database
                CoroutineScope(Dispatchers.IO).launch {
                    db.studentDao.updateStudent(student)
                }
            }
        }
    }
}
