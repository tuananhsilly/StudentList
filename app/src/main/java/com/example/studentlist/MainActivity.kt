package com.example.studentlist

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etId: EditText
    private lateinit var etName: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var lv: ListView

    private lateinit var adapter: StudentAdapter
    private val students = mutableListOf<Student>()

    private var selectedIndex = -1   // index of selected student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etId = findViewById(R.id.etId)
        etName = findViewById(R.id.etName)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        lv = findViewById(R.id.lvStudents)

        // Adapter CHUẨN – dùng Student list
        adapter = StudentAdapter(this, students) { student ->
            students.remove(student)
            adapter.notifyDataSetChanged()
        }
        lv.adapter = adapter

        // ----------------------
        // ADD
        // ----------------------
        btnAdd.setOnClickListener {
            val id = etId.text.toString()
            val name = etName.text.toString()

            if (id.isBlank() || name.isBlank()) {
                Toast.makeText(this, "Please enter ID & Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            students.add(Student(id, name))
            adapter.notifyDataSetChanged()
            clearInput()
        }

        // ----------------------
        // SELECT TO EDIT
        // ----------------------
        lv.setOnItemClickListener { _, _, position, _ ->
            val s = students[position]
            etId.setText(s.id)
            etName.setText(s.name)
            selectedIndex = position
        }

        // ----------------------
        // UPDATE
        // ----------------------
        btnUpdate.setOnClickListener {
            if (selectedIndex == -1) {
                Toast.makeText(this, "Please select a student first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newId = etId.text.toString()
            val newName = etName.text.toString()

            if (newId.isBlank() || newName.isBlank()) {
                Toast.makeText(this, "Enter ID & Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            students[selectedIndex].id = newId
            students[selectedIndex].name = newName

            adapter.notifyDataSetChanged()
            clearInput()
            selectedIndex = -1
        }

        // ----------------------
        // DELETE (Long press row)
        // ----------------------
        lv.setOnItemLongClickListener { _, _, position, _ ->
            students.removeAt(position)
            adapter.notifyDataSetChanged()
            true
        }
    }

    private fun clearInput() {
        etId.setText("")
        etName.setText("")
    }
}
