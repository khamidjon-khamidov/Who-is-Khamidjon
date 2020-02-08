package com.hamidjonhamidov.whoiskhamidjon.requests.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hamidjonhamidov.whoiskhamidjon.models.database.AboutMeModel

@Database(entities = [AboutMeModel::class], version = 1)
abstract class AppDatabase: RoomDatabase(){

    abstract fun getAboutMeDao(): AboutMeDao

    companion object{
        val DATABASE_NAME = "app_db"
    }
}