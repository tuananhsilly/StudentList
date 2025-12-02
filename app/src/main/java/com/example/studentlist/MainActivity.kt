package com.example.studentlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_ADD = 100
        const val REQUEST_EDIT = 101

        const val EXTRA_STUDENT = "extra_student"
        const val EXTRA_POSITION = "extra_position"
    }

    private lateinit var lvStudents: ListView
    private lateinit var adapter: StudentAdapter
    private val students = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvStudents = findViewById(R.id.lvStudents)

        // Dữ liệu mẫu ban đầu (có thể bỏ nếu bạn đã load từ nơi khác)
        students.add(Student("SV001", "Nguyễn Văn A", "0123456789", "Hà Nội"))
        students.add(Student("SV002", "Trần Thị B", "0987654321", "Hải Phòng"))

        adapter = StudentAdapter(this, students)
        lvStudents.adapter = adapter

        // Nhấn vào 1 sinh viên -> mở màn hình chi tiết
        lvStudents.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, StudentDetailActivity::class.java)
            intent.putExtra(EXTRA_STUDENT, students[position])
            intent.putExtra(EXTRA_POSITION, position)
            startActivityForResult(intent, REQUEST_EDIT)
        }
    }

    // Tạo option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Xử lý chọn item menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_student -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Nhận kết quả trả về từ AddStudentActivity & StudentDetailActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK || data == null) return

        when (requestCode) {
            REQUEST_ADD -> {
                val newStudent =
                    data.getSerializableExtra(EXTRA_STUDENT) as? Student ?: return
                students.add(newStudent)
                adapter.notifyDataSetChanged()
            }
            REQUEST_EDIT -> {
                val updatedStudent =
                    data.getSerializableExtra(EXTRA_STUDENT) as? Student ?: return
                val position = data.getIntExtra(EXTRA_POSITION, -1)
                if (position in students.indices) {
                    students[position] = updatedStudent
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}
