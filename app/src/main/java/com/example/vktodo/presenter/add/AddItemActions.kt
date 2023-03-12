package com.example.vktodo.presenter.add

sealed interface AddItemActions {
    object NavigateUp: AddItemActions
    object OpenDateTimePicker: AddItemActions
}