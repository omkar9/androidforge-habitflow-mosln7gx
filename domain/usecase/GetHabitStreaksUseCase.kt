package com.androidforge.habitflow.domain.usecase

import com.androidforge.habitflow.domain.model.HabitCompletion
import com.androidforge.habitflow.domain.repository.HabitCompletionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetHabitStreaksUseCase @Inject constructor(
    private val repository: HabitCompletionRepository
) {
    /**
     * Calculates the current and longest streaks for a given habit.
     * A streak is defined as consecutive daily completions up to the current date or the last completion date.
     * Returns a Pair of (currentStreak, longestStreak).
     */
    operator fun invoke(habitId: String): Flow<Pair<Int, Int>> {
        return repository.getCompletionsForHabit(habitId).map {
            calculateStreaks(it)
        }
    }

    private fun calculateStreaks(completions: List<HabitCompletion>): Pair<Int, Int> {
        if (completions.isEmpty()) return 0 to 0

        val sortedDates = completions.map { it.completionDate }.sortedDescending()
        if (sortedDates.isEmpty()) return 0 to 0

        var currentStreak = 0
        var longestStreak = 0
        var tempStreak = 0

        val today = LocalDate.now()
        var previousDate: LocalDate? = null

        // Check if the habit was completed today or yesterday to start current streak calculation
        val lastCompletionDate = sortedDates.first()
        val isCompletedTodayOrYesterday = lastCompletionDate == today || lastCompletionDate == today.minusDays(1)

        // Calculate current streak only if the habit was completed today or yesterday
        if (isCompletedTodayOrYesterday) {
            var checkDate = today
            while (sortedDates.contains(checkDate)) {
                currentStreak++
                checkDate = checkDate.minusDays(1)
            }
        }

        // Calculate longest streak
        for (date in sortedDates.sorted()) { // Iterate in ascending order for longest streak
            if (previousDate == null || date == previousDate.plusDays(1)) {
                tempStreak++
            } else {
                longestStreak = maxOf(longestStreak, tempStreak)
                tempStreak = 1
            }
            previousDate = date
        }
        longestStreak = maxOf(longestStreak, tempStreak)

        return currentStreak to longestStreak
    }
}