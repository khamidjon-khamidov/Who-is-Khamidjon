package com.hamidjonhamidov.whoiskhamidjon.requests.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hamidjonhamidov.whoiskhamidjon.models.about_me.AboutMeModel
import com.hamidjonhamidov.whoiskhamidjon.models.skills.SkillModel

@Database(entities = [AboutMeModel::class, SkillModel::class], version = 1)
abstract class AppDatabase: RoomDatabase(){

    abstract fun getMainMeDao(): MainDao

    companion object{
        val DATABASE_NAME = "app_db"
    }
}