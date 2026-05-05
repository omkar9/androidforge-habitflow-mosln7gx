package com.androidforge.habitflow.domain.model

import java.time.LocalDate

data class Habit(
    val id: String,
    val name: String,
    val description: String?,
    val creationDate: LocalDate,
    val lastModifiedDate: LocalDate
)