package com.hamidjonhamidov.whoiskhamidjon.requests.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hamidjonhamidov.whoiskhamidjon.models.database.AboutMeModel

@Dao
interface AboutMeDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(aboutMeModel: AboutMeModel): Long

    @Query("SELECT * FROM about_me")
    fun getAboutMeModel(): LiveData<AboutMeModel?>
}