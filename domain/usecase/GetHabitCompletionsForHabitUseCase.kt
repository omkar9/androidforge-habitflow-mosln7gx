package com.androidforge.habitflow.domain.usecase

import com.androidforge.habitflow.domain.model.HabitCompletion
import com.androidforge.habitflow.domain.repository.HabitCompletionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHabitCompletionsForHabitUseCase @Inject constructor(
    private val repository: HabitCompletionRepository
) {
    operator fun invoke(habitId: String): Flow<List<HabitCompletion>> {
        return repository.getCompletionsForHabit(habitId)
    }
}