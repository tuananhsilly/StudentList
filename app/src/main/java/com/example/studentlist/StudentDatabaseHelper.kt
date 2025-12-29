package com.example.studentlist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, 
    DATABASE_NAME, 
    null, 
    DATABASE_VERSION
) {
    
    companion object {
        private const val DATABASE_NAME = "student_database.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_STUDENTS = "students"
        
        // Column names
        private const val COLUMN_MSSV = "mssv"
        private const val COLUMN_FULL_NAME = "fullName"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_ADDRESS = "address"
    }
    
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_STUDENTS (
                $COLUMN_MSSV TEXT PRIMARY KEY,
                $COLUMN_FULL_NAME TEXT NOT NULL,
                $COLUMN_PHONE TEXT,
                $COLUMN_ADDRESS TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
        
        // Thêm dữ liệu mẫu
        insertSampleData(db)
    }
    
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENTS")
        onCreate(db)
    }
    
    private fun insertSampleData(db: SQLiteDatabase) {
        val students = listOf(
            Student("SV001", "Nguyễn Văn A", "0123456789", "Hà Nội"),
            Student("SV002", "Trần Thị B", "0987654321", "Hải Phòng")
        )
        
        students.forEach { student ->
            val values = ContentValues().apply {
                put(COLUMN_MSSV, student.mssv)
                put(COLUMN_FULL_NAME, student.fullName)
                put(COLUMN_PHONE, student.phone)
                put(COLUMN_ADDRESS, student.address)
            }
            db.insert(TABLE_STUDENTS, null, values)
        }
    }
    
    // CRUD Operations
    fun insertStudent(student: Student): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MSSV, student.mssv)
            put(COLUMN_FULL_NAME, student.fullName)
            put(COLUMN_PHONE, student.phone)
            put(COLUMN_ADDRESS, student.address)
        }
        return db.insert(TABLE_STUDENTS, null, values)
    }
    
    fun getAllStudents(): List<Student> {
        val students = mutableListOf<Student>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_STUDENTS,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_MSSV ASC"
        )
        
        cursor.use {
            while (it.moveToNext()) {
                val mssv = it.getString(it.getColumnIndexOrThrow(COLUMN_MSSV))
                val fullName = it.getString(it.getColumnIndexOrThrow(COLUMN_FULL_NAME))
                val phone = it.getString(it.getColumnIndexOrThrow(COLUMN_PHONE))
                val address = it.getString(it.getColumnIndexOrThrow(COLUMN_ADDRESS))
                students.add(Student(mssv, fullName, phone, address))
            }
        }
        return students
    }
    
    fun updateStudent(oldMssv: String, student: Student): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MSSV, student.mssv)
            put(COLUMN_FULL_NAME, student.fullName)
            put(COLUMN_PHONE, student.phone)
            put(COLUMN_ADDRESS, student.address)
        }
        return db.update(
            TABLE_STUDENTS,
            values,
            "$COLUMN_MSSV = ?",
            arrayOf(oldMssv)
        )
    }
    
    fun deleteStudent(mssv: String): Int {
        val db = writableDatabase
        return db.delete(
            TABLE_STUDENTS,
            "$COLUMN_MSSV = ?",
            arrayOf(mssv)
        )
    }
    
    fun getStudentByMssv(mssv: String): Student? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_STUDENTS,
            null,
            "$COLUMN_MSSV = ?",
            arrayOf(mssv),
            null,
            null,
            null
        )
        
        cursor.use {
            if (it.moveToFirst()) {
                val fullName = it.getString(it.getColumnIndexOrThrow(COLUMN_FULL_NAME))
                val phone = it.getString(it.getColumnIndexOrThrow(COLUMN_PHONE))
                val address = it.getString(it.getColumnIndexOrThrow(COLUMN_ADDRESS))
                return Student(mssv, fullName, phone, address)
            }
        }
        return null
    }
}
