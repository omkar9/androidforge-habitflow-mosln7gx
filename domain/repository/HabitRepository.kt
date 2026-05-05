package com.androidforge.habitflow.domain.repository

import com.androidforge.habitflow.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun insertHabit(habit: Habit)
    suspend fun updateHabit(habit: Habit)
    suspend fun deleteHabit(habit: Habit)
    fun getHabitById(id: String): Flow<Habit?>
    fun getAllHabits(): Flow<List<Habit>>
}