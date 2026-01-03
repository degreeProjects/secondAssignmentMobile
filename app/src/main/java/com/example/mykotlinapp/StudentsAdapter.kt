package com.example.mykotlinapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class StudentsAdapter(
    private val context: Context,
    private val students: List<Student>
) : BaseAdapter() {

    override fun getCount(): Int = students.size

    override fun getItem(position: Int): Student = students[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.student_list_item, parent, false)
            holder = ViewHolder(
                view.findViewById(R.id.studentImageView),
                view.findViewById(R.id.studentNameTextView),
                view.findViewById(R.id.studentIdTextView),
                view.findViewById(R.id.studentCheckBox)
            )
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val student = getItem(position)

        holder.imageView.setImageResource(student.avatarResId)
        holder.nameTextView.text = student.name
        holder.idTextView.text = "ID: ${student.id}"
        
        // Remove previous listener to avoid conflicts
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = student.isChecked

        // Set new listener
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            student.isChecked = isChecked
        }

        return view
    }

    private data class ViewHolder(
        val imageView: ImageView,
        val nameTextView: TextView,
        val idTextView: TextView,
        val checkBox: CheckBox
    )
}


