package com.example.studentlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView

class StudentAdapter(
    private val context: Context,
    private val students: MutableList<Student>,
    private val onDelete: (Student) -> Unit
) : ArrayAdapter<Student>(context, R.layout.item_student, students) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_student, parent, false)

        val tvName: TextView = rowView.findViewById(R.id.tvName)
        val tvId: TextView = rowView.findViewById(R.id.tvId)
        val btnDelete: ImageButton = rowView.findViewById(R.id.btnDelete)

        val student = students[position]

        tvName.text = student.name
        tvId.text = student.id

        btnDelete.setOnClickListener {
            onDelete(student)
        }

        return rowView
    }
}
