package com.androidforge.habitflow.domain.usecase

import com.androidforge.habitflow.domain.repository.HabitCompletionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class IsHabitCompletedOnDateUseCase @Inject constructor(
    private val repository: HabitCompletionRepository
) {
    operator fun invoke(habitId: String, date: LocalDate): Flow<Boolean> {
        return repository.isHabitCompletedOnDate(habitId, date)
    }
}