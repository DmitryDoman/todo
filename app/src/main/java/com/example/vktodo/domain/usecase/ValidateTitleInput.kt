package com.example.vktodo.domain.usecase

class ValidateTitleInput {
    operator fun invoke(title: String): Boolean {
        return title.isNotBlank()
    }
}