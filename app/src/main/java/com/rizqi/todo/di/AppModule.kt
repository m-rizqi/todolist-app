package com.rizqi.todo.di

import android.app.Application
import androidx.room.Room
import com.rizqi.todo.data.database.TaskDatabase
import com.rizqi.todo.data.repository.TaskRepositoryImpl
import com.rizqi.todo.domain.repository.TaskRepository
import com.rizqi.todo.domain.use_case.DeleteTask
import com.rizqi.todo.domain.use_case.GetTask
import com.rizqi.todo.domain.use_case.InsertTask
import com.rizqi.todo.domain.use_case.TaskUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): TaskDatabase{
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: TaskDatabase) : TaskRepository{
        return TaskRepositoryImpl(db.taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository) : TaskUseCases{
        return TaskUseCases(
            getAllTask = GetTask(repository),
            deleteTask = DeleteTask(repository),
            insertTask = InsertTask(repository)
        )
    }
}