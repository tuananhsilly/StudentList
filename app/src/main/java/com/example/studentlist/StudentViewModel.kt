package com.example.studentlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class StudentViewModel(application: Application) : AndroidViewModel(application) {
    
    private val databaseHelper = StudentDatabaseHelper(application)
    
    private val _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> = _students
    
    init {
        loadStudents()
    }
    
    private fun loadStudents() {
        _students.value = databaseHelper.getAllStudents()
    }
    
    fun addStudent(student: Student) {
        databaseHelper.insertStudent(student)
        loadStudents()
    }
    
    fun updateStudent(position: Int, student: Student) {
        val currentList = _students.value ?: return
        if (position in currentList.indices) {
            val oldStudent = currentList[position]
            databaseHelper.updateStudent(oldStudent.mssv, student)
            loadStudents()
        }
    }
    
    fun deleteStudent(position: Int) {
        val currentList = _students.value ?: return
        if (position in currentList.indices) {
            val student = currentList[position]
            databaseHelper.deleteStudent(student.mssv)
            loadStudents()
        }
    }
    
    fun getStudent(position: Int): Student? {
        return _students.value?.getOrNull(position)
    }
}