package com.androidforge.habitflow.domain.usecase

import com.androidforge.habitflow.domain.model.Habit
import com.androidforge.habitflow.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHabitsUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    operator fun invoke(): Flow<List<Habit>> {
        return repository.getAllHabits()
    }
}