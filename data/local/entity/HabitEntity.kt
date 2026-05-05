package com.androidforge.habitflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidforge.habitflow.domain.model.Habit
import java.time.LocalDate

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String?,
    val creationDate: LocalDate,
    val lastModifiedDate: LocalDate
) {
    fun toDomain(): Habit {
        return Habit(
            id = id,
            name = name,
            description = description,
            creationDate = creationDate,
            lastModifiedDate = lastModifiedDate
        )
    }
}

fun Habit.toEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        name = name,
        description = description,
        creationDate = creationDate,
        lastModifiedDate = lastModifiedDate
    )
}