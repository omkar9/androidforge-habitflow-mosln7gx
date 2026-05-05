package com.androidforge.habitflow.domain.usecase

import com.androidforge.habitflow.domain.model.Habit
import com.androidforge.habitflow.domain.repository.HabitRepository
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class AddHabitUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(name: String, description: String?): Habit {
        val newHabit = Habit(
            id = UUID.randomUUID().toString(),
            name = name,
            description = description,
            creationDate = LocalDate.now(),
            lastModifiedDate = LocalDate.now()
        )
        repository.insertHabit(newHabit)
        return newHabit
    }
}