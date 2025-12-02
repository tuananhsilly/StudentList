package com.example.studentlist

import java.io.Serializable

data class Student(
    var mssv: String,
    var fullName: String,
    var phone: String,
    var address: String
) : Serializable