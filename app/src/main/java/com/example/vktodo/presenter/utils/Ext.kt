package com.example.vktodo.presenter.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun Calendar.asFormattedString(): String = SimpleDateFormat("dd.MM.yyyy HH:mm")
    .format(Date(this.timeInMillis))

@SuppressLint("SimpleDateFormat")
fun Long.asFormattedString(): String = SimpleDateFormat("dd.MM.yyyy HH:mm")
    .format(Date(this))