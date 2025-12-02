package com.example.studentlist

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class AddStudentActivity : AppCompatActivity() {

    private lateinit var edtMssv: EditText
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private lateinit var edtAddress: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        edtMssv = findViewById(R.id.edtMssv)
        edtName = findViewById(R.id.edtName)
        edtPhone = findViewById(R.id.edtPhone)
        edtAddress = findViewById(R.id.edtAddress)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        btnSave.setOnClickListener {
            if (validateInput()) {
                val student = Student(
                    edtMssv.text.toString().trim(),
                    edtName.text.toString().trim(),
                    edtPhone.text.toString().trim(),
                    edtAddress.text.toString().trim()
                )
                val resultIntent = Intent()
                resultIntent.putExtra(MainActivity.EXTRA_STUDENT, student)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun validateInput(): Boolean {
        if (edtMssv.text.isNullOrBlank() || edtName.text.isNullOrBlank()) {
            Toast.makeText(this, "MSSV và Họ tên không được để trống", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
