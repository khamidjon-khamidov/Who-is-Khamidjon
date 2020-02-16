package com.hamidjonhamidov.whoiskhamidjon.requests.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hamidjonhamidov.whoiskhamidjon.models.about_me.AboutMeModel
import com.hamidjonhamidov.whoiskhamidjon.models.skills.SkillModel

@Dao
interface MainDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplaceAboutMe(aboutMeModel: AboutMeModel): Long

    @Query("SELECT * FROM about_me")
    fun getAboutMeModel(): LiveData<AboutMeModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceSkills(skillModel: SkillModel)

    @Query("SELECT * FROM skills")
    fun getSkills(): LiveData<List<SkillModel>>
}