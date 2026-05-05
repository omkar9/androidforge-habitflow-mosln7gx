package com.androidforge.habitflow.domain.repository

import com.androidforge.habitflow.domain.model.HabitCompletion
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HabitCompletionRepository {
    suspend fun markHabitCompleted(habitId: String, date: LocalDate, completed: Boolean)
    fun getCompletionsForHabit(habitId: String): Flow<List<HabitCompletion>>
    fun isHabitCompletedOnDate(habitId: String, date: LocalDate): Flow<Boolean>
}