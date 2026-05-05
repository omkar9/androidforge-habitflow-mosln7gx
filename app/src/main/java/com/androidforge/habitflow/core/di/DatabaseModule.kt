package com.androidforge.habitflow.core.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androidforge.habitflow.data.local.dao.HabitCompletionDao
import com.androidforge.habitflow.data.local.dao.HabitDao
import com.androidforge.habitflow.data.local.entity.HabitCompletionEntity
import com.androidforge.habitflow.data.local.entity.HabitEntity
import com.androidforge.habitflow.core.util.LocalDateConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(
    entities = [HabitEntity::class, HabitCompletionEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun habitCompletionDao(): HabitCompletionDao
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "habit_flow_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHabitDao(database: AppDatabase): HabitDao {
        return database.habitDao()
    }

    @Provides
    @Singleton
    fun provideHabitCompletionDao(database: AppDatabase): HabitCompletionDao {
        return database.habitCompletionDao()
    }
}