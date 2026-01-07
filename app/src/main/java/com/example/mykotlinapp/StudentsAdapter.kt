package com.example.mykotlinapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinapp.models.Model
import com.example.mykotlinapp.models.Student

class StudentsAdapter(
    private val students: List<Student>,
    private val model: Model,
    private val onItemClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    // Called by RecyclerView when it needs to create a new ViewHolder
    // This inflates the layout and creates the view holder object
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list_item, parent, false)
        return StudentViewHolder(view)
    }

    // Called by RecyclerView to display data at a specific position
    // This method is called automatically for each visible item and when scrolling
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
            // Always use the default user_icon for all students
            imageView.setImageResource(R.drawable.user_icon)
            nameTextView.text = student.name
            idTextView.text = "ID: ${student.id}"
            
            // Remove previous listener to avoid conflicts
            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = student.isPresent

            // Set new listener
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                student.isPresent = isChecked
                
                // Update student using Model (UI already updated, so callback is empty)
                model.updateStudent(student) {}
            }
            
            // Set click listener for the entire item
            itemView.setOnClickListener {
                onItemClick(student)
            }
        }
    }
}
