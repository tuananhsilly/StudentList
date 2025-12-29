package com.example.studentlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    var mssv: String,
    var fullName: String,
    var phone: String,
    var address: String
) : Parcelable