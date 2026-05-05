package com.androidforge.habitflow.domain.usecase

import com.androidforge.habitflow.domain.repository.HabitCompletionRepository
import java.time.LocalDate
import javax.inject.Inject

class MarkHabitCompletedUseCase @Inject constructor(
    private val repository: HabitCompletionRepository
) {
    suspend operator fun invoke(habitId: String, date: LocalDate, completed: Boolean) {
        repository.markHabitCompleted(habitId, date, completed)
    }
}