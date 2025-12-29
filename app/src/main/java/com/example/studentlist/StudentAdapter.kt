package com.example.studentlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView

class StudentAdapter(
    context: Context,
    private val students: List<Student>,
    private val onDeleteClick: (Int) -> Unit
) : ArrayAdapter<Student>(context, 0, students) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_student, parent, false)

        val tvMssv = rowView.findViewById<TextView>(R.id.tvMssv)
        val tvName = rowView.findViewById<TextView>(R.id.tvName)
        val btnDelete = rowView.findViewById<ImageButton>(R.id.btnDelete)

        val student = students[position]

        tvMssv.text = student.mssv
        tvName.text = student.fullName
        
        btnDelete.setOnClickListener {
            onDeleteClick(position)
        }

        return rowView
    }
}
