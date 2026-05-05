package com.androidforge.habitflow.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.androidforge.habitflow.domain.model.HabitCompletion
import java.time.LocalDate

@Entity(
    tableName = "habit_completions",
    primaryKeys = ["habitId", "completionDate"],
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["habitId", "completionDate"], unique = true)]
)
data class HabitCompletionEntity(
    val habitId: String,
    val completionDate: LocalDate
) {
    fun toDomain(): HabitCompletion {
        return HabitCompletion(
            habitId = habitId,
            completionDate = completionDate
        )
    }
}

fun HabitCompletion.toEntity(): HabitCompletionEntity {
    return HabitCompletionEntity(
        habitId = habitId,
        completionDate = completionDate
    )
}