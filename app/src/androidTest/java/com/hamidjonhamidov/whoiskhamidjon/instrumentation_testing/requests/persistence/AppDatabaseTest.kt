package com.hamidjonhamidov.whoiskhamidjon.instrumentation_testing.requests.persistence

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hamidjonhamidov.whoiskhamidjon.requests.persistence.AppDatabase
import org.junit.After
import org.junit.Before

abstract class AppDatabaseTest{

    // system under test
    private lateinit var appDatabase: AppDatabase

    fun getBlogPostDao() = appDatabase.getBlogPostsDao()

    fun getMainDao() = appDatabase.getMainMeDao()


    @Before
    fun init(){
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
    }

    @After
    fun finish() = appDatabase.close()
}