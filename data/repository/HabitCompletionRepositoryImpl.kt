package com.androidforge.habitflow.data.repository

import com.androidforge.habitflow.data.local.dao.HabitCompletionDao
import com.androidforge.habitflow.data.local.entity.HabitCompletionEntity
import com.androidforge.habitflow.domain.model.HabitCompletion
import com.androidforge.habitflow.domain.repository.HabitCompletionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitCompletionRepositoryImpl @Inject constructor(
    private val habitCompletionDao: HabitCompletionDao
) : HabitCompletionRepository {

    override suspend fun markHabitCompleted(habitId: String, date: LocalDate, completed: Boolean) {
        if (completed) {
            habitCompletionDao.insertCompletion(HabitCompletionEntity(habitId, date))
        } else {
            val existingCompletion = habitCompletionDao.getCompletionForDate(habitId, date)
            existingCompletion?.let { habitCompletionDao.deleteCompletion(it) }
        }
    }

    override fun getCompletionsForHabit(habitId: String): Flow<List<HabitCompletion>> {
        return habitCompletionDao.getCompletionsForHabit(habitId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun isHabitCompletedOnDate(habitId: String, date: LocalDate): Flow<Boolean> {
        return habitCompletionDao.getCompletionsForHabit(habitId).map {
            it.any { completion -> completion.completionDate == date }
        }
    }
}