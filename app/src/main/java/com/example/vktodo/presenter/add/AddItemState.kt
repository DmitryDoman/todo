package com.example.vktodo.presenter.add

import java.util.Calendar

data class AddItemState(
    val title: String = "",
    val date: Calendar = Calendar.getInstance(),
    val isSaveEnabled: Boolean = false
)