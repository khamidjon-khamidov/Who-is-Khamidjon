package com.hamidjonhamidov.whoiskhamidjon.models.skills

import com.hamidjonhamidov.whoiskhamidjon.models.about_me.AboutMeModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class SkillModelTest {

    val templateSkillModel = SkillModel(
        -1, "name", "image", "des", "0"
    )

    val templateSkillModel1 = SkillModel(
        0, "name#1", "image#1", "des#1", "1"
    )

    val templateSkillModel2 = SkillModel(
        2, "name#2", "image#2", "des#2", "2"
    )

    /*
        Compare two equal AboutMeModels
     */
    @Test
    internal fun isSkillEqual_identicalProperties_returnTrue()  {

        // Arrange
        val model1 = SkillModel(templateSkillModel)
        val model2 = SkillModel(templateSkillModel)

        // Act

        // Assert
        Assertions.assertEquals(model1, model2)
        println("models are equal")
    }

    /*
        Compare two AboutMeModels with 2 different pks
     */
    @Test
    internal fun isSkillsEqual_differentIds_returnFalse() {
        // Arrange
        val model1 = SkillModel(templateSkillModel)
        val model2 = SkillModel(templateSkillModel)
        model2.id = 4

        // Act

        // Assert
        assertNotEquals(model1, model2)
        println("The skills are not equal")
    }

    /*
        Compare two AboutMeModels with 2 different phone number
     */
    @Test
    internal fun isSkillsEqual_differentName_returnFalse() {

        // Arrange
        val model1 = SkillModel(templateSkillModel)
        val model2 = SkillModel(templateSkillModel)
        model2.name = "23434234"

        // Act

        // Assert
        Assertions.assertNotEquals(model1, model2)
        println("The skills are not equal")

    }
}