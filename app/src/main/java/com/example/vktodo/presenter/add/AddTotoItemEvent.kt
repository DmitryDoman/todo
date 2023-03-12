package com.example.vktodo.presenter.add

import java.util.Calendar
import java.util.Date

sealed interface AddTotoItemEvent {
    object Save : AddTotoItemEvent
    object ShowDateTimePicker: AddTotoItemEvent
    data class TitleChanged(val title: String) : AddTotoItemEvent
    data class DataChanged(val date: Calendar) : AddTotoItemEvent
}