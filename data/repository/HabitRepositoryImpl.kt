package com.androidforge.habitflow.data.repository

import com.androidforge.habitflow.data.local.dao.HabitDao
import com.androidforge.habitflow.data.local.dao.HabitCompletionDao
import com.androidforge.habitflow.data.local.entity.toEntity
import com.androidforge.habitflow.domain.model.Habit
import com.androidforge.habitflow.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao,
    private val habitCompletionDao: HabitCompletionDao // Injected here to handle cascade delete for completions
) : HabitRepository {

    override suspend fun insertHabit(habit: Habit) {
        habitDao.insertHabit(habit.toEntity())
    }

    override suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit.toEntity())
    }

    override suspend fun deleteHabit(habit: Habit) {
        // Room's ForeignKey.CASCADE should handle completions, but explicit deletion for clarity/safety.
        // If not using CASCADE, uncomment the line below:
        habitCompletionDao.deleteAllCompletionsForHabit(habit.id)
        habitDao.deleteHabit(habit.toEntity())
    }

    override fun getHabitById(id: String): Flow<Habit?> {
        return habitDao.getHabitById(id).map { it?.toDomain() }
    }

    override fun getAllHabits(): Flow<List<Habit>> {
        return habitDao.getAllHabits().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}