package com.example.vktodo.presenter.utils

sealed class Screen(val destination: String) {
    object TodoList : Screen("list_screen")
    object Add : Screen("add_screen")
}
