package com.rizqi.todolistapp.di

import com.google.firebase.database.FirebaseDatabase
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
    fun provideDatabaseInstance() = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun provideUserList(
        database: FirebaseDatabase,
    ) = database.getReference("users")

}