package com.example.studentlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StudentDetailActivity : AppCompatActivity() {

    private lateinit var edtMssv: EditText
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private lateinit var edtAddress: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnBack: Button

    private var position: Int = -1
    private var student: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        edtMssv = findViewById(R.id.edtMssv)
        edtName = findViewById(R.id.edtName)
        edtPhone = findViewById(R.id.edtPhone)
        edtAddress = findViewById(R.id.edtAddress)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnBack = findViewById(R.id.btnBack)

        student = intent.getSerializableExtra(MainActivity.EXTRA_STUDENT) as? Student
        position = intent.getIntExtra(MainActivity.EXTRA_POSITION, -1)

        student?.let {
            edtMssv.setText(it.mssv)
            edtName.setText(it.fullName)
            edtPhone.setText(it.phone)
            edtAddress.setText(it.address)
        }

        btnUpdate.setOnClickListener {
            if (student != null && validateInput()) {
                val updated = student!!.copy(
                    mssv = edtMssv.text.toString().trim(),
                    fullName = edtName.text.toString().trim(),
                    phone = edtPhone.text.toString().trim(),
                    address = edtAddress.text.toString().trim()
                )

                val resultIntent = Intent()
                resultIntent.putExtra(MainActivity.EXTRA_STUDENT, updated)
                resultIntent.putExtra(MainActivity.EXTRA_POSITION, position)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        btnBack.setOnClickListener {
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
